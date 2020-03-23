package com.project.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.blog.models.entities.Article;
import com.project.blog.models.entities.User;

public interface ArticleRepository extends JpaRepository<Article, Long>{

	List<Article> findAllByOrderByReleaseDateDesc();
	
	List<Article> findByIsPrivateFalseOrderByReleaseDateDesc();
	
	List<Article> findByUser(User user);

}
