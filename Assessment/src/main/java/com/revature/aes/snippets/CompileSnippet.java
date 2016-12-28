package com.revature.aes.snippets;

public class CompileSnippet {
	public static boolean compileJava(String fullyQualifiedClassName) {
		try {
			Runtime.getRuntime().exec("java " + fullyQualifiedClassName);
			System.out.println("Running " + fullyQualifiedClassName);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}