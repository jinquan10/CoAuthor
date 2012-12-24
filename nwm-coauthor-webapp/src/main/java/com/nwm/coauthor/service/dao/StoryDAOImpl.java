package com.nwm.coauthor.service.dao;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.nwm.coauthor.service.model.StoryModel;
import com.nwm.coauthor.service.resource.response.GetPrivateStoryResponse;

@Component
public class StoryDAOImpl {
	@Autowired
	@Qualifier("mongoTemplate")
	private MongoTemplate mongoTemplate;
	
	public void createStory(StoryModel storyModel){
		mongoTemplate.insert(storyModel);
	}
	
	public List<GetPrivateStoryResponse> getStoriesByFbId(String fbId){
		return mongoTemplate.find(query(where("fbId").is(fbId).orOperator(where("fbFriends").is(fbId))), GetPrivateStoryResponse.class, "storyModel");
	}
}
