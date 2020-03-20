package com.project.blog.models.entities.s3;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name="files")
public class PersonalFile {
	
	@EmbeddedId
	private PersonalFileKey fileKey;
	
	@MapsId("folderPath")
	@ManyToOne
	private PersonalFolder folder;
	
	@NotBlank
	private String url;
	
	/** Constructors **/
	
	public PersonalFile() {
		super();
	}
	
	public PersonalFile(PersonalFileKey fileKey, PersonalFolder folder, @NotBlank String url) {
		super();
		this.fileKey = fileKey;
		this.folder = folder;
		this.url = url;
	}
	
	/** Getters & Setters **/

	public PersonalFileKey getFileKey() {
		return fileKey;
	}

	public void setFileKey(PersonalFileKey fileKey) {
		this.fileKey = fileKey;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public PersonalFolder getFolder() {
		return folder;
	}

	public void setFolder(PersonalFolder folder) {
		this.folder = folder;
	}

}
