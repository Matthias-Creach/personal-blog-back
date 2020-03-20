package com.project.blog.services.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.project.blog.models.entities.Article;
import com.project.blog.models.entities.Comment;
import com.project.blog.models.entities.User;
import com.project.blog.repositories.ArticleRepository;
import com.project.blog.repositories.CommentRepository;
import com.project.blog.repositories.UserRepository;
import com.project.blog.services.itf.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	ArticleRepository articleRepository;
	
	@Autowired
	CommentRepository commentRepository;
	
	@Autowired
	PasswordEncoder encoder;
	
	@Override
	public User getUser(Long userId) {
		Optional<User> optionalUser = userRepository.findById(userId);
		if(optionalUser.isPresent()) {
			User user = optionalUser.get();
			
			//Set articles
			List<Article> listArticles = articleRepository.findByUser(user);
			user.setArticles(listArticles);
			
			//Set comments
			List<Comment> listComments = commentRepository.findByUser(user);
			user.setComments(listComments);
			
			return user;
		}else {
			return null;
		}
	}

	@Override
	public List<User> getAllUsers() {
		List<User> listUsers = userRepository.findAll();
		return listUsers;
	}
	
	@Override
	public User updateUser(User updateUser) {
		Optional<User> actualUser = userRepository.findById(updateUser.getId());
		if (actualUser.isPresent()) {
			//Set new params
			actualUser.get().setEmail(updateUser.getEmail());
			actualUser.get().setPassword(encoder.encode(updateUser.getPassword()));
			
			//Update in ddb
			User user = userRepository.save(actualUser.get());
			return user;
		}
		return null;
	}
	
	@Override
	public void deleteUser(Long userId) {
		userRepository.deleteById(userId);
	}

}
