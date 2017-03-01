package com.revature.aes.grading;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.revature.aes.beans.Assessment;
import com.revature.aes.beans.AssessmentDragDrop;
import com.revature.aes.beans.FileUpload;
import com.revature.aes.beans.Option;
import com.revature.aes.beans.TemplateQuestion;
import com.revature.aes.logging.Logging;
import org.springframework.stereotype.Component;

@Component
public class AssessmentGrader {

	private Logging log = new Logging();

	public double gradeAssessment(Assessment assessment){

		Map<Integer, TemplateQuestion> templateDataMap = new HashMap<>();
		for(TemplateQuestion tQuestion: assessment.getTemplate().getTemplateQuestion()){
			templateDataMap.put(tQuestion.getQuestion().getQuestionId(), tQuestion);
		}

		log.info("Grading assessment#"+assessment.getAssessmentId());
		double dragDrop[] = gradeDragDrop(assessment, templateDataMap);
		double multipleChoiceSelect[] = gradeMultChoiceSelect(assessment, templateDataMap);
		double sourceCode[] = gradeSourceCode(assessment, templateDataMap);
		log.info("Drag and Drop Score: "+dragDrop[0]+"/"+dragDrop[1]);
		log.info("Multiple Choice/Select Score: "+multipleChoiceSelect[0]+"/"+multipleChoiceSelect[1]);
		log.info("Snippet Score: "+sourceCode[0]+"/"+sourceCode[1]);
		double earnedPoints = (dragDrop[0]+multipleChoiceSelect[0]+sourceCode[0]);
		double availablePoints = (dragDrop[1]+multipleChoiceSelect[1]+sourceCode[1]);
		log.info("points earned: " + earnedPoints + " points available: " + availablePoints);
		log.info("Score earned: "+earnedPoints/availablePoints);
		return 100*(earnedPoints/availablePoints);
	}
	
	private double[] gradeMultChoiceSelect(Assessment assessment, Map<Integer, TemplateQuestion> templateDataMap){
		double[] result = new double[2];
		double itemWeightedGrade;
		double itemWeight;
		//Getting total weight of MultipleChoice/Select questions from assessment template
		for(TemplateQuestion tQuestion : assessment.getTemplate().getTemplateQuestion()){

			if(tQuestion.getQuestion().getFormat().getFormatName().equals("Multiple Choice") ||
					tQuestion.getQuestion().getFormat().getFormatName().equals("Multiple Select")){

				result[1]+=tQuestion.getWeight();

			}

		}
		//handling if no MultipleChoice/Select questions answered
		if(assessment.getOptions()==null){

			return result;

		}

		Map<Integer, Set<Option>> userDataMap = new HashMap<>();

		//Populate map of all selections from user, grouped by question pushed to set
		for(Option opt: assessment.getOptions()){
			if(userDataMap.containsKey(opt.getQuestion().getQuestionId())){
				userDataMap.get(opt.getQuestion().getQuestionId()).add(opt);
				log.info(" adding option to option set: " + opt.getOptionText() + " on question: " +opt.getQuestion().getQuestionId());
			}
			else {
				Set<Option> optSet = new HashSet<>();
				optSet.add(opt);
				userDataMap.put(opt.getQuestion().getQuestionId(), optSet);
				log.info(" creating new option set with : " + opt.getOptionText() + " on question: " +opt.getQuestion().getQuestionId());
			}
		}

		//Grade questions
		for(int key: userDataMap.keySet()){
			itemWeight = templateDataMap.get(key).getWeight();
			double countCorrect = 0.0;
			double countOptions = 0.0;

			for(Option opt: templateDataMap.get(key).getQuestion().getOption()){
					countOptions+=opt.getCorrect();
			}

			for(Option opt: userDataMap.get(key)){
				log.info(" grading question: " + opt.getQuestion().getQuestionText());
				log.info(" answer selected: " + opt.getOptionText());
				log.info(" answer should be " + opt.getCorrect());
				if(opt.getCorrect() == 1) {
					log.info("[Info] " + new Timestamp(System.currentTimeMillis()) + " option evaluted as correct");
					countCorrect += 1.0;
				}
				else{
					countCorrect-=0.5;
				}
			}

			if(countCorrect<0){	countCorrect=0;	}

			itemWeightedGrade = itemWeight*(countCorrect/countOptions);//
			result[0] = result[0]+itemWeightedGrade;
			log.info(" out of " + countOptions + " options " + countCorrect +" were correct");
		}
		return result;
	}

	private double[] gradeDragDrop(Assessment assessment, Map<Integer, TemplateQuestion> templateDataMap){
		double[] result = new double[2];
		double itemWeightedGrade;
		double itemWeight;
		//Getting total weight of Drag and Drop questions for the assessment template
		for(TemplateQuestion tQuestion : assessment.getTemplate().getTemplateQuestion()){

			if(tQuestion.getQuestion().getFormat().getFormatName().equals("Drag and Drop")){

				result[1]+=tQuestion.getWeight();

			}

		}
		//handling if no Code Snippet questions answered
		if(assessment.getAssessmentDragDrop()==null){

			return result;

		}
		Map<Integer, Set<AssessmentDragDrop>> userDataMap = new HashMap<>();
		
		for(AssessmentDragDrop dragDrop: assessment.getAssessmentDragDrop()){
			if(userDataMap.containsKey(dragDrop.getDragDrop().getQuestion().getQuestionId())){
				userDataMap.get(dragDrop.getDragDrop().getQuestion().getQuestionId()).add(dragDrop);
			}
			else {
				Set<AssessmentDragDrop> dragDropSet = new HashSet<>();
				dragDropSet.add(dragDrop);
				userDataMap.put(dragDrop.getDragDrop().getQuestion().getQuestionId(), dragDropSet);
			}
		}

		for(int key: userDataMap.keySet()){
			itemWeight = templateDataMap.get(key).getWeight();
			
			double countCorrect = 0.0;
			double countOptions = 0.0;
			for(AssessmentDragDrop dragDrop: userDataMap.get(key)){
				log.info(" grading question: " + dragDrop.getDragDrop().getQuestion().getQuestionText());
				log.info(" answer selected: " + dragDrop.getDragDrop().getDragDropText());
				log.info(" user position is " + dragDrop.getUserOrder() + " should be " + dragDrop.getDragDrop().getCorrectOrder());
				if(dragDrop.getUserOrder() == dragDrop.getDragDrop().getCorrectOrder()){
					log.info("[Info] "+ new Timestamp(System.currentTimeMillis())+" option evaluted as correct");
					countCorrect += 1.0;
				}
				countOptions += 1.0;
			}

			itemWeightedGrade = itemWeight*(countCorrect/countOptions);//
			result[0] = result[0]+itemWeightedGrade;
			log.info("out of " + countOptions + " options " + countCorrect +" were correct");
		}

		return result;
	}

	
	
	// This method should return the aggregate grades for all source code questions
	private double[] gradeSourceCode(Assessment assessment, Map<Integer, TemplateQuestion> templateDataMap){
		SourceCodeEvaluationClient sec = new SourceCodeEvaluationClient();
		double[] result = new double[2];
		double itemWeightedGrade;
		double itemWeight;

		for(TemplateQuestion tQuestion : assessment.getTemplate().getTemplateQuestion()){

			if(tQuestion.getQuestion().getFormat().getFormatName().equals("Code Snippet")){

				result[1]+=tQuestion.getWeight();

			}

		}
		//handling if no Code Snippet questions answered
		if(assessment.getFileUpload()==null){

			return result;

		}
		Map<Integer, FileUpload> userDataMap = new HashMap<>();

		for(FileUpload file : assessment.getFileUpload()){	
			userDataMap.put(file.getQuestion().getQuestionId(), file);
		}

		double codeTestResult;

		for(int key: userDataMap.keySet()){
			String userFileName = userDataMap.get(key).getFileUrl();
			log.info(" userFileName: " + userFileName);
			String keyFileName = templateDataMap.get(key).getQuestion().getSnippetTemplates().iterator().next().getSolutionUrl();
			log.info(" keyFileName: " + keyFileName);
			itemWeight = templateDataMap.get(key).getWeight();
			log.info(" weight: "+itemWeight);
			codeTestResult = sec.evaluateSourceCode(userFileName, keyFileName);
			log.info(" code being evaluated to "+codeTestResult);
			itemWeightedGrade = itemWeight*codeTestResult;//
			result[0] = result[0]+itemWeightedGrade;
		}

		return result;
	}
	
}