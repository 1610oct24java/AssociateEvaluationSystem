package com.revature.aes.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.revature.aes.io.SnippetIO;
import com.revature.aes.logging.Logging;

@Service
public class S3Service {
	
	@Autowired
	Logging log;
	
	static String S3LOCATION = "aes.revature/";

	public boolean uploadToS3(String snippetContents, String key) {
		File file = new File("tempFile");
		PrintWriter printWriter = null;
		
		try {
			printWriter = new PrintWriter(file);
		    BufferedWriter writer = new BufferedWriter(printWriter);
		    writer.write(snippetContents);
		    //writer.close();
			new SnippetIO().upload(file, key);
			if(!file.delete()){
				log.error("File not found! Can not delete file that does not exists!");
			}
			writer.close();
			return true;
		} catch (IOException e) {
			log.stackTraceLogging(e);
			return false;
		} finally{
			printWriter.close();
		}
	}
	
	public String readFromS3(String key){
        AmazonS3 s3client = new AmazonS3Client();
        S3Object s3object = s3client.getObject(new GetObjectRequest(
                S3LOCATION, key));
        InputStreamReader streamreader = new InputStreamReader(s3object.getObjectContent());
        BufferedReader reader = new BufferedReader(streamreader);
        StringBuilder bld = new StringBuilder();
        String line;
        try {
            while((line = reader.readLine()) != null) {
            	bld.append(line + "\n");
            }
            streamreader.close();
        } catch (IOException e) {
        	log.stackTraceLogging(e);
        }
        
        return bld.toString();
    }
}
