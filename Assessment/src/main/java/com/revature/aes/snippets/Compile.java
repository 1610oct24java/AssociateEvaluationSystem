package com.revature.aes.snippets;

public class Compile {
	public static boolean compileJava(String fullyQualifiedClassName) {
		try {
			Runtime.getRuntime().exec("javac " + fullyQualifiedClassName + ".java");
			System.out.println("Compiling " + fullyQualifiedClassName);
			Runtime.getRuntime().exec("java " + fullyQualifiedClassName);
			System.out.println("Running " + fullyQualifiedClassName);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}