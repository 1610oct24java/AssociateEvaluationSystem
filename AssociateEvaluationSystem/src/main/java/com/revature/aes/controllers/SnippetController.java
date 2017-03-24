package com.revature.aes.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.revature.aes.io.SnippetIO;
import com.revature.aes.logging.Logging;


@RestController
@RequestMapping("/rest")
public class SnippetController {
	
	@Autowired
	Logging log;
	
	// key should be a string with the appropriate file extension
	@RequestMapping(value = "/s3uploadTextAsFile/{folderName}/{key:.+}", method=RequestMethod.POST)
	public String uploadToS3(@PathVariable String folderName, @PathVariable String key, @RequestBody String snippetContents){
		System.out.println("\n\n\n\n\n Snippet Contents: "+snippetContents+"\n\n\n\n\n\n\n\n");
		System.out.println("folderName: "+folderName);
		System.out.println("key: "+key);
		try{
			File file = File.createTempFile("snippet", ".tmp");
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write(snippetContents);
			fileWriter.flush();
			fileWriter.close();
			Scanner scan = new Scanner(file);
			while(scan.hasNextLine()){
				System.out.println(scan.nextLine());
			}
			scan.close();
			new SnippetIO().upload(file, folderName + "/" + key);
			
			if (!file.delete())
			{
				log.error("File not found! Can not delete file that does not exist!");
			}
			return "true";
		}
		 catch (IOException e) {
			log.stackTraceLogging(e);
			return "false";
		}
	}
	
	@ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/s3uploadFile/{folderName}", method=RequestMethod.POST)
    public void uploadFileToS3(@RequestParam("file") MultipartFile file, @PathVariable("folderName") String folderName) throws IOException {
        System.out.println(String.format("receive %s", file.getOriginalFilename()));
        new SnippetIO().upload(convert(file, folderName), folderName + "/" + file.getOriginalFilename());
    }
	
	private File convert(MultipartFile file, String folderName) throws IOException
	{    
	    File convFile = new File(file.getOriginalFilename());
	    convFile.createNewFile(); 
	    FileOutputStream fos = new FileOutputStream(convFile); 
	    fos.write(file.getBytes());
	    fos.close(); 
	    return convFile;
	}
	
}