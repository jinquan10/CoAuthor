package com.nwm.coauthor.service.dao;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.mongodb.WriteResult;
import com.nwm.coauthor.service.model.StoryModel;
import com.nwm.coauthor.service.model.TotalCharsModel;
import com.nwm.coauthor.service.model.UpdateStoryForNewEntryModel;
import com.nwm.coauthor.service.resource.response.StoryResponse;

@Component
public class StoryDAOImpl {
    @Autowired
    @Qualifier("mongoTemplate")
    private MongoTemplate mongoTemplate;

    public void createStory(StoryModel storyModel) {
        mongoTemplate.insert(storyModel);
    }

    public Integer getTotalChars(String storyId){
        Query q = new Query(where("storyId").is(storyId));
        
        q.fields().include("currEntryCharCount");
        
        TotalCharsModel model = mongoTemplate.findOne(q, TotalCharsModel.class, "storyModel");
        
        return model.getCurrEntryCharCount();
    }
    
	public List<StoryResponse> getMyStories(String fbId) {
		Query q = new Query();
		Criteria c = new Criteria();
		c.orOperator(where("leaderFbId").is(fbId), where("fbFriends").is(fbId));
		q.addCriteria(c);
		
		return mongoTemplate.find(q, StoryResponse.class, "storyModel");
	}

    public StoryResponse getStory(String storyId) {
        return mongoTemplate.findOne(new Query(where("storyId").is(storyId)), StoryResponse.class, "storyModel");
    }

    public WriteResult updateStoryForAddingEntry(UpdateStoryForNewEntryModel model) {
        Query q = new Query(where("storyId").is(model.getStoryId()));
        
        Update u = new Update();
        u.set("currEntryCharCount", model.getCurrEntryCharCount());
        u.set("lastEntry", model.getLastEntry());
        u.set("storyLastUpdated", model.getStoryLastUpdated());
        u.set("lastFriendWithEntry", model.getLastFriendWithEntry());
        
        return mongoTemplate.updateFirst(q, u, StoryModel.class);
    }

    public void likeStory(String storyId) {
        Query query = new Query();
        query.addCriteria(where("storyId").is(storyId));

        Update update = new Update();
        update.inc("likes", 1);

        mongoTemplate.updateFirst(query, update, StoryModel.class);
    }
    
//    public List<PrivateStoryResponse> getStoriesByFbId(String fbId) {
//        Criteria c = new Criteria();
//        c.orOperator(where("leaderFbId").is(fbId), where("fbFriends").is(fbId));
//
//        Query q = new Query();
//        q.fields().slice("entries", 1);
//        q.addCriteria(c);
//
//        return mongoTemplate.find(q, PrivateStoryResponse.class, "storyModel");
//    }
//
//    public WriteResult addEntry(String fbId, AddEntryModel request) {
//        Criteria c = new Criteria();
//        Criteria orC = new Criteria();
//
//        c.andOperator(where("_id").is(request.getStoryId()), where("lastFriendEntry").ne(fbId), where("numCharacters").gte(request.getEntry().getEntry().length()),
//                where("version").is(request.getVersion()), orC.orOperator(where("fbFriends").is(fbId), where("leaderFbId").is(fbId)));
//
//        Update update = new Update();
//        update.push("entries", request.getEntry());
//        update.inc("version", 1);
//        update.set("lastFriendEntry", fbId);
//
//        Query q = new Query();
//        q.addCriteria(c);
//
//        return mongoTemplate.updateFirst(q, update, StoryModel.class);
//    }
//
//    public PrivateStoryResponse getPrivateStory(ObjectId storyId) {
//        Query q = new Query(where("_id").is(storyId));
//
//        return mongoTemplate.findOne(q, PrivateStoryResponse.class, "storyModel");
//    }
//

//
    public WriteResult publishStory(String fbId, String storyId, Long now) {
        Criteria criteria = new Criteria();
        criteria.andOperator(where("leaderFbId").is(fbId), where("storyId").is(storyId), where("title").ne(null), where("title").ne(""));
        
        Query query = new Query(criteria);
        
        Update update = new Update();
        update.set("isPublished", true);
        update.set("storyLastUpdated", now);
        
        return mongoTemplate.updateFirst(query, update, StoryModel.class);
    }

    public WriteResult changeStoryTitle(String fbId, String storyId, String title, Long now) {
        Criteria criteria = new Criteria();
        criteria.andOperator(where("storyId").is(storyId), where("leaderFbId").is(fbId), where("isPublished").is(false));
        
        Update update = new Update();
        update.set("title", title);
        update.set("storyLastUpdated", now);
        
        return mongoTemplate.updateFirst(new Query(criteria), update, StoryModel.class);
    }
}
