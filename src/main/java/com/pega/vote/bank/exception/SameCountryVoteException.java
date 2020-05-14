package com.pega.vote.bank.exception;

public class SameCountryVoteException extends Exception {

	/**
	 * voting for own country
	 */
	private static final long serialVersionUID = 1L;
	
	public SameCountryVoteException(String msg) {
		super(msg);
	}
}