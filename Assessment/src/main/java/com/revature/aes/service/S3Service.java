package com.revature.aes.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.stereotype.Service;

import com.revature.aes.io.SnippetIO;

@Service
public class S3Service {
	
	public boolean uploadToS3(String snippetContents, String key){
		File file;
		try {
			System.out.println("Upload contents: " + snippetContents);
			file = File.createTempFile("snippet", ".tmp");
			file.deleteOnExit();
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(snippetContents);
			new SnippetIO().upload(file, key);
			writer.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}
