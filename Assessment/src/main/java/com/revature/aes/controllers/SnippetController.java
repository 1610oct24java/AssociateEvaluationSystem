package com.revature.aes.controllers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.revature.aes.io.SnippetIO;

@RestController
@RequestMapping("/rest")
public class SnippetController {
	
	@RequestMapping(value = "/s3upload/{key}")
	public boolean uploadToS3(String snippetContents, @RequestParam String key){
		File file;
		try {
			file = File.createTempFile("snippet", ".tmp");
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			writer.write(snippetContents);
			new SnippetIO().upload(file, key);
			writer.close();
			file.delete();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}
}