package com.revature.aes.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.revature.aes.beans.AssessmentRequest;
import com.revature.aes.io.SnippetIO;
import com.revature.aes.logging.Logging;

@Service
public class S3Service {

	@Autowired
	Logging log;

	@Autowired
	SnippetIO snippetIO;

	static String S3LOCATION = "aes.revature/";

	public boolean uploadToS3(String snippetContents, String key) {
		File file = new File("tempFile");
		PrintWriter printWriter = null;

		try {
			printWriter = new PrintWriter(file);

		  BufferedWriter writer = new BufferedWriter(printWriter);
		  writer.write(snippetContents);
		  writer.close();
			snippetIO.upload(file, key);
			log.info("=============== uploadToS3 ===============");
			log.info("------- snippetContents-------");
			log.info(snippetContents);
			log.info("------- End snippetContents-------");
			log.info("------- key-------");
			log.info(key);
			log.info("------- End key-------");
			log.info("=============== End uploadToS3 ===============");
			if (!file.delete()) {
				log.error("File not found! Can not delete file that does not exists!");
			}
			writer.close();
			return true;
		} catch(Exception e) {
			log.error(Logging.errorMsg("S3 Upload failed", e));
			return false;
		}finally {
			if (printWriter != null) {
				printWriter.close();
			}
		}

	}

	public boolean uploadAssReqToS3(AssessmentRequest assessContents, String key){
		File file2 = null;
		try (FileOutputStream file = new FileOutputStream(file2); ObjectOutputStream oos = new ObjectOutputStream(file)) {
			file2 = new File("AssessRequest");
			oos.writeObject(assessContents);
			

			new SnippetIO().upload(file2, key);
			if (!file2.delete()) {
				log.error("File not found! Can not delete file that does not exists!");
			}
			return true;
		}
		catch(Exception e)
		{
			log.error(Logging.errorMsg("uploading Assessment Req to S3", e));
			return false;
		}
	}

	public String readFromS3(String key) throws IOException {
		AmazonS3 s3client = new AmazonS3Client();
		log.info("=========== readFromS3 ===============");
		log.info(key);
		S3Object s3object = s3client.getObject(new GetObjectRequest(S3LOCATION, key));
		InputStreamReader streamreader = new InputStreamReader(s3object.getObjectContent());
		BufferedReader reader = new BufferedReader(streamreader);
		StringBuilder bld = new StringBuilder();
		String line;
		log.info(key);
		try {
			while ((line = reader.readLine()) != null) {
				bld.append(line + "\n");
			}
		} finally {
			streamreader.close();
		}
		log.info(bld.toString());
		log.info("=========== End readFromS3 ===============");

		return bld.toString();
	}
}
