package com.exame.livraria.persistence.exception;

@SuppressWarnings("serial")
public class BookNotFoundException extends RuntimeException {

	public BookNotFoundException(String msg) {
		super(msg);
	}
	public BookNotFoundException(String msg, Throwable cause) {
		super(msg, cause);
	}
}
