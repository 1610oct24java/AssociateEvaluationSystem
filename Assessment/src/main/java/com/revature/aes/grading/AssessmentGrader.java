package com.revature.aes.grading;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.revature.aes.beans.Assessment;
import com.revature.aes.beans.AssessmentDragDrop;
import com.revature.aes.beans.FileUpload;
import com.revature.aes.beans.Option;
import com.revature.aes.beans.SnippetTemplate;
import com.revature.aes.beans.TemplateQuestion;

public class AssessmentGrader {
	public double gradeAssessment(Assessment assessment){
		double[] dragDrop = gradeDragDrop(assessment);
		double[] multChoiceSelect = gradeMultChoiceSelect(assessment); 
		double[] snippet = gradeSnippet(assessment);
		
		return (100*(dragDrop[0]+multChoiceSelect[0]+snippet[0]))/(dragDrop[1]+multChoiceSelect[1]+snippet[1]);
	}
	
	public double[] gradeMultChoiceSelect(Assessment assessment){
		double[] result = new double[2];
		double itemWeightedGrade;
		double itemWeight;
		Map<Integer, TemplateQuestion> templateDataMap = new HashMap<>();
		Map<Integer, Set<Option>> userDataMap = new HashMap<>();
		
		for(TemplateQuestion tQuestion: assessment.getMyTemplate().getTemplateQuestion()){
			templateDataMap.put(tQuestion.getTemplateQuestionId(), tQuestion);
		}
		
		
		for(Option opt: assessment.getOptions()){
			if(userDataMap.containsKey(opt.getQuestion())){
				userDataMap.get(opt.getQuestion()).add(opt);
			}
			else {
				Set<Option> optSet = new HashSet<>();
				optSet.add(opt);
				userDataMap.put(opt.getQuestion(), optSet);
			}
		}
		
		int key;
		for(Entry<Integer, Set<Option>> entry: userDataMap.entrySet()){
			key = entry.getKey();
			itemWeight = templateDataMap.get(key).getWeight();
			
			int countCorrect = 0;
			int countOptions = 0;
			for(Option opt: userDataMap.get(key)){
				if(opt.getCorrect() == 1){
					countCorrect += 1;
				}
				countOptions += 1;
			}
			
			if(countOptions==0){
				countOptions = 1;
			}
			
			itemWeightedGrade = itemWeight*((double)countCorrect/(double)countOptions);//
			result[0] = result[0]+itemWeightedGrade;
			result[1] = result[1]+itemWeight;
		}
		return result;
	}
		
	public double[] gradeDragDrop(Assessment assessment){
		double[] result = new double[2];
		double itemWeightedGrade;
		double itemWeight;
		Map<Integer, TemplateQuestion> templateDataMap = new HashMap<>();
		Map<Integer, Set<AssessmentDragDrop>> userDataMap = new HashMap<>();
		
		
		for(TemplateQuestion tQuestion: assessment.getMyTemplate().getTemplateQuestion()){
			templateDataMap.put(tQuestion.getTemplateQuestionId(), tQuestion);
		}
		
		
		for(AssessmentDragDrop dragDrop: assessment.getAssessmentDragDrop()){
			if(userDataMap.containsKey(dragDrop.getDragDrop().getQuestionId())){
				userDataMap.get(dragDrop.getDragDrop().getQuestionId()).add(dragDrop);
			}
			else {
				Set<AssessmentDragDrop> dragDropSet = new HashSet<>();
				dragDropSet.add(dragDrop);
				userDataMap.put(dragDrop.getDragDrop().getQuestionId(), dragDropSet);
			}
		}

		int key;
		for(Entry<Integer, Set<AssessmentDragDrop>> entry: userDataMap.entrySet()){
			key = entry.getKey();
			itemWeight = templateDataMap.get(key).getWeight();
			
			int countCorrect = 0;
			int countOptions = 0;
			for(AssessmentDragDrop dragDrop: userDataMap.get(key)){
				if(dragDrop.getUserOrder() == dragDrop.getDragDrop().getCorrectOrder()){
					countCorrect += 1;
				}
				countOptions += 1;
			}
			
			if(countOptions==0){
				countOptions = 1;
			}
			
			itemWeightedGrade = itemWeight*((double)countCorrect/(double)countOptions);//
			result[0] = result[0]+itemWeightedGrade;
			result[1] = result[1]+itemWeight;
		}
		
		return result;
	}
		
	
	
	// This method should return the aggregate grades for all snippets
	public double[] gradeSnippet(Assessment assessment){
		SnippetEvaluationClient sec = new SnippetEvaluationClient();
		double[] result = new double[2];
		double itemWeightedGrade;
		double itemWeight;
		Map<Integer, FileUpload> userDataMap = new HashMap<>();
		Map<Integer, TemplateQuestion> templateDataMap = new HashMap<>(); 
		
		for(TemplateQuestion tQuestion: assessment.getMyTemplate().getTemplateQuestion()){
			templateDataMap.put(tQuestion.getTemplateQuestionId(), tQuestion);
		}
		
		for(FileUpload file : assessment.getFileUpload()){	
			userDataMap.put(file.getQuestionId(), file);
		}
		
		int key;
		for(Entry<Integer, FileUpload> entry: userDataMap.entrySet()){
			key = entry.getKey();
			String userFileName = userDataMap.get(key).getFileUrl();
			Set<SnippetTemplate> setST = templateDataMap.get(key).getTemplateQuestion().getSnippetTemplate();
			String keyFileName = setST.iterator().next().getSolutionUrl();
			itemWeight = templateDataMap.get(key).getWeight();
			int codeTestResult = sec.evaluateSnippet(userFileName, keyFileName);
			itemWeightedGrade = itemWeight*codeTestResult;//
			result[0] = result[0]+itemWeightedGrade;
			result[1] = result[1]+itemWeight;
		}
			
		return result;
	}
	
}