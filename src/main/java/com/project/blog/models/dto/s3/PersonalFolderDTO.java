package com.project.blog.models.dto.s3;

import java.util.List;

public class PersonalFolderDTO {
	
	private String name;
	private String parent_name;
	private String path;
	private List<PersonalFolderDTO> children;
	private List<PersonalFileDTO> files;
	
	public PersonalFolderDTO() {
		super();
		// TODO Auto-generated constructor stub
	}
	public PersonalFolderDTO(String name, String parent_name, String path, List<PersonalFolderDTO> children, List<PersonalFileDTO> files) {
		super();
		this.name = name;
		this.parent_name = parent_name;
		this.path = path;
		this.children = children;
		this.files = files;
	}
	public String getParent_name() {
		return parent_name;
	}
	public void setParent_name(String parent_name) {
		this.parent_name = parent_name;
	}
	public List<PersonalFileDTO> getFiles() {
		return files;
	}
	public void setFiles(List<PersonalFileDTO> files) {
		this.files = files;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getParent() {
		return parent_name;
	}
	public void setParent(String parent_name) {
		this.parent_name = parent_name;
	}
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public List<PersonalFolderDTO> getChildren() {
		return children;
	}
	public void setChildren(List<PersonalFolderDTO> children) {
		this.children = children;
	}
	
	
}
