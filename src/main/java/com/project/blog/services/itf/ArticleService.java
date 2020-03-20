package com.project.blog.services.itf;

import java.util.List;

import com.project.blog.models.dto.ArticleDTO;
import com.project.blog.models.entities.Article;

public interface ArticleService {
	public abstract List<Article> getAllArticles();
	public abstract List<Article> getPublicArticles();
	public abstract Article getArticle(Long articleId);
	
	public abstract Article createArticle(Article newArticle, Long userId);
	public abstract Article updateArticle(Article updateArticle);
	public abstract void deleteArticle(Long articleId);
}
