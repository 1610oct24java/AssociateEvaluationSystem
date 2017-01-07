package com.revature.aes.controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.aes.io.SnippetIO;
import com.revature.aes.util.Error;

@RestController
@RequestMapping("/rest")
public class SnippetController {

	@RequestMapping(value = "/s3upload/{key}")
	public boolean uploadToS3(String snippetContents, @RequestParam String key) {
		File file;
		try {
			file = File.createTempFile("snippet", ".tmp");
			FileWriter fwriter = new FileWriter(file);
			BufferedWriter writer = new BufferedWriter(fwriter);
			writer.write(snippetContents);
			new SnippetIO().upload(file, key);
			writer.close();
			fwriter.close();
			return file.delete();
		} catch (IOException e) {
			StackTraceElement thing = Thread.currentThread().getStackTrace()[1];
			Error.error("\nat Line:\t" + thing.getLineNumber() + "\nin Method:\t" + thing.getMethodName()
					+ "\nin Class:\t" + thing.getClassName(), e);
			return false;
		}
	}
}