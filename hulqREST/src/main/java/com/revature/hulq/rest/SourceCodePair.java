package com.revature.hulq.rest;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SourceCodePair
{
	private String testFileName;
	private String keyFileName;

	
	public SourceCodePair(@JsonProperty("submission")String testFileName, @JsonProperty("solution")String keyFileName) {
		super();
		this.testFileName = testFileName;
		this.keyFileName = keyFileName
		;
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
		return "SnippetRequestObject [testFileName=" + testFileName + ", keyFileName=" + keyFileName + "]";
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((keyFileName == null) ? 0 : keyFileName.hashCode());
		result = prime * result + ((testFileName == null) ? 0 : testFileName.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SourceCodePair other = (SourceCodePair) obj;
		if (keyFileName == null) {
			if (other.keyFileName != null)
				return false;
		} else if (!keyFileName.equals(other.keyFileName))
			return false;
		if (testFileName == null) {
			if (other.testFileName != null)
				return false;
		} else if (!testFileName.equals(other.testFileName))
			return false;
		return true;
	}
	
	
	
	
}