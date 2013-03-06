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
public class StoryDAOImpl {
	@Autowired
	@Qualifier("mongoTemplate")
	private MongoTemplate mongoTemplate;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
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

	public void addEntry(String fbId, AddEntryModel request) throws AddEntryException{
		Criteria c = new Criteria();
		Criteria orC = new Criteria();
		
		c.andOperator(where("_id").is(request.getStoryId()), 
				where("lastFriendEntry").ne(fbId), 
				where("numCharacters").gte(request.getEntry().getEntry().length()),
				where("version").is(request.getVersion()),
				orC.orOperator(where("fbFriends").is(fbId), where("leaderFbId").is(fbId)));
		
		Update update = new Update();
		update.push("entries", request.getEntry());
		update.inc("version", 1);
		update.set("lastFriendEntry", fbId);
		
		Query q = new Query();
		q.addCriteria(c);
		
		StoryModel model = mongoTemplate.findAndModify(q, update, StoryModel.class, "storyModel");
		
		if(model == null){
		    q = new Query(where("_id").is(request.getStoryId()));
		    
		    mongoTemplate.find(q, StoryModel.class);		    
		    
			logger.error("addEntry(): Add entry failed.\nHere is why: " + q.toString());
//			logger.error("addEntry(): Add entry failed.\nHere is why: [_id | {}] [fbFriends | {}] [lastFriendEntry | {}] [numCharacters | {}] [version | {}]", request.getStoryId(), fbId, fbId, request.getEntry().getEntry().length(), request.getVersion());
			
			throw new AddEntryException();
		}
	}
	
	public PrivateStoryResponse getPrivateStory(String fbId, ObjectId storyId) throws StoryNotFoundException{
		Criteria getStoryC = new Criteria();
		
		getStoryC.andOperator(where("_id").is(storyId));
		
		Query q = new Query();
		q.addCriteria(getStoryC);
		
		PrivateStoryResponse result = mongoTemplate.findOne(q, PrivateStoryResponse.class, "storyModel");
		
		if(result == null){
			logger.error("getStory(): Get private story not found failed.\nHere is why: " + q.toString());

			throw new StoryNotFoundException();
		}
		
		return result;
	}
	
	public void likeStory(ObjectId storyId){
	    Query query = new Query();
	    query.addCriteria(where("_id").is(storyId));
	    
	    Update update = new Update();
	    update.inc("likes", 1);
	    
		mongoTemplate.findAndModify(query, update, StoryModel.class);
	}
}
