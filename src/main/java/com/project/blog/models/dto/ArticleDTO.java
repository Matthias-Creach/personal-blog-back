package com.project.blog.models.dto;

import java.sql.Date;
import java.util.List;

public class ArticleDTO {
	
	private Long id;
	private String title;
	private String content;
	private Date releaseDate;
	
	private String username;
	
	private List<CommentDTO> listComments;

	/** Constructors **/
	
	public ArticleDTO() {super();}
	
	public ArticleDTO(Long id, String title, String content, Date releaseDate, String username,
			List<CommentDTO> listComments) {
		super();
		this.id = id;
		this.title = title;
		this.content = content;
		this.releaseDate = releaseDate;
		this.username = username;
		this.listComments = listComments;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	/** Getters & Setters **/
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getReleaseDate() {
		return releaseDate;
	}

	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<CommentDTO> getListComments() {
		return listComments;
	}

	public void setListComments(List<CommentDTO> listComments) {
		this.listComments = listComments;
	}
	
}
