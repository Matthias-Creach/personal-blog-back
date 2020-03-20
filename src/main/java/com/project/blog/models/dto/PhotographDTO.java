package com.project.blog.models.dto;

public class PhotographDTO {
	
	private String url;
	private String format;
	public PhotographDTO(String url, String format) {
		super();
		this.url = url;
		this.format = format;
	}
	public PhotographDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getFormat() {
		return format;
	}
	public void setFormat(String format) {
		this.format = format;
	}
	
	
}
