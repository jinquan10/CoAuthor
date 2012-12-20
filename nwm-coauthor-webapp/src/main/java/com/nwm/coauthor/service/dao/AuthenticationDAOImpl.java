package com.nwm.coauthor.service.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.mongodb.Mongo;

@Component
public class AuthenticationDAOImpl {
	@Autowired
	@Qualifier("mongoTemplate")
	private MongoTemplate mongoTemplate;
	
	public String authenticateUsernamePassword(String username, String password){
		// (query(where("username").is(username).and("password").is(password)), Authentication.class);
		return null;
	}
	
	public String authenticateToken(String token){
		return null;
	}
}
