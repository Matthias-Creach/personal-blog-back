package com.project.blog.services.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.blog.models.dto.ArticleDTO;
import com.project.blog.models.entities.Article;
import com.project.blog.repositories.ArticleRepository;
import com.project.blog.repositories.UserRepository;
import com.project.blog.services.itf.ArticleService;
import com.project.blog.services.itf.CommentService;

@Service
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	ArticleRepository articleRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	CommentService commentService;
	
	@Override
	public List<Article> getAllArticles() {
		List<Article> listArticles = articleRepository.findAll();
		return listArticles;
	}

	@Override
	public List<Article> getPublicArticles() {
		List<Article> listArticles = articleRepository.findByIsPrivateFalse();
		return listArticles;
	}
	
	@Override
	public Article getArticle(Long articleId) {
		Optional<Article> article = articleRepository.findById(articleId);
		return article.get();
	}

	@Override
	public Article createArticle(Article newArticle, Long userId) {

		newArticle.setUser(userRepository.getOne(userId));
		newArticle.setComments(new ArrayList<>());
		Article article = articleRepository.save(newArticle);
		
		return article;
	}

	@Override
	public Article updateArticle(Article updateArticle) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteArticle(Long articleId) {
		articleRepository.deleteById(articleId);
	}

}
