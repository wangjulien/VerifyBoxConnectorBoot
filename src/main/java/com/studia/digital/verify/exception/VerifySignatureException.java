package com.studia.digital.verify.exception;

public class VerifySignatureException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4082873018802597392L;

	public VerifySignatureException() {
		super();
	}

	public VerifySignatureException(String message, Throwable cause, boolean enableSuppression,
			boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public VerifySignatureException(String message, Throwable cause) {
		super(message, cause);
	}

	public VerifySignatureException(String message) {
		super(message);
	}

	public VerifySignatureException(Throwable cause) {
		super(cause);
	}

}
