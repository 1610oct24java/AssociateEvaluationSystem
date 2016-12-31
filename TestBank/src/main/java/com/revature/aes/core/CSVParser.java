package com.revature.aes.core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.revature.aes.beans.Format;
import com.revature.aes.beans.Option;
import com.revature.aes.beans.Question;
import com.revature.aes.services.QuestionService;

@Component
public class CSVParser {
	private final String commaReplacement = "!__comma__!";
	private Map<String, Format> formats;
	@Autowired
	private QuestionService service;
	
	/**
	 * Replace all instances of quoted commas with a placeholder.
	 * This will enable splitting the file by commas.
	 * @return List containing the cleaned input lines.
	 * @throws IOException 
	 */
	private List<String> escapeCommas(String filename) throws IOException{
		List<String> linesCleaned = new ArrayList<String>();
		BufferedReader br = null;
		FileReader fr = null;
		String lineInFile = "";
		
		try {
			fr = new FileReader(filename);
			br = new BufferedReader(fr);
			while ((lineInFile = br.readLine()) != null) {
				// This will replace a quoted comma with the placeholder.
				// Any spaces inside the quotes are removed.
				lineInFile = lineInFile.replaceAll("\"\\s*,\\s*\"", commaReplacement);
				linesCleaned.add(lineInFile);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (fr != null){
				fr.close();
			}
			if (br != null) {
				br.close();
			}
		}
		//TODO if (lineInFile.length()%2==1) throw InvalidFileFormatException
		return linesCleaned;
	}
	
	/**
	 * Replace the placeholders with commas.
	 * @param line may contain placeholders.
	 * @return Line with commas replacing the placeholders.
	 */
	private String placeCommas(String line){
		return line.replaceAll(commaReplacement, ",");
	}
	
	
	/**
	 * Get the format type id that matches the given question.
	 * @param numberOfChoices given by the user.
	 * @return the format type id needed for storing the question.
	 */
	private Format getFormatType(int numberOfChoices){		
		// TODO get formats from db
		
		switch(numberOfChoices){
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
	 * @param filename contains the questions to be added to the system.
	 * @return all questions that are created from the input file.
	 */
	public Map<String, Question> parseCSV(String filename) {
		String cvsSplitBy = ",";
		String[] linesList;
		String line;
		Question question;
		String questionText;
		Format format;
		Option option;
		boolean trueFalseQuestion = false;
		List<Option> options = new ArrayList<Option>();
		Map<String, Question> questions = new HashMap<String, Question>();
		
		formats = new HashMap<String, Format>();
		format = new Format();
		format.setFormatId(1);
		format.setFormatName("True/False");
		formats.put("True/False", format);
		format.setFormatId(2);
		format.setFormatName("Multiple Choice");
		formats.put("Multiple Choice", format);
		format.setFormatId(3);
		format.setFormatName("Multiple Select");
		formats.put("Multiple Select", format);
		format.setFormatId(4);
		format.setFormatName("Drag and Drop");
		formats.put("Drag and Drop", format);
		format.setFormatId(5);
		format.setFormatName("Essay");
		formats.put("Essay", format);
		format.setFormatId(6);
		format.setFormatName("Short Answer");
		formats.put("Short Answer", format);
		format.setFormatId(7);
		format.setFormatName("Code Snippet");
		formats.put("Code Snippet", format);
		
		// handle the quoted commas
		List<String> linesCleaned = null;
		try {
			linesCleaned = escapeCommas(filename);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		for (int i=0;i<linesCleaned.size();i+=2){
			// *question line
			line = linesCleaned.get(i);
			linesList = line.split(cvsSplitBy);
			questionText = placeCommas(linesList[0].trim());
			question = new Question();
			question.setQuestionText(questionText);
			
			for (int j=1;j<linesList.length;j++){
				option = new Option();
				option.setOptionText(placeCommas(linesList[j].trim()));
				option.setCorrect(0);
				option.setQuestion(question);
				options.add(option);
			}
			if(linesList.length==1){
				trueFalseQuestion = true;
			}
			// *answer line
			line = linesCleaned.get(i+1);
			linesList = line.split(cvsSplitBy);
			if (trueFalseQuestion){
				format = formats.get("True/False");
				if("true".equalsIgnoreCase(linesList[0]) || "T".equalsIgnoreCase(linesList[0])){
					option = new Option();
					option.setOptionText("True");
					option.setCorrect(1);
					option.setQuestion(question);
					options.add(option);
					
					option = new Option();
					option.setOptionText("False");
					option.setCorrect(0);
					option.setQuestion(question);
					options.add(option);
				}
				else{
					option = new Option();
					option.setOptionText("True");
					option.setCorrect(0);
					option.setQuestion(question);
					options.add(option);
					
					option = new Option();
					option.setOptionText("False");
					option.setCorrect(1);
					option.setQuestion(question);
					options.add(option);
				}
			}
			else{
				// go through multiple choice options to mark the correct ones
				for (int j=0;j<linesList.length;j++){
					format = getFormatType(linesList.length);
					for (Option opt : options){
						// if answer is correct, update the option
						if (linesList[j].trim().equals(opt.getOptionText())){
							opt.setCorrect(1);
						}
					}
				}
			}
			question.setFormat(format);
			question.setMultiChoice(options);
			// save questions
			service.addQuestion(question);
			// add questions to the return map
			questions.put(question.getQuestionText(), question);
			// reset options
			options = new ArrayList<Option>();
		}
		return questions;
	}
}