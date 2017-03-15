package com.revature.aes.io;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.revature.aes.logging.Logging;

@Component
public class SnippetIO {
	
	@Autowired
	Logging log;
	
	static String S3LOCATION = "aes.revature/";
	
	
	public boolean upload(File file, String key){
        AmazonS3 s3client = new AmazonS3Client();
        try {
            log.debug("Uploading a new object to S3 from a file\n");
            s3client.putObject(new PutObjectRequest(S3LOCATION, key, file));
            log.debug("Done.");
            return true;
         } catch (AmazonServiceException ase) {
            log.debug("Caught an AmazonServiceException, which " +
            		"means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            log.debug("Error Message:    " + ase.getMessage());
            log.debug("HTTP Status Code: " + ase.getStatusCode());
            log.debug("AWS Error Code:   " + ase.getErrorCode());
            log.debug("Error Type:       " + ase.getErrorType());
            log.debug("Request ID:       " + ase.getRequestId());
            log.stackTraceLogging(ase);
            return false;
        } catch (AmazonClientException ace) {
        	log.debug("Caught an AmazonClientException, which " +
            		"means the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
        	log.debug("Error Message: " + ace.getMessage());
        	log.stackTraceLogging(ace);
            return false;
        }
    }
	
	public boolean download(String key){
        AmazonS3 s3client = new AmazonS3Client();
        try {
            log.debug("Downloading an object");
            File file=new File(key);
                      
            s3client.getObject(new GetObjectRequest(S3LOCATION, key),file);
        
            return true;
         } catch (AmazonServiceException ase) {
        	 log.debug("Caught an AmazonServiceException, which " +
            		"means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
        	 log.debug("Error Message:    " + ase.getMessage());
        	 log.debug("HTTP Status Code: " + ase.getStatusCode());
        	 log.debug("AWS Error Code:   " + ase.getErrorCode());
        	 log.debug("Error Type:       " + ase.getErrorType());
        	 log.debug("Request ID:       " + ase.getRequestId());
        	 log.stackTraceLogging(ase);
            return false;
        } catch (AmazonClientException ace) {
        	log.debug("Caught an AmazonClientException, which " +
            		"means the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
        	log.debug("Error Message: " + ace.getMessage());
        	log.stackTraceLogging(ace);
            return false;
        }

	}
}
