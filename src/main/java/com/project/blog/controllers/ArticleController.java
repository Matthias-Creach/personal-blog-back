package com.project.blog.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.project.blog.constants.security.AuthorizationConstants;
import com.project.blog.models.dto.ArticleDTO;
import com.project.blog.models.entities.Article;
import com.project.blog.services.impl.ArticleServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/blog/articles")
public class ArticleController {
	
	@Autowired
	ArticleServiceImpl articleService;
	
	private ModelMapper modelMapper = new ModelMapper();
	
	@GetMapping("/{articleId}")
	public ResponseEntity<ArticleDTO> getArticle(@PathVariable("articleId") Long articleId){
		Article article = articleService.getArticle(articleId);
		ArticleDTO articleDto = convertToDto(article);
		return ResponseEntity.ok().body(articleDto);
	}
	
	@GetMapping("/public")
	@PreAuthorize(AuthorizationConstants.EVERYONE)
	public ResponseEntity<List<ArticleDTO>> getPublicArticles(){
		List<Article> listArticles = articleService.getPublicArticles();
		List<ArticleDTO> listArticlesDto = listArticles
				.stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listArticlesDto);
	}
	
	@GetMapping("/")
	public ResponseEntity<List<ArticleDTO>> getAllArticles(){
		List<Article> listArticles = articleService.getAllArticles();
		List<ArticleDTO> listArticlesDto = listArticles
				.stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
		
		return ResponseEntity.ok().body(listArticlesDto);
	}
	
	@PostMapping("/")
	@PreAuthorize(AuthorizationConstants.ADMIN)
	public ResponseEntity<ArticleDTO> addArticle(@RequestBody ArticleDTO newArticle, @RequestParam Long userId){
		Article article = articleService.createArticle(convertToEntity(newArticle), userId);
		ArticleDTO articleDto = convertToDto(article);
		return ResponseEntity.ok().body(articleDto);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize(AuthorizationConstants.ADMIN)
	public ResponseEntity<?> deleteArticle(@PathVariable("id") Long articleId){
		articleService.deleteArticle(articleId);
		return ResponseEntity.ok().body("Article has been deleted");
	}
	
	/** DTO <--> Entity **/
	
	private ArticleDTO convertToDto(Article article) {
		ArticleDTO articleDto = modelMapper.map(article, ArticleDTO.class);
		articleDto.setUsername(article.getUser().getUsername());
		return articleDto;
	}
	
	private Article convertToEntity(ArticleDTO articleDto) {
		Article article = modelMapper.map(articleDto, Article.class);
		return article;
	}
}
