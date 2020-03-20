package com.project.blog.models.dto;

import java.sql.Date;

public class CommentDTO {
	
	private String content;
	private String username;
	private Date date;
	
	/** Constructors **/
	
	public CommentDTO() {super();}
	
	public CommentDTO(String content, String username, Date date) {
		super();
		this.content = content;
		this.username = username;
		this.date = date;
	}
	
	/** Getters & Setters **/

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	public Date getDate() {
		return date;
	}
	
	public void setDate(Date date) {
		this.date = date;
	}
	
}
