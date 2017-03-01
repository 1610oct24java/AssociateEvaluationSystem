package com.revature.hulq.bash;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.revature.hulq.logging.Logging;
import com.revature.hulq.util.TestProfile;
import com.revature.hulq.exceptions.*;
@Component	
public class BashDriver {
	@Autowired
	Logging log;
	public double gradeCode(String keyPath, String testPath, List<String> argSet, TestProfile testProfile) {
		double result = 100.0;
		try {
			Map<Integer, BashData> valSet = runCodeTestScript(keyPath, testPath, argSet);
			result = bashGrader(valSet, testProfile);
		} catch (KeyCompilationException kce){
			// there was a problem compiling the trainers code
			result = 100.00;
		} catch (TestCompilationException tce) {
			// there was a problem compiling the candidates code
			result = 0.0;
		} catch (UnsupportedFileTypeException ufte) {
			// the file types sent to hulqBASH are not supported
			result = 100.00; 
		} catch (BashException be) {
			// there was a fault with the script itself 
			result = 100.0;
			
		}

		return result;
	}
	

	private Map<Integer, BashData> runCodeTestScript(String keyPath, String testPath, List<String> arguments) throws BashException {
		
		Map<Integer, BashData> data = new HashMap<Integer, BashData>();

		List<String> command = new ArrayList<String>();
		command.add("/bin/bash");
		command.add("hulqBASH.sh");
		command.add(keyPath);
		command.add(testPath);
		command.addAll(arguments);
		
		try {
			System.out.println("Executing bash command: " + command.toString());
			ProcessBuilder pb = new ProcessBuilder(command);
			Process p = pb.start();
			BufferedReader in = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String inputLine;
			String lineData;
			String lineType;
			Integer lineKey;

			while ((inputLine = in.readLine()) != null) {
				if (inputLine.startsWith("ERROR(f)")) {
					throw new UnsupportedFileTypeException("file type exception: the files are not currently supported by hulqBASH");
				}
				if (inputLine.startsWith("ERROR(c:k)")) {
					throw new KeyCompilationException("key compilation exception: ");
				}
				if (inputLine.startsWith("ERROR(c:t)")) {
					throw new TestCompilationException("");
				}
				
				String[] dataPair = inputLine.split(":");
				lineKey = Integer.parseInt(dataPair[0].substring(dataPair[0].length() - 1, dataPair[0].length()));
				lineType = dataPair[0].substring(0, 1);
				lineData = dataPair[1].substring(1);
				// lineKey is in the data map
				if (data.containsKey(lineKey)) {
					if (lineType.equals("t")) {
						data.get(lineKey).setUserInfo(lineData);
					} else {
						data.get(lineKey).setKeyInfo(lineData);
					}
				}
				// lineKey is not in data map
				else {
					BashData dValue = new BashData();
					if (lineType.equals("t")) {
						dValue.setUserInfo(lineData);
					} else {
						dValue.setKeyInfo(lineData);
					}
					data.put(lineKey, dValue);
				}
			}

			in.close();

		} catch (IOException e) {
			throw new BashException("Caught IO exception trying to run script" + e.getStackTrace().toString());
		}
		return data;

	}

	private double bashGrader(Map<Integer, BashData> results, TestProfile testProfile) {
		// value from the key map
		String keyVal;
		// value from the user map
		String useVal;
		// if math mode is enabled this value is used to hold the key value
		double kVal = 0.0;
		// if math mode is enabled this value is used to hold the user value
		double uVal = 0.0;
		// holds relative equality(math mode) or relative match(string mode) of single case
		double rVal = 0.0;
		// holds sum of all scores from test runs
		double totalVal = 0.0;
		// holds sum of all scores/number of test runs
		double finalResult = 0.0;
		System.out.println(testProfile.toString());
		for (Integer key : results.keySet()) {
			// get key value from map
			keyVal = results.get(key).getKeyInfo();
			// get user value from map
			useVal = results.get(key).getUserInfo();
			System.out.println("COMPARING VALUES(k:u)[" + keyVal + ":" + useVal + "]");
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
					rVal = 0.0;
					totalVal = totalVal + rVal;
				// if user response is numeric
				} else {
					kVal = Double.parseDouble(keyVal);
					uVal = Double.parseDouble(useVal);
					if(uVal > kVal){
						rVal =  (uVal - kVal) / uVal;
					} else {
						rVal = (kVal - uVal) / kVal;
					}
					System.out.println("difference:" + rVal);
				}
				// get likeness
				rVal = 1 - rVal;

			// if string mode (default)
			} else {
				// if String is a perfect match
				System.out.println(keyVal + ":" + useVal);
				if (keyVal.equals(useVal)) {
					rVal = 1.0;

				} else {
					rVal = stringCompare(keyVal, useVal);
				}
			}
			System.out.println("case result (pre case error margin): " + rVal);
			
			// if string match percentage is below, discard case value
			if (rVal < testProfile.getCaseErrorMargin()) {
				rVal = 0.0;
			}
			
			// add case result to total result
			System.out.println("case result (post case error margin): " + rVal);
			totalVal = totalVal + rVal;
		}

		// compute grade of all results
		finalResult = totalVal / (double) results.size();

		System.out.println("final result (pre gross error margin): " + finalResult);
		// if overall grade is below pass threshold, set = zero
		if (finalResult < testProfile.getGrossErrorMargin()) {
			finalResult = 0.0;
		}
		System.out.println("final result (post gross error margin): " + finalResult);
		// return final grade
		return finalResult;

	}

	private double stringCompare(String key, String user) {
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
		return ((double)key.length() - (double)matrix[key.length() - 1][user.length() - 1])/(double)key.length();
	}

}