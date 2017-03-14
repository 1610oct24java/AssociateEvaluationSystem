package com.revature.hulq.util;

import java.io.Serializable;

public class TestProfile implements Serializable {
	private static final long serialVersionUID = 8477981913953145440L;
	
	//ignore case
	private boolean caseIgnore;
	//trim whitespace (leading and trailing)
	private boolean trimWhitespace;
	//extract whitespace (internal)
	private boolean extractWhitespace;
	//enable math mode (evaluates relative mathematic equality)
	private boolean mathMode;
	//sets tolerance for each case, if match < caseErrorMargin res=0
	private double caseErrorMargin;
	//sets tolerance for entire test, if avg of all runs < grossErrorMargin grade=0
	private double grossErrorMargin;
	//indicates file name required for the submitted code
	private String testFileName;
	//indicates file name required for the solution/key code
	private String keyFileName;
	
	public TestProfile() {
		super();
		this.setTrimWhitespace(false);
		this.setExtractWhitespace(false);
		this.setCaseIgnore(false);
		this.setMathMode(false);
		this.setCaseErrorMargin(0.0);
		this.setGrossErrorMargin(0.0);
		this.setKeyFileName("defaultKey");
		this.setTestFileName("defaultTest");
	}

	public boolean isCaseIgnore() {
		return caseIgnore;
	}

	public void setCaseIgnore(boolean caseIgnore) {
		this.caseIgnore = caseIgnore;
	}

	public boolean isTrimWhitespace() {
		return trimWhitespace;
	}

	public void setTrimWhitespace(boolean trimWhitespace) {
		this.trimWhitespace = trimWhitespace;
	}

	public boolean isExtractWhitespace() {
		return extractWhitespace;
	}

	public void setExtractWhitespace(boolean extractWhitespace) {
		this.extractWhitespace = extractWhitespace;
	}

	public boolean isMathMode() {
		return mathMode;
	}

	public void setMathMode(boolean mathMode) {
		this.mathMode = mathMode;
	}

	public double getCaseErrorMargin() {
		return caseErrorMargin;
	}

	public void setCaseErrorMargin(double caseErrorMargin) {
		this.caseErrorMargin = caseErrorMargin;
	}

	public double getGrossErrorMargin() {
		return grossErrorMargin;
	}

	public void setGrossErrorMargin(double grossErrorMargin) {
		this.grossErrorMargin = grossErrorMargin;
	}

	public String getTestFileName() {
		return testFileName;
	}

	public void setTestFileName(String testFileName) {
		this.testFileName = testFileName;
	}

	public String getKeyFileName() {
		return keyFileName;
	}

	public void setKeyFileName(String keyFileName) {
		this.keyFileName = keyFileName;
	}

	@Override
	public String toString() {
		return "TestProfile [caseIgnore=" + caseIgnore + ", trimWhitespace=" + trimWhitespace + ", extractWhitespace="
				+ extractWhitespace + ", mathMode=" + mathMode + ", caseErrorMargin=" + caseErrorMargin
				+ ", grossErrorMargin=" + grossErrorMargin + ", testFileName=" + testFileName + ", keyFileName="
				+ keyFileName + "]";
	}

	
	
	
	
	



	
	
	

}
