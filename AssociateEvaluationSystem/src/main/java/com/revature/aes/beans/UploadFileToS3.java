package com.revature.aes.beans;

import java.io.File;

import com.amazonaws.AmazonClientException;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.s3.transfer.TransferManager;
import com.amazonaws.services.s3.transfer.Upload;

public class UploadFileToS3 {

	public static void main(String[] args) throws Exception {
        
		String existingBucketName = "";
        String keyName            = "";
        String filePath           = "C://AssessmentProperties.ser";  
        
        TransferManager tm = new TransferManager(new ProfileCredentialsProvider());           
        Upload upload = tm.upload(existingBucketName, keyName, new File(filePath));
     
        try {
        	upload.waitForCompletion();
        	System.out.println("Upload complete.");
        } catch (AmazonClientException amazonClientException) {
        	System.out.println("Upload failed.");
        	amazonClientException.printStackTrace();
        }
    }
}

	

