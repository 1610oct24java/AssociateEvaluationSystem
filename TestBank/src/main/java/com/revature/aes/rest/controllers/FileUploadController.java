package com.revature.aes.rest.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.revature.aes.services.AssessmentGenService;

@RestController
public class FileUploadController {
	
	@Autowired
	private AssessmentGenService assGenService;
	
	@RequestMapping(value="/parseAiken", method=RequestMethod.POST)
	public void parseAikenFile(@RequestParam("file") MultipartFile file){
		assGenService.setFile(file);
		String fileName = file.getOriginalFilename();
		
		if(fileName.endsWith(".txt")){
			assGenService.uploadAikenFile();
		}else if(fileName.endsWith(".csv")){
			assGenService.uploadCSVFile();
		}else{
			// not a valid file type
		}
		
	}
}
