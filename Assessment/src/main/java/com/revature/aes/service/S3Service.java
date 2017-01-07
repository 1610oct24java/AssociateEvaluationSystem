package com.revature.aes.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import com.revature.aes.util.Error;

import org.springframework.stereotype.Service;

import com.revature.aes.io.SnippetIO;

@Service
public class S3Service {

	public boolean uploadToS3(String snippetContents, String key) {
		File file = new File("tempFile");
		try {
			PrintWriter pwriter = new PrintWriter(file);
			BufferedWriter writer = new BufferedWriter(pwriter);
			writer.write(snippetContents);
			writer.close();
			pwriter.close();
			new SnippetIO().upload(file, key);
			file.delete();
			return true;
		} catch (IOException e) {
			StackTraceElement thing = Thread.currentThread().getStackTrace()[1];
			Error.error("\nat Line:\t" + thing.getLineNumber() + "\nin Method:\t" + thing.getMethodName()
					+ "\nin Class:\t" + thing.getClassName(), e);
			return false;
		}
	}
}
