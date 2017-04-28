package com.revature.hulq.rest;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.revature.hulq.bash.BashDriver;
import com.revature.hulq.s3.FileAccess;
import com.revature.hulq.util.FileParser;
import com.revature.hulq.util.TestProfile;

@Component
public class HulqDriver {
	static BashDriver bd = new BashDriver();
	static FileParser fp = new FileParser();
	static FileAccess fa = new FileAccess();
	private final Logger log = LoggerFactory.getLogger(this.getClass());
		
	
	public double executeCodeTest(String keyFileKey, String testFileKey){
		boolean getKey = fa.download(keyFileKey);
		boolean getTest = fa.download(testFileKey);
		log.info("key:" + keyFileKey + " test:" + testFileKey);
		//create temp file to check if script file is present
		File varTmpDir = new File("hulqBASH.sh");
		//check if script file exists in directory
		boolean hasScript = varTmpDir.exists();
		final String BASHTAR = "hulqBASH.tar.gz";
		
		if (!hasScript){
			//download script, false if fails
			fa.download(BASHTAR);
			//extract script, false if fails
			hasScript = extractBash(BASHTAR);
			//remove tar.gz file from directory
			removeTar(BASHTAR);
		}
		
		
		double result = 100.0;
		
		//if files downloaded from s3 successfully, and script exists 
		if(getTest && getKey && hasScript){
			List<String> arguments = fp.getArgs(keyFileKey);
			TestProfile testProfile = fp.getTestProfile(keyFileKey);
			// rename key and test files
			
			File oldKey = new File(keyFileKey);
			String newKeyName = testProfile.getKeyFileName();
			File newKey = new File(newKeyName);
			boolean renamedKey = oldKey.renameTo(newKey);
		
			File oldTest = new File(testFileKey);
			// rename user code with filename from config and extension from submission
			// this allows the candidate to define what language they wish to use
			String newTestName = testProfile.getTestFileName()+"."+testFileKey.split("\\.")[1];
			File newTest = new File(newTestName);
			boolean renamedTest = oldTest.renameTo(newTest);
			
			log.info(renamedKey + "-newKey:" + newKeyName +"[=]"+ renamedTest+ " newTest:" + newTestName);
			
			//if both files were successfully renamed
			if(renamedKey && renamedTest){
				log.info("bacon");
				result = bd.gradeCode(newKeyName, newTestName, arguments, testProfile);
			}
		} 
		
		return result;
	}
	
	public boolean extractBash(String tarPath){
		String command = "tar";
		String mods = "-xvzf";
		ProcessBuilder pb = new ProcessBuilder(command, mods, tarPath);
		try {
			pb.start();
			return true;
		} catch (IOException e) {
			log.info("IOException", e);
			return false;
		}
	}
	
	public boolean removeTar(String tarPath){
		String command = "rm";
		ProcessBuilder pb = new ProcessBuilder(command, tarPath);
		try {
			pb.start();
			return true;
		} catch (IOException e) {
			log.info("IOException", e);
			return false;
		}
	}

}
