package com.revature.aes.fileTester;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileParser {
		
	public static List<String> getArgs(String keyPath) {
		// string used to hold individual groups of command arguments
		String valueString = "";

		// List used to hold all groups of command arguments
		List<String> valueSet = new ArrayList<String>();

		// flag used to indicate if parser loop is within multi-line comment
		boolean inDataLine = false;

		try {
			BufferedReader br = new BufferedReader(new FileReader(keyPath));
			String line = br.readLine();
			while ((line = br.readLine()) != null) {
				// if line begins with multi-line comment
				if (line.trim().startsWith("/*")) {
					// set flag to true
					inDataLine = true;
					// remove beginning comment
					line = line.replace("/*", "");
				}
				// if inside multi-line comment
				if (inDataLine) {
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
						valueString = valueString + " " + line;
						
						// add value string to value set
						valueSet.add(valueString.trim());
					}

					// if argument line has comment
					if (line.contains("%")) {
						line = line.split("%")[0];
					}

					// parse individual lines of argument notation
					if (line.startsWith("@ArgSet")) {
						// if the the argument string is not empty
						if (valueString != null && !(valueString.equals(""))) {

							valueSet.add(valueString.trim());
						}
						// reset value string
						valueString = "";
					} else if(line.length()!=0){
						// add argument line to value string
						valueString = valueString + " " + line;
					}
				}
			}
			br.close();
		} catch (IOException e) {
			System.out.println("ERROR: key parser has failed");
			System.out.println("CAUSE: file not found(probably)");
			System.out.println("ACTION: obvious");
			return null;
		}

		return valueSet;
	}

}
