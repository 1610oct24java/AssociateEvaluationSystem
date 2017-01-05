package fileTester;

import java.util.List;

public class CodeTester {
	static BashDriver bd = new BashDriver();
	static FileParser fp = new FileParser();
	static FileAccess fa = new FileAccess();
		
	public static void main(String args[]){
		List<String> arguments = fp.getArgs("newFailKeyMike.cpp");
		System.out.println(arguments.toString());
		String output = bd.runCodeTestScript("newFailKeyMike.cpp","newTestMike.cpp", arguments);
		System.out.println(output);
	}
	
	
	public static boolean executeCodeTest(String keyFileKey, String testFileKey){
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
