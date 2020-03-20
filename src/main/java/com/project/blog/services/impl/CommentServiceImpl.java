package com.project.blog.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.blog.models.dto.CommentDTO;
import com.project.blog.models.entities.Article;
import com.project.blog.models.entities.Comment;
import com.project.blog.repositories.CommentRepository;
import com.project.blog.services.itf.CommentService;

@Service
public class CommentServiceImpl implements CommentService{

	@Autowired
	CommentRepository commentRepository;
	
	@Override
	public List<CommentDTO> getCommentsByArticle(Article article) {
		List<Comment> listComment = commentRepository.findByArticle(article);
		List<CommentDTO> listCommentDto = new ArrayList<CommentDTO>();
		listComment.forEach(comment -> {
			listCommentDto.add(mapToDto(comment));
		});
		return listCommentDto;
	}
	
	private CommentDTO mapToDto(Comment comment) {
		CommentDTO dto = new CommentDTO();
		dto.setContent(comment.getContent());
		dto.setDate(comment.getDate());
		dto.setUsername(comment.getUser().getUsername());
		return dto;
	}

}
