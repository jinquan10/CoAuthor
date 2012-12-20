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
	
	public void login(String coToken, Integer fbId){
		throw new UnsupportedOperationException();
	}

	public Integer authenticateCOToken(String coToken) {
		throw new UnsupportedOperationException();
	}
}
