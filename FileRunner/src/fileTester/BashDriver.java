package fileTester;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class BashDriver {
	FileAccess fa = new FileAccess();
	public String runCodeTestScript(String keyPath, String testPath, List<String> arguments){
		List<String> command = new ArrayList<String>();
		command.add("/bin/bash");
		command.add("codeTester.sh");
		command.add(keyPath);
		command.add(testPath);
		command.addAll(arguments);
		
		String result = "pass";
		
		File scriptFile = new File("codeTester.sh");
		if(!scriptFile.exists()){
			fa.download("codeTester.sh");
		}
				
		try{
			System.out.println(command.toString());
			ProcessBuilder pb = new ProcessBuilder(command);
			Process p = pb.start();
			BufferedReader in =
        	new BufferedReader(new InputStreamReader(p.getInputStream()));
    		String inputLine;
    		while ((inputLine = in.readLine()) != null) {
        		result = inputLine;
        		System.out.println("result:"+result);
    		}
			
    		in.close();

		} catch (IOException e) {
        	e.printStackTrace();
    	}
		
		return result;
		
	}
}
