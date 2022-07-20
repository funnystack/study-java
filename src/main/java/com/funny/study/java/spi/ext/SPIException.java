package com.funny.study.java.spi.ext;

import java.util.StringJoiner;

/**
 * SPI抛出的异常
 * @author
 *
 */
public class SPIException extends IllegalArgumentException{

	private static final long serialVersionUID = -7739252913896522898L;

	private String message;


	public SPIException(String message){
		this.message = message;
	}

	@Override
	public synchronized Throwable fillInStackTrace() {
		return null;
	}



	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Override
	public String toString() {
		return new StringJoiner(", ", SPIException.class.getSimpleName() + "[", "]")
				.add("message='" + message + "'")
				.toString();
	}
}
