package com.nwm.coauthor.service.dao;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.nwm.coauthor.service.model.LoginModel;

@Component
public class AuthenticationDAOImpl {
	@Autowired
	@Qualifier("mongoTemplate")
	private MongoTemplate mongoTemplate;
	
	public void login(String coToken, String fbId){
		mongoTemplate.upsert(query(where("fbId").is(fbId)), update("coToken", coToken), LoginModel.class);
	}

	public LoginModel authenticateCOTokenForFbId(String coToken) {
		Query query = new Query();
		query.addCriteria(where("coToken").is(coToken));
		query.fields().include("fbId");
		
		return mongoTemplate.findOne(query, LoginModel.class);
	}
}
