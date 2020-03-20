package com.project.blog.services.itf;

import java.util.List;

import com.project.blog.models.dto.CommentDTO;
import com.project.blog.models.entities.Article;
import com.project.blog.models.entities.Comment;
import com.project.blog.models.entities.User;

public interface CommentService {
	
	abstract List<CommentDTO> getCommentsByArticle(Article article);
}
