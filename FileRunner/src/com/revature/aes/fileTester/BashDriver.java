package com.revature.aes.fileTester;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BashDriver {
	public String runCodeTestScript(String keyPath, String testPath, List<String> arguments){
		List<String> command = new ArrayList<String>();
		command.add("/bin/bash");
		command.add("test.sh");
		command.add(keyPath);
		command.add(testPath);
		command.addAll(arguments);
		System.out.println(command.toString());
		String result = "fail";
		try{
			ProcessBuilder pb = new ProcessBuilder(command);
			Process p = pb.start();
			BufferedReader in =
        	new BufferedReader(new InputStreamReader(p.getInputStream()));
    		String inputLine;
    		while ((inputLine = in.readLine()) != null) {
        		result = inputLine;
    		}
			
    		in.close();

		} catch (IOException e) {
        	e.printStackTrace();
    	}
		
		return result;
		
	}
}
