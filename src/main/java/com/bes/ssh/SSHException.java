package com.bes.ssh;

public class SSHException extends Exception {

	public SSHException() {
		super();
	}

	public SSHException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public SSHException(String message, Throwable cause) {
		super(message, cause);
	}

	public SSHException(String message) {
		super(message);
	}

	public SSHException(Throwable cause) {
		super(cause);
	}


}
