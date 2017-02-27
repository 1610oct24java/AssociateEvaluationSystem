package com.revature.aes.beans;

import java.io.Serializable;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class Packet implements Serializable{
	
	private static final long serialVersionUID = -4093107248683252129L;
	
	private Assessment assessment;
	private List<SnippetUpload> snippetUpload;
	
	public Packet() {
		super();
	}
	
	public Packet(Assessment assessment, List<SnippetUpload> snippetUpload) {
		super();
		this.assessment = assessment;
		this.snippetUpload = snippetUpload;
	}

	public Assessment getAssessment() {
		return assessment;
	}

	public void setAssessment(Assessment assessment) {
		this.assessment = assessment;
	}


	public List<SnippetUpload> getSnippetUpload() {
		return snippetUpload;
	}


	public void setSnippetUpload(List<SnippetUpload> snippetUpload) {
		this.snippetUpload = snippetUpload;
	}

	@Override
	public String toString() {
		return "Packet [assessment=" + assessment + ", snippetUpload=" + snippetUpload + "]";
	}
}