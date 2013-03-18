package com.nwm.coauthor.service.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.nwm.coauthor.service.model.StoryModel;

@Component
public class StoryDAOImpl {
    @Autowired
    @Qualifier("mongoTemplate")
    private MongoTemplate mongoTemplate;

    public void createStory(StoryModel storyModel) {
        mongoTemplate.insert(storyModel);
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
//    public void likeStory(ObjectId storyId) {
//        Query query = new Query();
//        query.addCriteria(where("_id").is(storyId));
//
//        Update update = new Update();
//        update.inc("likes", 1);
//
//        mongoTemplate.updateFirst(query, update, StoryModel.class);
//    }
//
//    public WriteResult publishStory(String fbId, ObjectId storyId) {
//        Criteria criteria = new Criteria();
//        criteria.andOperator(where("leaderFbId").is(fbId), where("_id").is(storyId), where("title").ne(null), where("title").ne(""));
//        
//        Query query = new Query(criteria);
//        
//        Update update = new Update();
//        update.set("isPublished", true);
//        
//        return mongoTemplate.updateFirst(query, update, StoryModel.class);
//    }
//
//    public WriteResult changeStoryTitle(String fbId, ObjectId storyId, String title) {
//        Criteria criteria = new Criteria();
//        criteria.andOperator(where("_id").is(storyId), where("leaderFbId").is(fbId), where("isPublished").is(false));
//        
//        Update update = new Update();
//        update.set("title", title);
//        
//        return mongoTemplate.updateFirst(new Query(criteria), update, StoryModel.class);
//    }
}
