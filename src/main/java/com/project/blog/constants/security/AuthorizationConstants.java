package com.project.blog.constants.security;

public class AuthorizationConstants {

	public final static String USER = "hasRole('USER')";
	public final static String ADMIN = "hasRole('ADMIN')";
	
	public final static String EVERYONE = USER + " or " + ADMIN;
}
