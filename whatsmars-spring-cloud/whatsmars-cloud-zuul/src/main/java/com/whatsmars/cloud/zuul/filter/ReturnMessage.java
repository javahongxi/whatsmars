package com.whatsmars.cloud.zuul.filter;

public class ReturnMessage {

	private int status;
	
	private String returnUrl;
	
	private String message;

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getReturnUrl() {
		return returnUrl;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ReturnMessage(int status, String returnUrl, String message) {
		super();
		this.status = status;
		this.returnUrl = returnUrl;
		this.message = message;
	}

	public ReturnMessage(int status, String message) {
		super();
		this.status = status;
		this.returnUrl = null;
		this.message = message;
	}

	public ReturnMessage() {
		super();
	}
	
}
