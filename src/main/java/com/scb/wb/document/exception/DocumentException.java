package com.scb.wb.document.exception;

public class DocumentException extends Exception {

	private static final long serialVersionUID = -3429163039326371575L;

	private String errorCode;

	public DocumentException() {
		super();
	}

	public DocumentException(final String message) {
		super(message);
	}

	public DocumentException(final Throwable throwable) {
		super(throwable);
	}

	public DocumentException(final String message, final Throwable throwable) {
		super(message, throwable);
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(final String errorCode) {
		this.errorCode = errorCode;
	}

}
