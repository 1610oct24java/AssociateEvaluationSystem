package com.revature.aes.snippets;

public class RunSnippet {
	public static boolean compileJava(String fullyQualifiedClassName) {
		try {
			Runtime.getRuntime().exec("javac " + fullyQualifiedClassName + ".java");
			System.out.println("Compiling " + fullyQualifiedClassName);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}