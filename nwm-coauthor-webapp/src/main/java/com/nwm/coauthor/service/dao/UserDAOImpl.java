package com.nwm.coauthor.service.dao;

import static org.springframework.data.mongodb.core.query.Criteria.where;

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

import com.mongodb.WriteResult;
import com.nwm.coauthor.service.model.UserModel;

@Component
public class UserDAOImpl {
    @Autowired
    @Qualifier("mongoTemplate")
    private MongoTemplate mongoTemplate;

    public boolean isStoryLiked(String fbId, String storyId) {
        Criteria c = new Criteria();
        c.andOperator(where("storyLikes").is(storyId), where("fbId").is(fbId));

        Query q = new Query();
        q.addCriteria(c);

        long likeCount = mongoTemplate.count(q, UserModel.class);

        return likeCount > 0 ? true : false;
    }

    public WriteResult likeStory(String fbId, String storyId) {
        Query q = new Query();
        q.addCriteria(where("fbId").is(fbId));

        Update update = new Update();
        update.push("storyLikes", storyId);

        return mongoTemplate.updateFirst(q, update, UserModel.class);
    }
}
