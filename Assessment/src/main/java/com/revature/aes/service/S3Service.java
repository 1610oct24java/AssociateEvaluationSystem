package com.revature.aes.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.stereotype.Service;

import com.revature.aes.io.SnippetIO;

@Service
public class S3Service {

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
}
