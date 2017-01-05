package com.revature.aes.controllers;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SnippetRequestObject
{
	private String snippet;
	private String solution;

	public String getSnippet() {
		return snippet;
	}
	public void setSnippet(String snippet) {
		this.snippet = snippet;
	}
	public String getSolution() {
		return solution;
	}
	public void setSolution(String solution) {
		this.solution = solution;
	}
	@Override
	public String toString() {
		return "SnippetRequestObject [snippet=" + snippet + ", solution=" + solution + "]";
	}
	public SnippetRequestObject(@JsonProperty("snippet")String snippet, @JsonProperty("solution")String solution) {
		super();
		this.snippet = snippet;
		this.solution = solution;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((snippet == null) ? 0 : snippet.hashCode());
		result = prime * result + ((solution == null) ? 0 : solution.hashCode());
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
		SnippetRequestObject other = (SnippetRequestObject) obj;
		if (snippet == null) {
			if (other.snippet != null)
				return false;
		} else if (!snippet.equals(other.snippet))
			return false;
		if (solution == null) {
			if (other.solution != null)
				return false;
		} else if (!solution.equals(other.solution))
			return false;
		return true;
	}
	public SnippetRequestObject() {
	}
}