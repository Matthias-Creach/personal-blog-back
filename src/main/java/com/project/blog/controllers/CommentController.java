package com.project.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.blog.models.entities.Comment;
import com.project.blog.repositories.CommentRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/blog/comments")
public class CommentController {
	
	@Autowired
	CommentRepository commentRepository;
	
	@GetMapping("/")
	public ResponseEntity<List<Comment>> getAllComments(){
		List<Comment> comments = commentRepository.findAll();
		return ResponseEntity.ok().body(comments);
	}
	
	
}
