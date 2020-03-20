package com.project.blog.models.entities.s3;

import java.util.Collection;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

@Entity
@Table(name = "folders")
public class PersonalFolder {

	@Id
	@NotBlank
	private String path;
	
	@NotBlank
	private String name;
	
	@ManyToOne
	private PersonalFolder parent;
	
	@OneToMany(mappedBy = "parent")
	private Collection<PersonalFolder> children;
	
	@OneToMany(mappedBy = "folder")
	private Collection<PersonalFile> files;

	public PersonalFolder() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PersonalFolder(@NotBlank String path, @NotBlank String name, PersonalFolder parent,
			Collection<PersonalFolder> children, Collection<PersonalFile> files) {
		super();
		this.path = path;
		this.name = name;
		this.parent = parent;
		this.children = children;
		this.files = files;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public PersonalFolder getParent() {
		return parent;
	}

	public void setParent(PersonalFolder parent) {
		this.parent = parent;
	}

	public Collection<PersonalFolder> getChildren() {
		return children;
	}

	public void setChildren(Collection<PersonalFolder> children) {
		this.children = children;
	}

	public Collection<PersonalFile> getFiles() {
		return files;
	}

	public void setFiles(Collection<PersonalFile> files) {
		this.files = files;
	}
	
}
