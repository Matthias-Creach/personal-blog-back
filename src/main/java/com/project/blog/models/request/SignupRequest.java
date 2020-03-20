package com.project.blog.models.request;

import java.util.Set;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SignupRequest {

	@NotBlank
	@Size(max = 20)
	private String username;
	
	@NotBlank
	@Size(max = 255)
	private String password;
	
	@NotBlank
	@Size(max = 50)
	@Email
	private String email;
	
	private Set<String> roles;
	
	/** Constructors **/
	
	public SignupRequest() {super();}

	public SignupRequest(@NotBlank @Size(max = 20) String username, @NotBlank @Size(max = 50) String password,
			@NotBlank @Size(max = 50) String email, Set<String> roles) {
		super();
		this.username = username;
		this.password = password;
		this.email = email;
		this.roles = roles;
	}

	/** Getters & Setters **/
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Set<String> getRoles() {
		return roles;
	}

	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}
	
}
