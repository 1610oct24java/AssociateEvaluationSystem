package com.revature.aes.controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	
	@RequestMapping(value = "/s3upload/{key}")
	public boolean uploadToS3(String snippetContents, @RequestParam String key){
		File file;
		FileWriter fileWriter = null;
		try{
			try {
				file = File.createTempFile("snippet", ".tmp");
				fileWriter = new FileWriter(file);
				BufferedWriter writer = new BufferedWriter(fileWriter);
				writer.write(snippetContents);
				new SnippetIO().upload(file, key);
				writer.close();
				if(!file.delete()){
					log.error("File not found! Can not delete file that does not exists!");
				}
				return true;
			}finally{
				if(fileWriter != null){
					fileWriter.close();
				}
			}
		}
		 catch (IOException e) {
			log.stackTraceLogging(e);
			return false;
		}
	}
	
	@ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/s3uploadFile", method=RequestMethod.POST)
    public void uploadFileToS3(@RequestParam("file") MultipartFile file) throws IOException {
        System.out.println(String.format("receive %s", file.getOriginalFilename()));
        new SnippetIO().upload(convert(file), ".java");
    }
	
	private File convert(MultipartFile file) throws IOException
	{    
	    File convFile = new File(file.getOriginalFilename());
	    convFile.createNewFile(); 
	    FileOutputStream fos = new FileOutputStream(convFile); 
	    fos.write(file.getBytes());
	    fos.close(); 
	    return convFile;
	}
	
}