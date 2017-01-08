package com.revature.aes.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import org.springframework.stereotype.Service;

import com.revature.aes.io.SnippetIO;
import com.revature.aes.util.Error;

@Service
public class S3Service {

	public boolean uploadToS3(String snippetContents, String key) {
		File file = new File("tempFile");
		try(BufferedWriter writer =
                   new BufferedWriter(new PrintWriter(file))) {
			writer.write(snippetContents);
			new SnippetIO().upload(file, key);
			return file.delete();
		} catch (IOException e) {
			StackTraceElement thing = Thread.currentThread().getStackTrace()[1];
			Error.error("\nat Line:\t" + thing.getLineNumber() + "\nin Method:\t" + thing.getMethodName()
					+ "\nin Class:\t" + thing.getClassName(), e);
			return false;
		}
	}
}
