package com.revature.aes.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.revature.aes.io.SnippetIO;

@Service
public class S3Service {
	
	static String S3LOCATION = "aes.revature/";;

	public boolean uploadToS3(String snippetContents, String key) {
		File file = new File("tempFile");
		try {
		    BufferedWriter writer = new BufferedWriter(new PrintWriter(file));
		    writer.write(snippetContents);
		    writer.close();
			new SnippetIO().upload(file, key);
			file.delete();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@PreAuthorize("hasRole('TRUSTED_REST_USER')")
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
            e.printStackTrace();
        }
        return fileContent;
    }
}
