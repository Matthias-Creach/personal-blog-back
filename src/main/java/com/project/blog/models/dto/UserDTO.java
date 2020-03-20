package com.project.blog.models.dto;

import java.util.List;

import com.project.blog.models.parameters.Role;

public class UserDTO {
	
	private String type = "Bearer";
	
	private Long id;
	private String username;
	private String password;
	private String email;
	
	private String token;
	
	private List<Role> roles;
	private List<ArticleDTO> articles;
	private List<CommentDTO> comments;
	
	/** Constructors **/
	
	public UserDTO() {
		super();
	}

	public UserDTO(
			Long id, 
			String username, 
			String password, 
			String email, 
			String token, 
			List<Role> roles, 
			List<ArticleDTO> articles,
			List<CommentDTO> comments
	){
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.email = email;
		this.token = token;
		this.roles = roles;
		this.articles = articles;
		this.comments = comments;
	}

	/** Getters & Setters **/

	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
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
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public List<Role> getRoles() {
		return roles;
	}
	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
	public List<ArticleDTO> getArticles() {
		return articles;
	}
	public void setArticles(List<ArticleDTO> articles) {
		this.articles = articles;
	}
	public List<CommentDTO> getComments() {
		return comments;
	}
	public void setComments(List<CommentDTO> comments) {
		this.comments = comments;
	}

}
