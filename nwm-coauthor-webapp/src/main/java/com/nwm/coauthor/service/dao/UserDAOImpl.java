package com.nwm.coauthor.service.dao;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.nwm.coauthor.exception.AddEntryException;
import com.nwm.coauthor.exception.StoryNotFoundException;
import com.nwm.coauthor.service.model.AddEntryModel;
import com.nwm.coauthor.service.model.StoryModel;
import com.nwm.coauthor.service.model.UserModel;
import com.nwm.coauthor.service.resource.response.PrivateStoryResponse;

@Component
public class UserDAOImpl {
	@Autowired
	@Qualifier("mongoTemplate")
	private MongoTemplate mongoTemplate;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public boolean isStoryLiked(String fbId, ObjectId storyId){
		Criteria c = new Criteria();
		c.andOperator(where("likes").is(storyId), where("fbId").is(fbId));
		
		Query q = new Query();
		q.addCriteria(c);
		
		long likeCount = mongoTemplate.count(q, UserModel.class);
		
		return likeCount > 0 ? true : false;
	}
}
