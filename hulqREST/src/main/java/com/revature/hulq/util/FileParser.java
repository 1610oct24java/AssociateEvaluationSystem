package com.revature.hulq.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
@Component
public class FileParser {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	public List<String> getArgs(String keyPath) {
		// string used to hold individual groups of command arguments
		StringBuilder valueString = new StringBuilder();

		// List used to hold all groups of command arguments
		List<String> valueSet = new ArrayList<String>();

		// flag used to indicate if parser loop is within multi-line comment
		boolean inDataLine = false;
		boolean inArguments = false;

		try(FileReader fr = new FileReader(keyPath); BufferedReader br = new BufferedReader(fr)) {
			String line = br.readLine();

			while ((line = br.readLine()) != null) {
				// if line begins with multi-line comment
				if (line.trim().startsWith("/*")) {
					// set flag to true
					inDataLine = true;
					// remove beginning comment
					line = line.replace("/*", "");
				}
				
				if (line.trim().startsWith("@ArgSet") || line.trim().startsWith("@argset")){
					inArguments = true;
				}
				
				if (line.trim().startsWith("@Config") || line.trim().startsWith("@config")){
					inArguments = false;
				}
				// if inside multi-line comment
				if (inDataLine && inArguments) {
					// trim whitespace from line
					line = line.trim();
					// if end of multi-line comment has been reached
					if (line.trim().endsWith("*/")) {
						// set flag to false
						inDataLine = false;
						// trim whitespace, remove ending comment
						line = line.trim().replace("*/", "");
						// add content of line to end of value string
						// if argument line has comment
						if (line.contains("%")) {
							line = line.split("%")[0];
						}
						valueString = valueString.append(" " + line);

						// add value string to value set
						valueSet.add(valueString.toString().trim());
					}

					// if argument line has comment
					if (line.contains("%")) {
						line = line.split("%")[0];
					}

					// parse individual lines of argument notation
					if (line.startsWith("@ArgSet")) {
						// if the the argument string is not empty
						if (valueString != null) {
							if(!valueString.toString().isEmpty()){
								valueSet.add(valueString.toString().trim());
							}
							//reset value string
							valueString.setLength(0);
						}
					} else if (line.length() != 0) {
						// add argument line to value string
						valueString = valueString.append(" " + line);
					}
				}
			}
		} catch (IOException e) {
			log.info("IOException", e);
			log.info("ERROR: key parser has failed");
			log.info("CAUSE: file not found(probably)");
			log.info("ACTION: obvious");
			return null;
		}
		log.info("===================ARGS=====================");
		for(String s: valueSet)
		{
			log.info(s);
		}
		log.info("===================END ARGS=====================");

		return valueSet;
	}

	public TestProfile getTestProfile(String keyPath) {
		TestProfile testProfile = new TestProfile();
		// flag used to indicate if parser loop is within multi-line comment
		boolean inDataLine = false;
		boolean inConfigLine = false;

		try(FileReader fr = new FileReader(keyPath); BufferedReader br = new BufferedReader(fr)) {
			String line = br.readLine();
			while ((line = br.readLine()) != null) {
				line = line.trim();
				// if line begins with multi-line comment
				if (line.startsWith("/*")) {
					// set flag to true
					inDataLine = true;
					// remove beginning comment
					line = line.replace("/*", "");
				}

				if (line.startsWith("@Argset")) {
					inConfigLine = false;
					break;
				}

				if ("@Config".equalsIgnoreCase(line)) {
					inConfigLine = true;
					continue;
				}

				if (line.endsWith("*/")) {
					inDataLine = false;
				}
				// if inside multi-line comment
				if (inDataLine && inConfigLine) {

					String[] val = line.split(":");
					if (val.length == 2) {

						val[0] = val[0].toLowerCase();
						switch (val[0]) {
						case "mathmode":
							testProfile.setMathMode(Boolean.parseBoolean(val[1]));
							break;
						case "trimwhitespace":
							testProfile.setTrimWhitespace(Boolean.parseBoolean(val[1]));
							break;
						case "extractwhitespace":
							testProfile.setExtractWhitespace(Boolean.parseBoolean(val[1]));
							break;
						case "caseignore":
							testProfile.setCaseIgnore(Boolean.parseBoolean(val[1]));
							break;
						case "grosserrormargin":
							if (NumberUtils.isNumber(val[1])) {
								testProfile.setGrossErrorMargin(Double.parseDouble(val[1]));
							}
							break;
						case "caseerrormargin":
							if (NumberUtils.isNumber(val[1])) {
								testProfile.setCaseErrorMargin(Double.parseDouble(val[1]));
							}
							break;
						case "keyfilename":
							testProfile.setKeyFileName(val[1]);
							break;
						case "testfilename":
							testProfile.setTestFileName(val[1]);	
							break;
						default:
							break;
						}
					}
				}
			}
		} catch (IOException e) {
			log.info("IOException", e);
			log.info("ERROR: key parser has failed");
			log.info("CAUSE: file not found(probably)");
			log.info("ACTION: obvious");
			return null;
		}

		return testProfile;
	}
	

}
