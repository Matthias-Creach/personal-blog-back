package com.project.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.blog.models.entities.Article;
import com.project.blog.models.entities.Comment;
import com.project.blog.models.entities.User;

public interface CommentRepository extends JpaRepository<Comment, Long> {
	
	List<Comment> findByArticle(Article article);
	
	List<Comment> findByUser(User user);
	
	
}
