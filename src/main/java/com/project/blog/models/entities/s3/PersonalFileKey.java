package com.project.blog.models.entities.s3;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.validation.constraints.NotBlank;

@Embeddable
public class PersonalFileKey implements Serializable{
	
	@NotBlank
	private String name;
	
	@NotBlank
	private String folderPath;
	
	/** Constructors **/

	public PersonalFileKey() {
		super();
	}

	public PersonalFileKey(@NotBlank String name, @NotBlank String folderPath) {
		super();
		this.name = name;
		this.folderPath = folderPath;
	}
	
	/** Getters & Setters **/

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFolderPath() {
		return folderPath;
	}

	public void setFolderPath(String folderPath) {
		this.folderPath = folderPath;
	}

}
