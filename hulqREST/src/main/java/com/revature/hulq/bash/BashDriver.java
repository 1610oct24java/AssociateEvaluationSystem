package com.revature.hulq.bash;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.revature.hulq.logging.Logging;
import com.revature.hulq.util.TestProfile;
import com.revature.hulq.exceptions.*;
@Component	
public class BashDriver {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	public double gradeCode(String keyPath, String testPath, String timeOutLimit, List<String> argSet, TestProfile testProfile) {
		double result;
		try {
			Map<Integer, BashData> valSet = runCodeTestScript(keyPath, testPath, argSet, timeOutLimit);
			result = bashGrader(valSet, testProfile) * 100;
		} catch (KeyCompilationException kce){
			// there was a problem compiling the trainers code
			result = 0.0;
		} catch (TestCompilationException tce) {
			// there was a problem compiling the candidates code
			result = 0.0;
		} catch (UnsupportedFileTypeException ufte) {
			// the file types sent to hulqBASH are not supported
			result = 0.0; 
		} catch (BashException be) {
			// there was a fault with the script itself 
			result = 0.0;
		}
		log.info("The result is " + result);
		return result;
	}
	
	/**
	 * 
	 * @param keyPath : File that is the answer
	 * @param testPath : File that user entered
	 * @param arguments : 
	 * @return
	 * @throws BashException
	 */
	private Map<Integer, BashData> runCodeTestScript(String keyPath, String testPath, List<String> arguments, String timeOutLimit) throws BashException {
		
		Map<Integer, BashData> data = new HashMap<>();
		
		log.info("Time out limit specified: " + timeOutLimit);
		
		List<String> command = new ArrayList<>();
		command.add("/bin/bash");
		command.add("hulqBASH.sh");
		command.add(timeOutLimit);	
		command.add(keyPath);
		command.add(testPath);
		command.addAll(arguments);
		
		log.info("WAFFLES");
		log.info("============= runCodeTestScript ===============");
		log.info("keyPath: " + keyPath);
		log.info("testPath: " + testPath);
		log.info("args: "+arguments);
		
		try {
			log.info("Executing bash command: " + command.toString());
			ProcessBuilder pb = new ProcessBuilder(command);
			Process p = pb.start();
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String inputLine = null;
			StringBuilder lineData = null;
			String lineType = null;
			Integer lineKey = null;

			BufferedReader stdError = new BufferedReader(new InputStreamReader(p.getErrorStream()));
			// read any errors from the executed bash command, normally occurs at the for loop in hulqBASH.sh
			String errorStr = null;
		    log.info("\nHere is the standard error of the command (if any):\n");
		    while ((errorStr = stdError.readLine()) != null)
		    {
	        	log.info("It broke.\n" + errorStr);
				log.error("============= END runCodeTestScript (TestCompilationException) ===============");
				throw new TestCompilationException("Error or Exception found");
		    }
		    stdError.close();

		    log.info("Nothing broke yet...\nNow testing the code:");
			while ((inputLine = in.readLine()) != null) {
				log.info("BASH: ");
				log.info(inputLine);
				if (inputLine.startsWith("ERROR(f)")) {
					log.error(inputLine);
					log.error("============= END runCodeTestScript (Error File Types) ===============");
					throw new UnsupportedFileTypeException("file type exception: the files are not currently supported by hulqBASH");
				}
				if (inputLine.startsWith("ERROR(c:k)")) {
					log.error(inputLine);
					log.error("============= END runCodeTestScript (Error Key Compile) ===============");
					throw new KeyCompilationException("key compilation exception: ");
				}
				if (inputLine.startsWith("ERROR(c:t)")) {
					log.error(inputLine);
					log.error("============= END runCodeTestScript (Error) ===============");
					throw new TestCompilationException("");
				}
				//Spaghetti code here...
				if (inputLine.startsWith("key") || inputLine.startsWith("test")) {
					//Update key info
					if (lineKey != null) {
						// lineKey is in the data map
						if (data.containsKey(lineKey)) {
							if (lineType.equals("t")) {
								data.get(lineKey).setUserInfo(lineData.toString());
							} else {
								data.get(lineKey).setKeyInfo(lineData.toString());
							}
						}
						lineData = null;
					}

					String[] dataPair = inputLine.split(":");
					log.info("THE INPUTLINE IS " + inputLine);
					lineKey = Integer.parseInt(dataPair[0].substring(dataPair[0].length() - 1, dataPair[0].length()));
					lineType = dataPair[0].substring(0, 1);
					lineData = new StringBuilder(dataPair[1].substring(1));

					// lineKey is in the data map
					if (data.containsKey(lineKey)) {
						if (lineType.equals("t")) {
							data.get(lineKey).setUserInfo(lineData.toString());
						} else {
							data.get(lineKey).setKeyInfo(lineData.toString());
						}
					}
					// lineKey is not in data map
					else {
						BashData dValue = new BashData();
						if (lineType.equals("t")) {
							dValue.setUserInfo(lineData.toString());
						} else {
							dValue.setKeyInfo(lineData.toString());
						}
						data.put(lineKey, dValue);
					}
				}
				else {
					//Append the inputLine echo output to lineData
					if (lineData != null) {
						lineData.append(" " + inputLine);
					}
				}
			}

			//update key info
			if (lineKey != null) {
				// lineKey is in the data map
				if (data.containsKey(lineKey)) {
					if (lineType.equals("t")) {
						data.get(lineKey).setUserInfo(lineData.toString());
					} else {
						data.get(lineKey).setKeyInfo(lineData.toString());
					}
				}
				lineData = null;
			}

			in.close();

		} catch (IOException e) {
			log.warn("============= END runCodeTestScript (IOException) ===============");
			throw new BashException("Caught IO exception trying to run script" + e.getStackTrace().toString());
		} catch (Throwable e) {
			log.warn("============= END runCodeTestScript (Exception) ===============");
			log.error("ERROR: ", e);
			throw new BashException("Some sort of exception occurred when trying to run script");
		}
		log.info("============= END runCodeTestScript (Ok) ===============");
		return data;

	}

	private double bashGrader(Map<Integer, BashData> results, TestProfile testProfile) {
		// value from the key map
		String keyVal;
		// value from the user map
		String useVal;
		// if math mode is enabled this value is used to hold the key value
		double kVal;
		// if math mode is enabled this value is used to hold the user value
		double uVal;
		// holds relative equality(math mode) or relative match(string mode) of single case
		double rVal;
		// holds sum of all scores from test runs
		double totalVal = 0.0;
		// holds sum of all scores/number of test runs
		double finalResult;
		log.info("============= Bash Grader ===============");
		log.info(testProfile.toString());
		for (Integer key : results.keySet()) {
			// get key value from map
			keyVal = results.get(key).getKeyInfo();
			// get user value from map
			useVal = results.get(key).getUserInfo();
			log.info("COMPARING VALUES(k:u)[" + keyVal + ":" + useVal + "]");
			// if trim whitespace is enabled
			if (testProfile.isTrimWhitespace()) {
				keyVal = keyVal.trim();
				useVal = useVal.trim();
			}
			// if extract whitespace is enabled
			if (testProfile.isExtractWhitespace()) {
				keyVal = keyVal.replaceAll("\\s+", "");
				useVal = useVal.replaceAll("\\s+", "");
			}
			// if ignore case is enabled
			if (testProfile.isCaseIgnore()) {
				keyVal = keyVal.toLowerCase();
				useVal = useVal.toLowerCase();
			}
			// if math mode is enabled and can be used
			if (testProfile.isMathMode() && NumberUtils.isNumber(keyVal)) {
				// if user response is non numeric
				if (!NumberUtils.isNumber(useVal)) {
					rVal = 1.0;
				// if user response is numeric
				} else {
					kVal = Double.parseDouble(keyVal);
					uVal = Double.parseDouble(useVal);
					if(uVal > kVal){
						rVal =  (uVal - kVal) / uVal;
					} else {
						rVal = (kVal - uVal) / kVal;
					}
					log.info("difference:" + rVal);
				}
				// get likeness
				rVal = 1 - rVal;

			// if string mode (default)
			} else {
				// if String is a perfect match
				log.info(keyVal + ":" + useVal);
				if (keyVal.equals(useVal)) {
					rVal = 1.0;

				} else {
					rVal = stringCompare(keyVal, useVal);
				}
			}
			log.info("case result (pre case error margin): " + rVal);
			
			// if string match percentage is below, discard case value
			if (rVal < testProfile.getCaseErrorMargin()) {
				rVal = 0.0;
			}
			
			// add case result to total result
			log.info("case result (post case error margin): " + rVal);
			totalVal = totalVal + rVal;
		}

		// compute grade of all results
		finalResult = totalVal / (double) results.size();

		log.info("final result (pre gross error margin): " + finalResult);
		// if overall grade is below pass threshold, set = zero
		if (finalResult < testProfile.getGrossErrorMargin()) {
			finalResult = 0.0;
		}
		log.info("final result (post gross error margin): " + finalResult);
		// return final grade
		
		//If finalResult is less than 2% then it is ignored
		//Since our partial credit tends to be generous
		if ( finalResult < .03)
		{
			finalResult=0.0;
			log.warn("Final Result is less than 3% giving 0% since our grading might be too genorous");
		}
		
		if(Double.isNaN(finalResult)){
			finalResult = 0.0;
		}
		
		log.info("============= END Bash Grader ===============");
		return finalResult;

	}

	private double stringCompare(String key, String user) {
		log.info("================ stringCompare ==================");
		if (key.isEmpty() || user.isEmpty()) {
			log.info("================ END stringCompare (empty) ==================");
			return 0;
		}
		//this method is similar to cosine inequality
		int[][] matrix = new int[key.length() + 1][user.length() + 1];

		for (int i = 0; i <= key.length(); i++) {
			matrix[i][0] = i;
		}

		for (int j = 0; j <= user.length(); j++) {
			matrix[0][j] = j;
		}

		int sub, add, del;
		for (int i = 1; i <= key.length(); i++) {
			for (int j = 1; j <= user.length(); j++) {
				if (key.charAt(i - 1) == user.charAt(j - 1)) {
					matrix[i][j] = matrix[i - 1][j - 1];
				} else {
					sub = 1 + matrix[i - 1][j - 1];
					add = 1 + matrix[i][j - 1];
					del = 1 + matrix[i - 1][j];
					matrix[i][j] = Integer.min(sub, Integer.min(add, del));
				}
			}
		}
		
		log.info("================ END stringCompare ==================");
		return ((double)key.length() - (double)matrix[key.length() - 1][user.length() - 1])/(double)key.length();
	}

}