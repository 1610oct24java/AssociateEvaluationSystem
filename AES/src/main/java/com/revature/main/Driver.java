package com.revature.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Driver {
	public static void main(String[] args) {

		String csvFile = "input.csv";
		BufferedReader br = null;
		String line = "";
		String cvsSplitBy = ",";

		try {
			br = new BufferedReader(new FileReader(csvFile));
			// question line
			while ((line = br.readLine()) != null) {

				String[] lines = line.split(cvsSplitBy);
				System.out.println("Question: " + lines[0].trim().replaceAll("`", ","));
				System.out.println("Choices:");
				for (int i = 1; i < lines.length; i++) {
					System.out.println(lines[i].trim().replaceAll("`", ","));
				}
				// answer line
				line = br.readLine();
				lines = line.split(cvsSplitBy);
				System.out.println("Answer: ");
				for (int i = 0; i < lines.length; i++) {
					System.out.println(lines[i].trim().replaceAll("`", ","));
				}				
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

	}
}