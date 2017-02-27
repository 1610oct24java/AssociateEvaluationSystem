package com.revature.aes.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import com.revature.aes.logging.Logging;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.revature.aes.io.SnippetIO;

@Service
public class S3Service {
	
	static String S3LOCATION = "aes.revature/";
    @Autowired
    Logging log;

	public boolean uploadToS3(String snippetContents, String key) {
		File file = new File("tempFile");
		try {
		    BufferedWriter writer = new BufferedWriter(new PrintWriter(file));
		    writer.write(snippetContents);
		    writer.close();
			new SnippetIO().upload(file, key);
//			file delete()
			return true;
		} catch (IOException e) {
		    log.error(e.getMessage());
			return false;
		}
	}
	
	public String readFromS3(String key){
        AmazonS3 s3client = new AmazonS3Client();
        S3Object s3object = s3client.getObject(new GetObjectRequest(
                S3LOCATION, key));
        String fileContent="";
        BufferedReader reader = new BufferedReader(new InputStreamReader(s3object.getObjectContent()));
        String line;
        try {
            while((line = reader.readLine()) != null) {
              fileContent = fileContent + line + "\n";
            }
        } catch (IOException e) {
        	log.error(e.getMessage());
        }
        return fileContent;
    }
}
