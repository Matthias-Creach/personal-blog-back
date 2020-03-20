package com.project.blog.services.itf;

import java.util.List;

import com.project.blog.models.entities.User;

public interface UserService{

	User getUser(Long userId);
	List<User> getAllUsers();
	
	User updateUser(User updateUser);
	void deleteUser(Long userId);

}
