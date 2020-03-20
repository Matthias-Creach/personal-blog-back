package com.project.blog.models.response;

public class MessageResponse {

	private String message;

	/** Constuctors **/
	
	public MessageResponse(String message) {
		super();
		this.message = message;
	}
	
	/** Getters & Setters **/

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	
}
