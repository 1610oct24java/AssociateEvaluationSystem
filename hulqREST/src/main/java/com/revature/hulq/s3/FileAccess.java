package com.revature.hulq.s3;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.S3Object;


public class FileAccess {
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	//static private String S3LOCATION = "aes.revature/";
	static private String S3LOCATION = "hulq-bash";

	public boolean upload(String filename, String key){
        AmazonS3 s3client = new AmazonS3Client();
        try {
        	log.info("Uploading a new object to S3 from a file\n");
            File file = new File(filename);
            s3client.putObject(new PutObjectRequest(S3LOCATION, key, file));
            log.info("Done.");
            return true;
         } catch (AmazonServiceException ase) {
        	log.info("AmazonServiceException", ase);
            log.info("Caught an AmazonServiceException, which " +
            		"means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            log.info("Error Message:     " + ase.getMessage());
            log.info("HTTP Status Code:   " + ase.getStatusCode());
            log.info("AWS Error Code:    " + ase.getErrorCode());
            log.info("Error Type:         " + ase.getErrorType());
            log.info("Request ID:         " + ase.getRequestId());
            return false;
        } catch (AmazonClientException ace) {
        	log.info("AmazonClientException", ace);
            log.info("Caught an AmazonClientException, which " +
            		"means the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
            log.info("Error Message:  " + ace.getMessage());
            return false;
        }
    }
	
	public boolean download(String key){
		log.info("======== DOWNLOADING FILES FROM S3 ========");
        AmazonS3 s3client = new AmazonS3Client();
        try {
        	log.info("Downloading an object: " + key);
            File file=new File(key);
                      
            s3client.getObject(new GetObjectRequest(S3LOCATION, key),file);
            
            log.info("====== END DOWNLOADING FILES FROM S3 ======\n");
        
            return true;
         } catch (AmazonServiceException ase) {
        	log.info("AmazonServiceException", ase);
            log.info("Caught an AmazonServiceException, which " +
            		"means your request made it " +
                    "to Amazon S3, but was rejected with an error response" +
                    " for some reason.");
            log.info("Error Message:    " + ase.getMessage());
            log.info("HTTP Status Code: " + ase.getStatusCode());
            log.info("AWS Error Code:   " + ase.getErrorCode());
            log.info("Error Type:       " + ase.getErrorType());
            log.info("Request ID:       " + ase.getRequestId());
            log.info("Item:             " + key);
            log.info("====== END DOWNLOADING FILES FROM S3 (AMAZON SERVICE EXCEPTION) ======\n");
            return false;
        } catch (AmazonClientException ace) {
        	log.info("AmazonClientException", ace);
        	log.info("Caught an AmazonClientException, which " +
            		"means the client encountered " +
                    "an internal error while trying to " +
                    "communicate with S3, " +
                    "such as not being able to access the network.");
            log.info("Error Message: " + ace.getMessage());
            log.info("====== END DOWNLOADING FILES FROM S3 (AMAZON CLIENT EXCEPTION) ======\n");
            return false;
        }

	}
	
	public StringBuilder readFromS3(String key){
        AmazonS3 s3client = new AmazonS3Client();
	    S3Object s3object = s3client.getObject(new GetObjectRequest(
	            S3LOCATION, key));
	    StringBuilder fileContent = new StringBuilder();
	    BufferedReader reader = new BufferedReader(new InputStreamReader(s3object.getObjectContent()));
	    String line;
	    try {
			while((line = reader.readLine()) != null) {
			  fileContent = fileContent.append(line + "\n");
			}
		} catch (IOException e) {
			log.info("IOException", e);
		}
	    finally
	    {
	    	try
			{
				reader.close();
			} catch (IOException e)
			{
				log.info("IOException", e);
			}
	    }
		return fileContent;
	}
	

}
