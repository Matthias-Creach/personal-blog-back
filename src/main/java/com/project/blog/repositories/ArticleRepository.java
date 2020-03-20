package com.project.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.blog.models.entities.Article;
import com.project.blog.models.entities.User;

public interface ArticleRepository extends JpaRepository<Article, Long>{

	public List<Article> findByIsPrivateFalse();
	
	public List<Article> findByUser(User user);

}
