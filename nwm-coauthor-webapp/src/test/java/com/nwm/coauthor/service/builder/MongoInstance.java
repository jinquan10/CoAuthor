package com.nwm.coauthor.service.builder;

import java.net.UnknownHostException;

import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.core.MongoTemplate;

import com.mongodb.Mongo;
import com.mongodb.WriteConcern;

public class MongoInstance {
	private MongoInstance(){
		
	}
	
	private static class SingletonHolder{
		private static MongoTemplate mongoTemplate = new MongoTemplate(getMongo(), "coauthor", new UserCredentials("", ""));
		private static Mongo mongo;
		
		static{
			mongoTemplate.setWriteConcern(WriteConcern.SAFE);
			mongoTemplate.getDb().dropDatabase();
		}
		
		private static Mongo getMongo(){
			try {
				mongo = new Mongo("localhost", 27017);
			} catch (UnknownHostException e) {

			}
			
			return mongo;
		}
		
	}
	
	public static MongoTemplate getInstance(){
		return SingletonHolder.mongoTemplate;
	}
}
