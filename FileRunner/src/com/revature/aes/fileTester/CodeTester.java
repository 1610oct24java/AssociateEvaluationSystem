package com.revature.aes.fileTester;

import java.util.List;


public class CodeTester {
	static BashDriver bd = new BashDriver();
	static FileParser fp = new FileParser();
	static FileAccess fa = new FileAccess();
	
/*		fa.upload("../failKey.cpp", "newFailKeyMike.cpp");
		fa.upload("../passKey.cpp", "newPassKeyMike.cpp");
		fa.upload("../test.cpp", "newTestMike.cpp");*/

	public boolean executeCodeTest(String keyFileKey, String testFileKey){
		boolean getKey = fa.download(keyFileKey);
		boolean getTest = fa.download(testFileKey);
		boolean result = false;
		if(getTest && getKey){
			List<String> arguments = fp.getArgs(keyFileKey);
			String output = bd.runCodeTestScript(keyFileKey,testFileKey, arguments);
			if(output.equals("pass")){
				result=true;
			}
		}
		return result;
	}

}
