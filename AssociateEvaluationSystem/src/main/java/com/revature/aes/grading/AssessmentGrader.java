package com.revature.aes.grading;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.revature.aes.beans.Assessment;
import com.revature.aes.beans.AssessmentDragDrop;
import com.revature.aes.beans.FileUpload;
import com.revature.aes.beans.Option;
import com.revature.aes.beans.TemplateQuestion;

public class AssessmentGrader {
	public double gradeAssessment(Assessment assessment){
		double dragDrop[] = gradeDragDrop(assessment);
		double multChoiceSelect[] = gradeMultChoiceSelect(assessment); 
		double snippet[] = gradeSnippet(assessment);
		
		return ((100*(dragDrop[0]+multChoiceSelect[0]+snippet[0]))/(dragDrop[1]+multChoiceSelect[1]+snippet[1]));
	}
	
	public double[] gradeMultChoiceSelect(Assessment assessment){
		double[] result = new double[2];
		double itemWeightedGrade = 0.0;
		double itemWeight = 0.0;
		Map<Integer, TemplateQuestion> templateDataMap = new HashMap<Integer, TemplateQuestion>();
		Map<Integer, Set<Option>> userDataMap = new HashMap<Integer, Set<Option>>();
		
		for(TemplateQuestion tQuestion: assessment.getMyTemplate().getTemplateQuestion()){
			templateDataMap.put(tQuestion.getTemplateQuestionId(), tQuestion);
		}
		
		
		for(Option opt: assessment.getOptions()){
			if(userDataMap.containsKey(opt.getQuestion())){
				userDataMap.get(opt.getQuestion()).add(opt);
			}
			else {
				Set<Option> optSet = new HashSet<Option>();
				optSet.add(opt);
				userDataMap.put(opt.getQuestion(), optSet);
			}
		}
		
		for(int key: userDataMap.keySet()){
			itemWeight = templateDataMap.get(key).getWeight();
			
			double countCorrect = 0.0;
			double countOptions = 0.0;
			for(Option opt: userDataMap.get(key)){
				if(opt.getCorrect() == 1){
					countCorrect += 1.0;
				}
				countOptions += 1.0;
			}
			
			itemWeightedGrade = itemWeight*(countCorrect/countOptions);//
			result[0] = result[0]+itemWeightedGrade;
			result[1] = result[1]+itemWeight;
		}
		return result;
	}
		
	public double[] gradeDragDrop(Assessment assessment){
		double[] result = new double[2];
		double itemWeightedGrade = 0.0;
		double itemWeight = 0.0;
		Map<Integer, TemplateQuestion> templateDataMap = new HashMap<Integer, TemplateQuestion>();
		Map<Integer, Set<AssessmentDragDrop>> userDataMap = new HashMap<Integer, Set<AssessmentDragDrop>>();
		
		
		for(TemplateQuestion tQuestion: assessment.getMyTemplate().getTemplateQuestion()){
			templateDataMap.put(tQuestion.getTemplateQuestionId(), tQuestion);
		}
		
		
		for(AssessmentDragDrop dragDrop: assessment.getAssessmentDragDrop()){
			if(userDataMap.containsKey(dragDrop.getDragDrop().getQuestionId())){
				userDataMap.get(dragDrop.getDragDrop().getQuestionId()).add(dragDrop);
			}
			else {
				Set<AssessmentDragDrop> dragDropSet = new HashSet<AssessmentDragDrop>();
				dragDropSet.add(dragDrop);
				userDataMap.put(dragDrop.getDragDrop().getQuestionId(), dragDropSet);
			}
		}

		for(int key: userDataMap.keySet()){
			itemWeight = templateDataMap.get(key).getWeight();
			
			double countCorrect = 0.0;
			double countOptions = 0.0;
			for(AssessmentDragDrop dragDrop: userDataMap.get(key)){
				if(dragDrop.getUserOrder() == dragDrop.getDragDrop().getCorrectOrder()){
					countCorrect += 1.0;
				}
				countOptions += 1.0;
			}
			
			itemWeightedGrade = itemWeight*(countCorrect/countOptions);//
			result[0] = result[0]+itemWeightedGrade;
			result[1] = result[1]+itemWeight;
		}
		
		return result;
	}
		
	
	
	// This method should return the aggregate grades for all snippets
	public double[] gradeSnippet(Assessment assessment){
		SnippetEvaluationClient sec = new SnippetEvaluationClient();
		double[] result = new double[2];
		double itemWeightedGrade = 0.0;
		double itemWeight = 0.0;
		Map<Integer, FileUpload> userDataMap = new HashMap<Integer, FileUpload>();
		Map<Integer, TemplateQuestion> templateDataMap = new HashMap<Integer, TemplateQuestion>(); 
		
		for(TemplateQuestion tQuestion: assessment.getMyTemplate().getTemplateQuestion()){
			templateDataMap.put(tQuestion.getTemplateQuestionId(), tQuestion);
		}
		
		for(FileUpload file : assessment.getFileUpload()){	
			userDataMap.put(file.getQuestionId(), file);
		}
						
		for(int key: userDataMap.keySet()){
			String userFileName = userDataMap.get(key).getFileUrl();
			String keyFileName = templateDataMap.get(key).getTemplateQuestion().getSnippetTemplate().getSolutionUrl();
			itemWeight = templateDataMap.get(key).getWeight();
			int codeTestResult = sec.evaluateSnippet(userFileName, keyFileName);
			itemWeightedGrade = itemWeight*codeTestResult;//
			result[0] = result[0]+itemWeightedGrade;
			result[1] = result[1]+itemWeight;
		}
			
		return result;
	}
	
}