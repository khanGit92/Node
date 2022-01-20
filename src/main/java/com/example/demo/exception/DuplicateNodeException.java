package com.example.demo.exception;


public class DuplicateNodeException extends RuntimeException{
	
	public DuplicateNodeException(String reason) {
		super(reason);
	}
}
