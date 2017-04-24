package com.revature.aes.io;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.revature.aes.logging.Logging;

@Component
public class SnippetIO {

	@Autowired
	Logging log;

	static private String S3LOCATION = "aes.revature/";

	public boolean upload(File file, String key) {
		AmazonS3 s3client = new AmazonS3Client();
		try{
			log.debug("Uploading a new object to S3 from a file\n");
			s3client.putObject(new PutObjectRequest(S3LOCATION, key, file));
			log.debug("Done.");
			return true;
		}catch(Exception e){
			log.error(Logging.errorMsg("SnippetIO upload to S3 failed", e));
			return false;
		}
	}

	public boolean download(String key) {
		AmazonS3 s3client = new AmazonS3Client();
		try{
			log.debug("Downloading an object");
			File file = new File(key);

			s3client.getObject(new GetObjectRequest(S3LOCATION, key), file);

			return true;
		}catch(Exception e){
			log.error(Logging.errorMsg("SnippetIO download from S3 failed", e));
			return false;
		}

	}
}
