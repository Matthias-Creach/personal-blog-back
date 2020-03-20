package com.project.blog.controllers.security;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.blog.models.entities.User;
import com.project.blog.models.parameters.EnumRole;
import com.project.blog.models.parameters.Role;
import com.project.blog.models.request.LoginRequest;
import com.project.blog.models.request.SignupRequest;
import com.project.blog.models.response.JwtResponse;
import com.project.blog.models.response.MessageResponse;
import com.project.blog.repositories.RoleRepository;
import com.project.blog.repositories.UserRepository;
import com.project.blog.security.jwt.AuthTokenFilter;
import com.project.blog.security.jwt.JwtUtils;
import com.project.blog.security.services.UserDetailsImpl;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/blog/auth")
public class AuthController {
	
	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	AuthenticationManager authManager;
	
	//Repositories
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	//Utilities
	@Autowired
	PasswordEncoder encoder;
	
	@Autowired
	JwtUtils jwtUtils;
	
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){
		Authentication auth = authManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(auth);
		String jwt = jwtUtils.generatedJwtToken(auth);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());
		
		logger.trace("signin");
		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
	}
	
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest){
		
		//Verify if information already exists
		if(userRepository.existsByUsername(signupRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: username is already taken"));
		}
		// --> Change, giving information about existing mail
		if(userRepository.existsByEmail(signupRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: email is already taken"));
		}
		
		// Create new user account
		User user = new User(signupRequest.getUsername(), signupRequest.getEmail(), encoder.encode(signupRequest.getPassword()));
		Set<String> strRoles = signupRequest.getRoles();
		Set<Role> roles = new HashSet<Role>();
		
		if(strRoles == null || strRoles.isEmpty()) {
			Role userRole = roleRepository.findByName(EnumRole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: role is not found"));
			roles.add(userRole);
		}else {
			strRoles.forEach(role -> {
				switch(role) {
				case "admin":
					Role adminRole = roleRepository.findByName(EnumRole.ROLE_ADMIN)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found "));
					roles.add(adminRole);
					break;
				default:
					Role userRole = roleRepository.findByName(EnumRole.ROLE_USER)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found "));
					roles.add(userRole);
				}
			});
		}
		
		user.setRoles(roles);
		userRepository.save(user);
		
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
	
}
