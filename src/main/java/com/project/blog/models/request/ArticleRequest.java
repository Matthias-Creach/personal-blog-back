package com.project.blog.models.request;

import java.sql.Date;

public class ArticleRequest {
	
	private String title;
	private String content;
	private Boolean isPrivate;
	private Date releaseDate;
	
	//Foreign key
	private Long user_id;
	private Long article_id;
	
	/** Constructors **/
	
	public ArticleRequest(String title, String content, Boolean isPrivate, Date releaseDate, Long user_id,
			Long article_id) {
		super();
		this.title = title;
		this.content = content;
		this.isPrivate = isPrivate;
		this.releaseDate = releaseDate;
		this.user_id = user_id;
		this.article_id = article_id;
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
	public Boolean getIsPrivate() {
		return isPrivate;
	}
	public void setIsPrivate(Boolean isPrivate) {
		this.isPrivate = isPrivate;
	}
	public Date getReleaseDate() {
		return releaseDate;
	}
	public void setReleaseDate(Date releaseDate) {
		this.releaseDate = releaseDate;
	}
	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public Long getArticle_id() {
		return article_id;
	}
	public void setArticle_id(Long article_id) {
		this.article_id = article_id;
	}

}
