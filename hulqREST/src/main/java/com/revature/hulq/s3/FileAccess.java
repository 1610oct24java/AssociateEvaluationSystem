package com.revature.hulq.s3;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.springframework.stereotype.Component;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;


public class FileAccess {
	
	static private String S3LOCATION = "aes.revature/";

	public boolean upload(String filename, String key){
        AmazonS3 s3client = new AmazonS3Client();
        try {
            System.out.println("Uploading a new object to S3 from a file\n");
            File file = new File(filename);
            s3client.putObject(new PutObjectRequest(S3LOCATION, key, file));
            System.out.println("Done.");
            return true;
         } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which " +
            		"means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
            return false;
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which " +
            		"means the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
            return false;
        }
    }
	
	public boolean download(String key){
		System.out.println("=== DOWNLOADING FILES FROM S3 ===");
        AmazonS3 s3client = new AmazonS3Client();
        try {
            System.out.println("Downloading an object: " + key);
            File file=new File(key);
                      
            s3client.getObject(new GetObjectRequest(S3LOCATION, key),file);
            
            System.out.println("=== END DOWNLOADING FILES FROM S3 ===\n");
        
            return true;
         } catch (AmazonServiceException ase) {
            System.out.println("Caught an AmazonServiceException, which " +
            		"means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            System.out.println("Error Message:    " + ase.getMessage());
            System.out.println("HTTP Status Code: " + ase.getStatusCode());
            System.out.println("AWS Error Code:   " + ase.getErrorCode());
            System.out.println("Error Type:       " + ase.getErrorType());
            System.out.println("Request ID:       " + ase.getRequestId());
            System.out.println("Item:             " + key);
            System.out.println("=== END DOWNLOADING FILES FROM S3 (AMAZON SERVICE EXCEPTION) ===\n");
            return false;
        } catch (AmazonClientException ace) {
            System.out.println("Caught an AmazonClientException, which " +
            		"means the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
            System.out.println("Error Message: " + ace.getMessage());
            System.out.println("=== END DOWNLOADING FILES FROM S3 (AMAZON CLIENT EXCEPTION) ===\n");
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
			e.printStackTrace();
		}
		return fileContent;
	}
	

}
