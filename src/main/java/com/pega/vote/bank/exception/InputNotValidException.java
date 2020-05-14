package com.pega.vote.bank.exception;

public class InputNotValidException extends Exception {

	/**
	 * Exception for client Input
	 */
	private static final long serialVersionUID = 1L;
	
	public InputNotValidException(String msg) {
		super(msg);
	}

}
