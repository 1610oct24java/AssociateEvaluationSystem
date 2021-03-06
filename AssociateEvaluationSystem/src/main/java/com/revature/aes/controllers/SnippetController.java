package com.revature.aes.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

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
	
	// key will be the name of the file on the S3, and should be a string with the appropriate file extension
	@RequestMapping(value = "/s3uploadTextAsFile/{folderName}/{key:.+}", method=RequestMethod.POST)
	public String uploadToS3(@PathVariable String folderName, @PathVariable String key, @RequestBody String snippetContents){

		File file = null;

		try(FileWriter fileWriter = new FileWriter(file)){
			file= File.createTempFile("snippet", ".tmp");
			fileWriter.write(snippetContents);
			fileWriter.flush();
			new SnippetIO().upload(file, folderName + "/" + key);
			if (!file.delete())
			{
				log.error("File not found! Can not delete file that does not exist!");
			}
			return "true";
		}
		catch(Exception e){
			log.error(Logging.errorMsg("Could not upload to S3", e));
			return "false";
		}

	}
	
	@ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "/s3uploadFile/{folderName}", method=RequestMethod.POST)
    public void uploadFileToS3(@RequestParam("file") MultipartFile mpfile, @PathVariable("folderName") String folderName) throws IOException {
        File file = convert(mpfile, folderName);
		new SnippetIO().upload(file, folderName + "/" + mpfile.getOriginalFilename());
		if (!file.delete())
		{
			log.error("File not found! Can not delete file that does not exist!");
		}
    }
	
	private File convert(MultipartFile file, String folderName) throws IOException
	{    
	    File convFile = File.createTempFile(file.getOriginalFilename(),".tmp");
	    convFile.createNewFile(); 
	    FileOutputStream fos = new FileOutputStream(convFile); 
	    fos.write(file.getBytes());
	    fos.close(); 
	    return convFile;
	}
	
}