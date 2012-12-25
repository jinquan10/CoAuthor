package com.nwm.coauthor.service.dao;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.nwm.coauthor.service.model.StoryModel;
import com.nwm.coauthor.service.resource.response.PrivateStoryResponse;

@Component
public class StoryDAOImpl {
	@Autowired
	@Qualifier("mongoTemplate")
	private MongoTemplate mongoTemplate;
	
	public void createStory(StoryModel storyModel){
		mongoTemplate.insert(storyModel);
	}
	
	public List<PrivateStoryResponse> getStoriesByFbId(String fbId){
		Criteria c = new Criteria();
		c.orOperator(where("leaderFbId").is(fbId), where("fbFriends").is(fbId));
		
		Query q = new Query();
		q.fields().slice("entries", 1);
		q.addCriteria(c);
		
		return mongoTemplate.find(q, PrivateStoryResponse.class, "storyModel");
	}
}
