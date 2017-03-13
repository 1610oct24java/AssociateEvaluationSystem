package com.revature.aes.beans;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SerilizeAssessment {

  public static void main(String args[]) {

    SerilizeAssessment obj = new SerilizeAssessment();
	
	AssessmentRequestProperties ARP = new AssessmentRequestProperties();
	
	ARP.setCat1("Java");
	ARP.setCat1mcQuestions(1);
	ARP.setCat1msQuestions(1);
	ARP.setCat1ddQuestions(1);
	ARP.setCat1csQuestions(1);
	
	ARP.setCat2("OOP");
	ARP.setCat2mcQuestions(2);
	ARP.setCat2msQuestions(0);
	ARP.setCat2ddQuestions(0);
	ARP.setCat2csQuestions(0);

	ARP.setCat3("SQL");
	ARP.setCat3mcQuestions(2);
	ARP.setCat3msQuestions(0);
	ARP.setCat3ddQuestions(0);
	ARP.setCat3csQuestions(0);
	
	ARP.setCat4("JavaScript");
	ARP.setCat4mcQuestions(2);
	ARP.setCat4msQuestions(0);
	ARP.setCat4ddQuestions(0);
	ARP.setCat4csQuestions(0);
	
	ARP.setTimelimit(69);
	
	obj.serializeAssessment(ARP);
}

public void serializeAssessment(AssessmentRequestProperties ARP) {

	FileOutputStream fo = null;
	ObjectOutputStream os = null;

	try {

		fo = new FileOutputStream("C:/Users/Ramiz/Desktop/Notes/AssessmentProperties.ser");
		os = new ObjectOutputStream(fo);
		os.writeObject(ARP);

		System.out.println("Finished");

	} catch (Exception ex) {

		ex.printStackTrace();

	} finally {

		if (fo != null) {
			try {
				fo.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (os != null) {
			try {
				os.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
  }
}