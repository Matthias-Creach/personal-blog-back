package com.project.blog.models.entities;

import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Entity
@Table(name = "comments")
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank
	@Size(max = 255)
	private String content;
	
	private Date date;
	
	@ManyToOne
	@JoinColumn(name = "article_id")
	@JsonManagedReference
	private Article article;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	@JsonManagedReference
	private User user;

	/** Constructors **/
	
	public Comment() {super();}
	
	public Comment(Long id, @NotBlank @Size(max = 255) String content, Date date, Article article, User user) {
		super();
		this.id = id;
		this.content = content;
		this.date = date;
		this.article = article;
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Article getArticle() {
		return article;
	}

	public void setArticle(Article article) {
		this.article = article;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
}
