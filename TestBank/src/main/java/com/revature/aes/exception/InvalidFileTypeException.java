package com.revature.aes.exception;

public class InvalidFileTypeException extends Exception{
	private static final long serialVersionUID = -4945528914407366931L;
	
	public InvalidFileTypeException(String message){
		super(message);
	}
}
