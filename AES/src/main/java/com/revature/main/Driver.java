package com.revature.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Driver {
	private static final String commaReplacement = "!__comma__!";
	
	/*
	 * Replace all instances of quoted commas with a placeholder.
	 * This will enable splitting the file by commas.
	 */	
	private static List<String> escapeCommas(){
		List<String> linesCleaned = new ArrayList<String>();
		String csvFile = "input.csv";
		BufferedReader br = null;
		String lineInFile = "";

		try {
			br = new BufferedReader(new FileReader(csvFile));
			while ((lineInFile = br.readLine()) != null) {
				//lineInFile = lineInFile.replaceAll("\",\"", commaReplacement);
				lineInFile = lineInFile.replaceAll("\"\\s*,\\s*\"", commaReplacement);
				linesCleaned.add(lineInFile);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}		
		return linesCleaned;
	}
	
	private static String placeCommas(String line){
		return line.replaceAll(commaReplacement, ",");
	}
	
	public static void main(String[] args) {
		String cvsSplitBy = ",";
		String[] linesList;
		String line;

		//have this return the list
		List<String> linesCleaned = escapeCommas();
		
		for (int i=0;i<linesCleaned.size();i+=2){
			// question line
			line = linesCleaned.get(i);
			linesList = line.split(cvsSplitBy);
			System.out.println("Question:");
			System.out.println(placeCommas(linesList[0].trim()));
			System.out.println("Choices:");
			for (int j=1;j<linesList.length;j++){
				System.out.println(placeCommas(linesList[j].trim()));
			}
			// answer line
			line = linesCleaned.get(i+1);
			linesList = line.split(cvsSplitBy);
			System.out.println("Answers:");
			for (int j=0;j<linesList.length;j++){
				System.out.println(placeCommas(linesList[j].trim()));
			}
		}
	}
}
