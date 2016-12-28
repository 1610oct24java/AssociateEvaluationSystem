package com.revature.aes.main;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.revature.aes.beans.Format;
import com.revature.aes.daos.QuestionDAO;
import com.revature.aes.services.QuestionService;

public class Driver {
	
	public static void main(String args[]){
		
<<<<<<< HEAD
		ApplicationContext ac = new ClassPathXmlApplicationContext("beans.xml");
		QuestionService service = (QuestionService) ac.getBean("questionServiceImpl");
		
		System.out.println(service.getAllQuestions());
		
=======
		for (int i=0;i<linesCleaned.size();i+=2){
			// question line
			line = linesCleaned.get(i);
			linesList = line.split(cvsSplitBy);
			//System.out.println("Question:");
			question = new Question(1, null, placeCommas(linesList[0].trim()), null);
			//System.out.println("Choices:");
			for (int j=1;j<linesList.length;j++){
				//System.out.println(placeCommas(linesList[j].trim()));
				options.add(new Option(j, placeCommas(linesList[j].trim()), false, i));
			}
			// answer line
			line = linesCleaned.get(i+1);
			linesList = line.split(cvsSplitBy);
			//System.out.println("Answers:");
			for (int j=0;j<linesList.length;j++){
				//System.out.println(placeCommas(linesList[j].trim()));
				
				//search options and mark correct answers
				for (Option option : options){
					if(placeCommas(linesList[j].trim()).equals(option.getOptionText())){
						option.setCorrect(true);
					}
				}
			}
		}
>>>>>>> refs/remotes/origin/bank-feature-questions
	}
	
}


