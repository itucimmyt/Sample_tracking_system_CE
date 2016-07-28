package com.cimmyt.exception;

@SuppressWarnings("serial")
public class BackException extends Exception{

	private String message;

	public BackException(){
		super();
	}

	public BackException (String message){
		super(message);
		this.message = message;
	}
}
