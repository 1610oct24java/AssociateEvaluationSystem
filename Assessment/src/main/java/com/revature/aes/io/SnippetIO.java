package com.revature.aes.io;

import java.io.File;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;

import com.revature.aes.util.Error;
import com.revature.aes.logging.Logging;

public class SnippetIO {

	static String s3Location = "aes.revature/";

	static Logging log = new Logging();
	
	public static void handleAmazonServiceException(AmazonServiceException ase){
		log.info("Caught an AmazonServiceException, which " + "means your request made it "
				+ "to Amazon S3, but was rejected with an error response" + " for some reason.");
		log.info("Error Message:    " + ase.getMessage());
		log.info("HTTP Status Code: " + ase.getStatusCode());
		log.info("AWS Error Code:   " + ase.getErrorCode());
		log.info("Error Type:       " + ase.getErrorType());
		log.info("Request ID:       " + ase.getRequestId());		
	}
	
	private static void handleAmazonClientException(AmazonClientException ace) {
		StackTraceElement thing = Thread.currentThread().getStackTrace()[1];
		Error.error("\nat Line:\t" + thing.getLineNumber() + "\nin Method:\t" + thing.getMethodName()
				+ "\nin Class:\t" + thing.getClassName(), ace);
		log.info("internal error while trying to communicate with S3");
		log.info("Error Message: " + ace.getLocalizedMessage());
	}

	public boolean upload(File file, String key) {
		AmazonS3 s3client = new AmazonS3Client();
		try {
			log.info("Uploading a new object to S3 from a file\n");
			s3client.putObject(new PutObjectRequest(s3Location, key, file));
			log.info("Done.");
			return true;
		} catch (AmazonServiceException ase) {
			SnippetIO.handleAmazonServiceException(ase);
			return false;
		} catch (AmazonClientException ace) {
			SnippetIO.handleAmazonClientException(ace);
			return false;
		}
	}


	public boolean download(String key) {
		AmazonS3 s3client = new AmazonS3Client();
		try {
			log.info("Downloading an object");
			File file = new File(key);

			s3client.getObject(new GetObjectRequest(s3Location, key), file);

			return true;
		} catch (AmazonServiceException ase) {
			SnippetIO.handleAmazonServiceException(ase);
			return false;
		} catch (AmazonClientException ace) {
			SnippetIO.handleAmazonClientException(ace);
			return false;
		}

	}
}
