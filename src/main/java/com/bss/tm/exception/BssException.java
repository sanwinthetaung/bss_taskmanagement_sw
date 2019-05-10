package com.bss.tm.exception;

public class BssException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BssException() {
		super();
	}

	public BssException(String message) {
		super(message);
	}


	public BssException(String message, Throwable cause) {
		super(message, cause);
	}
}
