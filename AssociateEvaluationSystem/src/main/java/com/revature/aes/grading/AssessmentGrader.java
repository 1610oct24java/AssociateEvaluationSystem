package com.revature.aes.grading;

import java.sql.Timestamp;
import java.time.LocalDate;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssessmentGrader {

	Logging log = new Logging();

	public double gradeAssessment(Assessment assessment){
		log.info("Grading assessmsent#"+assessment.getAssessmentId());
		double dragDrop[] = gradeDragDrop(assessment);
		double multChoiceSelect[] = gradeMultChoiceSelect(assessment);
		double snippet[] = gradeSnippet(assessment);
		log.info("Drag and Drop Score: "+dragDrop[0]+"/"+dragDrop[1]);
		log.info("Multiple Choice/Select Score: "+multChoiceSelect[0]+"/"+multChoiceSelect[1]);
		log.info("Snippet Score: "+snippet[0]+"/"+snippet[1]);
		double earnedPoints = (dragDrop[0]+multChoiceSelect[0]+snippet[0]);
		double availablePoints = (dragDrop[1]+multChoiceSelect[1]+snippet[1]);
		log.info("points earned: " + earnedPoints + " points available: " + availablePoints);
		log.info("Score earned: "+earnedPoints/availablePoints);
		return 100*(earnedPoints/availablePoints);
	}
	
	public double[] gradeMultChoiceSelect(Assessment assessment){
		double[] result = new double[2];
		double itemWeightedGrade = 0.0;
		double itemWeight = 0.0;
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
		Map<Integer, TemplateQuestion> templateDataMap = new HashMap<Integer, TemplateQuestion>();
		Map<Integer, Set<Option>> userDataMap = new HashMap<Integer, Set<Option>>();

		//Polulate map of all questions from template
		for(TemplateQuestion tQuestion: assessment.getTemplate().getTemplateQuestion()){
			templateDataMap.put(tQuestion.getQuestion().getQuestionId(), tQuestion);
		}
		
		//Populate map of all selections from user, grouped by question pushed to set
		for(Option opt: assessment.getOptions()){
			if(userDataMap.containsKey(opt.getQuestion().getQuestionId())){
				userDataMap.get(opt.getQuestion().getQuestionId()).add(opt);
				log.info(" adding option to option set: " + opt.getOptionText() + " on question: " +opt.getQuestion().getQuestionId());
			}
			else {
				Set<Option> optSet = new HashSet<Option>();
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
			//result[1] = result[1]+itemWeight;
			log.info(" out of " + countOptions + " options " + countCorrect +" were correct");
		}
		return result;
	}

	public double[] gradeDragDrop(Assessment assessment){
		double[] result = new double[2];
		double itemWeightedGrade = 0.0;
		double itemWeight = 0.0;
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
		Map<Integer, TemplateQuestion> templateDataMap = new HashMap<Integer, TemplateQuestion>();
		Map<Integer, Set<AssessmentDragDrop>> userDataMap = new HashMap<Integer, Set<AssessmentDragDrop>>();
		
		
		for(TemplateQuestion tQuestion: assessment.getTemplate().getTemplateQuestion()){
			//System.out.println("Adding to templateDataMap: " + tQuestion);
			templateDataMap.put(tQuestion.getQuestion().getQuestionId(), tQuestion);
		}
		
		
		for(AssessmentDragDrop dragDrop: assessment.getAssessmentDragDrop()){
			if(userDataMap.containsKey(dragDrop.getDragDrop().getQuestion().getQuestionId())){
				//System.out.println("Adding to userDataMap: " + dragDrop);
				userDataMap.get(dragDrop.getDragDrop().getQuestion().getQuestionId()).add(dragDrop);
			}
			else {
				//System.out.println("Creating list of dragDrop statring with: " + dragDrop);
				Set<AssessmentDragDrop> dragDropSet = new HashSet<AssessmentDragDrop>();
				dragDropSet.add(dragDrop);
				userDataMap.put(dragDrop.getDragDrop().getQuestion().getQuestionId(), dragDropSet);
			}
		}

		for(int key: userDataMap.keySet()){
			/*System.out.println("At key: " + key);
			System.out.println("TemplateDataMap: " + templateDataMap);
			System.out.println("TemplateDataMap at key: " + templateDataMap.get(key));*/
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
			//result[1] = result[1]+itemWeight;
			log.info("out of " + countOptions + " options " + countCorrect +" were correct");
		}

		return result;
	}

	
	
	// This method should return the aggregate grades for all snippets
	public double[] gradeSnippet(Assessment assessment){
		SnippetEvaluationClient sec = new SnippetEvaluationClient();
		double[] result = new double[2];
		double itemWeightedGrade = 0.0;
		double itemWeight = 0.0;
		//Getting total weight of Drag and Drop questions for the assessment template
		for(TemplateQuestion tQuestion : assessment.getTemplate().getTemplateQuestion()){

			if(tQuestion.getQuestion().getFormat().getFormatName().equals("Code Snippet")){

				result[1]+=tQuestion.getWeight();

			}

		}
		//handling if no Code Snippet questions answered
		if(assessment.getFileUpload()==null){

			return result;

		}
		Map<Integer, FileUpload> userDataMap = new HashMap<Integer, FileUpload>();
		Map<Integer, TemplateQuestion> templateDataMap = new HashMap<Integer, TemplateQuestion>(); 
		
		for(TemplateQuestion tQuestion: assessment.getTemplate().getTemplateQuestion()){
			templateDataMap.put(tQuestion.getQuestion().getQuestionId(), tQuestion);
		}
		
		for(FileUpload file : assessment.getFileUpload()){	
			userDataMap.put(file.getQuestion().getQuestionId(), file);
		}
						
		for(int key: userDataMap.keySet()){
			String userFileName = userDataMap.get(key).getFileUrl();
			log.info(" userFileName: " + userFileName);
			String keyFileName = templateDataMap.get(key).getQuestion().getSnippetTemplates().iterator().next().getSolutionUrl();
			log.info(" keyFileName: " + keyFileName);
			itemWeight = templateDataMap.get(key).getWeight();
			log.info(" weight: "+itemWeight);
			int codeTestResult = sec.evaluateSnippet(userFileName, keyFileName);
			log.info(" code being evaluated to "+codeTestResult);
			itemWeightedGrade = itemWeight*codeTestResult;//
			result[0] = result[0]+itemWeightedGrade;
			result[1] = result[1]+itemWeight;
		}

		return result;
	}
	
}