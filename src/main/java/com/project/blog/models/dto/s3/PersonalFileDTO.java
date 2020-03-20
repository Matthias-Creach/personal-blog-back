package com.project.blog.models.dto.s3;

public class PersonalFileDTO {
	
	private String url;
	private String name;
	private String path;
	
	/** Constructors **/
	
	public PersonalFileDTO() {
		super();
	}
	public PersonalFileDTO(String url, String name, String path) {
		super();
		this.url = url;
		this.name = name;
		this.path = path;
	}
	
	/** Getters & Setters **/
	
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
}
