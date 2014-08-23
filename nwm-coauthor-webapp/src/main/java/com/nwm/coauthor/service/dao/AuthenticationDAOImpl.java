package com.nwm.coauthor.service.dao;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;
import static org.springframework.data.mongodb.core.query.Update.update;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.nwm.coauthor.Constants;
import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.UsernameExistsException;
import com.nwm.coauthor.service.model.UserModel;

@Component
public class AuthenticationDAOImpl {
    @Autowired
    @Qualifier("mongoTemplate")
    private MongoTemplate mongoTemplate;

    public void login(String coToken, String fbId) {
        mongoTemplate.upsert(query(where("fbId").is(fbId)), update("coToken", coToken), UserModel.class);
    }

    public UserModel authenticateCOTokenForFbId(String coToken) {
        Query query = new Query();
        query.addCriteria(where("coToken").is(coToken));
        query.fields().include("fbId");

        return mongoTemplate.findOne(query, UserModel.class);
    }

    public void insertNativeUser(UserModel user) throws UsernameExistsException {
        Query q = new Query();
        q.addCriteria(where("username").is(user.getUsername()));
        
        boolean exists = mongoTemplate.exists(q, Constants.USER_COLLECTION);
        
        if (!exists) {
            mongoTemplate.insert(user);
        } else {
            throw new UsernameExistsException();
        }
    }

    public void loginNative(String username, String password, String coToken) throws AuthenticationUnauthorizedException {
        Query q = new Query();
        q.addCriteria(where("username").is(username).and("password").is(password));
        
        Update u = new Update();
        u.set("coToken", coToken);
        u.set("lastLogin", System.currentTimeMillis());
        
        if (mongoTemplate.updateFirst(q, u, Constants.USER_COLLECTION).getN() < 1) {
            throw new AuthenticationUnauthorizedException();
        }
    }

    public void authenticateNative(String encryptedToken) throws AuthenticationUnauthorizedException {
        Query q = new Query();
        q.addCriteria(where("coToken").is(encryptedToken));
        
        boolean exists = mongoTemplate.exists(q, Constants.USER_COLLECTION);
        
        if (!exists) {
            throw new AuthenticationUnauthorizedException();
        }
    }

    public void logout(String encryptedToken) {
        Query q = new Query();
        q.addCriteria(where("coToken").is(encryptedToken));
        
        Update u = new Update();
        u.unset("coToken");
        
        mongoTemplate.updateFirst(q, u, Constants.USER_COLLECTION);
    }
}
