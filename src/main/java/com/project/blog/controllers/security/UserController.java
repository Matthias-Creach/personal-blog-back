package com.project.blog.controllers.security;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.blog.constants.security.AuthorizationConstants;
import com.project.blog.models.dto.UserDTO;
import com.project.blog.models.entities.User;
import com.project.blog.security.services.UserDetailsImpl;
import com.project.blog.services.itf.UserService;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.ParseException;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/blog/users")
public class UserController {
	
	@Autowired
	UserService userService;
	
	@Autowired
	AuthenticationManager authManager;
	
	ModelMapper modelMapper = new ModelMapper();
	
	@GetMapping("/{id}")
	@PreAuthorize(AuthorizationConstants.ADMIN)
	public ResponseEntity<UserDTO> getUser(@PathVariable("id") Long userId){
		User user = userService.getUser(userId);
		UserDTO userDto = convertToDto(user);
		
		return ResponseEntity.ok().body(userDto);
	}
	
	@GetMapping("/")
	@PreAuthorize(AuthorizationConstants.ADMIN)
	public ResponseEntity<List<UserDTO>> getAllUsers(){
		List<User> users = userService.getAllUsers();
		List<UserDTO> usersDto = users
				.stream()
				.map(this::convertToDto)
				.collect(Collectors.toList());
		
		return ResponseEntity.ok().body(usersDto);
	}
	
	@DeleteMapping("/{id}")
	@PreAuthorize(AuthorizationConstants.ADMIN)
	public ResponseEntity<?> deleteUser(@PathVariable("id") Long userId){
		userService.deleteUser(userId);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("/{id}")
	@PreAuthorize("#user.id == #userId and #user.id == principal.id")
	public ResponseEntity<UserDTO> updateUser(@PathVariable("id") Long userId, @RequestBody UserDTO user){
		
		User updateUser = userService.updateUser(convertToEntity(user));
		if(updateUser != null) {
			return ResponseEntity.ok().body(convertToDto(updateUser));
		}else {
			return ResponseEntity.notFound().build();
		}
		
	}
	
	/** DTO <-> Entity **/
	
	private UserDTO convertToDto(User user) {
		UserDTO userDto = modelMapper.map(user, UserDTO.class);
		return userDto;
	}
	
	private User convertToEntity(UserDTO userDto) throws ParseException{
		User user = modelMapper.map(userDto, User.class);
		
		return user;
	}

}
