//package com.revature.aes.test;
//
//import static org.junit.Assert.*;
//
//import java.io.File;
//import java.io.IOException;
//import java.io.PrintWriter;
//
//import org.junit.Test;
//
//import junit.framework.TestCase;
//
//public class AikenParserTest extends TestCase{
//
//	@Override
//	protected void setUp() throws Exception{
//		buildTextFile();
//	}
//	
//	@Test
//	public void test() {
//		fail("Not yet implemented");
//	}
//	
//	@Override
//	protected void tearDown() throws Exception{
//		destroyTextFile();
//	}
//	
//	private void buildTextFile(){
//		try{
//		    PrintWriter writer = new PrintWriter("aikenDemo.txt", "UTF-8");
//		    writer.println("What is the correct answer to this question?");
//		    writer.println("A. Is it this one?");
//		    writer.println("B. Maybe this answer?");
//		    writer.println("C. Possibly this one?");
//		    writer.println("D. Must be this one!");
//		    writer.println("ANSWER: D");
//		    writer.println();
//		    writer.println("What is the best programming language?");
//		    writer.println("A) Java");
//		    writer.println("B) Python?");
//		    writer.println("C) Perl");
//		    writer.println("D) HTML?!");
//		    writer.println("E) Cocaine");
//		    writer.println("F) C#");
//		    writer.println("ANSWER: A");
//		    writer.close();
//		} catch (IOException e) {
//		   e.printStackTrace();
//		}
//	}
//	
//	private void destroyTextFile(){
//		try{
//    		File file = new File("aikenDemo.txt");
//    		if(file.delete()){
//    			System.out.println(file.getName() + " is deleted!");
//    		}else{
//    			System.out.println("Delete operation is failed.");
//    		}
//    	}catch(Exception e){
//    		e.printStackTrace();
//    	}
//	}
//
//}
