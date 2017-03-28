package com.revature.aes.controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.aes.io.SnippetIO;
import com.revature.aes.logging.Logging;

@RestController
@RequestMapping("/rest")
public class SnippetController {

	@Autowired
	Logging log;

	@RequestMapping(value = "/s3upload/{key}")
	public boolean uploadToS3(String snippetContents, @RequestParam String key) throws IOException {
		File file;
		FileWriter fileWriter = null;
		try {
			file = File.createTempFile("snippet", ".tmp");
			fileWriter = new FileWriter(file);
			BufferedWriter writer = new BufferedWriter(fileWriter);
			writer.write(snippetContents);
			new SnippetIO().upload(file, key);
			writer.close();
			if (!file.delete()) {
				log.error("File not found! Can not delete file that does not exists!");
			}
			return true;
		} catch(Exception e){
			log.error(Logging.errorMsg("Could not upload to S3", e));
			return false;
		} finally {
			if (fileWriter != null) {
				fileWriter.close();
			}
		}

	}
}