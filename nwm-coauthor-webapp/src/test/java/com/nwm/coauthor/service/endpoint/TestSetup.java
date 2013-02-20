package com.nwm.coauthor.service.endpoint;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.util.StringUtils;

import com.mongodb.Mongo;
import com.mongodb.WriteConcern;
import com.nwm.coauthor.service.client.StoryClient;
import com.nwm.coauthor.service.model.UserModel;

public class TestSetup {
	protected static MongoTemplate mongoTemplate = null;
	protected static StoryClient storyClient = new StoryClient();
	
	private static MongoIniter mongoIniter = new TestSetup().new MongoIniter();
	
//	private static void authenticateForCoToken() throws SomethingWentWrongException, AuthenticationUnauthorizedException{
//		AuthenticationClient client = new AuthenticationClient();
//		String fbToken = System.getProperty("fbToken");
//		
//		ResponseEntity<AuthenticationResponse> response = client.authenticateFB(new AuthenticateFBRequest(fbToken));

//		users = new ArrayList<LoginModel>();
//		coTokens.add(response.getBody().getCoToken());
//	}
	
	protected List<UserModel> createUsers(Integer numUsers) throws InterruptedException{
		if(numUsers == null)
			numUsers = 2;
		
		List<UserModel> users = new ArrayList<UserModel>();
		String numUsersStr = System.getProperty("numUsers");
		
		if(StringUtils.hasText(numUsersStr)){
			numUsers = Integer.parseInt(numUsersStr);
		}
		
		for(int i = 0; i < numUsers; i++){
			UserModel loginModel = new UserModel(String.valueOf(Math.random()), String.valueOf(Math.random()));
			users.add(loginModel);
			mongoTemplate.insert(loginModel);
		}
		
		return users;
	}
	
	public class MongoIniter{
		public MongoIniter(){
			try {
				initMongo();
			} catch (UnknownHostException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		private void initMongo() throws UnknownHostException{
			mongoTemplate = new MongoTemplate(new Mongo("localhost", 27017), "coauthor", new UserCredentials("", ""));
			mongoTemplate.setWriteConcern(WriteConcern.SAFE);
			mongoTemplate.getDb().dropDatabase();		
		}
	}
}
