package com.revature.aes.core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.revature.aes.beans.Format;
import com.revature.aes.beans.Option;
import com.revature.aes.beans.Question;
import com.revature.aes.services.QuestionService;

@Service
public class CSVParser {

	@Autowired
	private QuestionService questionService;

	private static final String commaReplacement = "!__comma__!";
	private Map<String, Format> formats;

	/**
	 * Replace all instances of quoted commas with a placeholder. This will
	 * enable splitting the file by commas.
	 * 
	 * @return List containing the cleaned input lines.
	 */
	private List<String> escapeCommas(String filename) {
		// TODO verify that the input file contains an even number!!!
		List<String> linesCleaned = new ArrayList<String>();
		BufferedReader br = null;
		String lineInFile = "";

		try {
			br = new BufferedReader(new FileReader(filename));
			while ((lineInFile = br.readLine()) != null) {
				// This will replace a quoted comma with the placeholder.
				// Any spaces inside the quotes are removed.
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

	/**
	 * Replace the placeholders with commas.
	 * 
	 * @param line
	 *            may contain placeholders.
	 * @return Line with commas replacing the placeholders.
	 */
	private String placeCommas(String line) {
		return line.replaceAll(commaReplacement, ",");
	}

	/**
	 * Get the format type id that matches the given question.
	 * 
	 * @param numberOfChoices
	 *            given by the user.
	 * @return the format type id needed for storing the question.
	 */
	private Format getFormatType(int numberOfChoices) {
		// get formats from db

		switch (numberOfChoices) {
		case 0:
			return formats.get("True/False");
		case 1:
			return formats.get("Multiple Choice");
		default:
			return formats.get("Multiple Select");
		}
	}

	/**
	 * Parse a CSV file and create questions.
	 * 
	 * @param filename
	 *            contains the questions to be added to the system.
	 * @return all questions that are created from the input file.
	 */
	public Map<String, Question> parseCSV(String filename) {
		String cvsSplitBy = ",";
		String[] linesList;
		String line;
		Question question;
		String questionText;
		Format format = null;
		boolean trueFalseQuestion = false;
		Set<Option> options = new HashSet<Option>();
		Map<String, Question> questions = new HashMap<String, Question>();

		formats = new HashMap<String, Format>();
		formats.put("Multiple Choice", new Format(1, "Multiple Choice"));
		formats.put("True/False", new Format(2, "True/False"));
		formats.put("Multiple Select", new Format(3, "Multiple Select"));

		/*
		 * ApplicationContext ac = new XmlWebApplicationContext();
		 * QuestionService service =(QuestionService)
		 * ac.getBean("QuestionServiceImpl");
		 */

		// handle the quoted commas
		List<String> linesCleaned = escapeCommas(filename);

		for (int i = 0; i < linesCleaned.size(); i += 2) {
			// *question line
			line = linesCleaned.get(i);
			linesList = line.split(cvsSplitBy);
			questionText = placeCommas(linesList[0].trim());
			question = new Question(0, questionText);

			for (int j = 1; j < linesList.length; j++) {
				options.add(new Option(0, placeCommas(linesList[j].trim()), 0, question));
			}
			if (linesList.length == 1) {
				trueFalseQuestion = true;
			}
			// *answer line
			line = linesCleaned.get(i + 1);
			linesList = line.split(cvsSplitBy);
			if (trueFalseQuestion) {
				format = formats.get("True/False");
				if (linesList[0].equalsIgnoreCase("true") || linesList[0].equalsIgnoreCase("T")) {
					options.add(new Option(1, "True", 1));
					options.add(new Option(2, "False", 0));
				} else {
					options.add(new Option(1, "True", 0));
					options.add(new Option(2, "False", 1));
				}
			} else {
				// go through multiple choice options to mark the correct ones
				for (int j = 0; j < linesList.length; j++) {
					format = getFormatType(linesList.length);
					for (Option option : options) {
						// if answer is correct, update the option
						if (linesList[j].trim().equals(option.getOptionText())) {
							option.setCorrect(1);
						}
					}
				}
			}
			question.setFormat(format);
			question.setMultiChoice(options);
			// save question here
			questionService.addQuestion(question);
			questions.put(question.getQuestionText(), question);
		}
		return questions;
	}
}