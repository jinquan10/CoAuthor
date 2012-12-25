package com.nwm.coauthor.service.endpoint;

import java.io.IOException;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import org.junit.BeforeClass;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import com.mongodb.Mongo;
import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.service.client.AuthenticationClient;
import com.nwm.coauthor.service.client.StoryClient;
import com.nwm.coauthor.service.model.LoginModel;
import com.nwm.coauthor.service.resource.request.AuthenticateFBRequest;
import com.nwm.coauthor.service.resource.response.AuthenticationResponse;

public class TestSetup {
	protected static MongoTemplate mongoTemplate = null;
	protected StoryClient storyClient = new StoryClient();
	
	protected static List<LoginModel> users;
	
	@BeforeClass
	public static void beforeClass() throws IOException, SomethingWentWrongException, AuthenticationUnauthorizedException, InterruptedException{
		initMongo();
//		authenticateForCoToken();
		createUsers();
	}
	
	private static void initMongo() throws UnknownHostException{
		mongoTemplate = new MongoTemplate(new Mongo("localhost", 27017), "coauthor", new UserCredentials("", ""));
		mongoTemplate.getDb().dropDatabase();		
	}
	
	private static void authenticateForCoToken() throws SomethingWentWrongException, AuthenticationUnauthorizedException{
		AuthenticationClient client = new AuthenticationClient();
		String fbToken = System.getProperty("fbToken");
		
		ResponseEntity<AuthenticationResponse> response = client.authenticateFB(new AuthenticateFBRequest(fbToken));

//		users = new ArrayList<LoginModel>();
//		coTokens.add(response.getBody().getCoToken());
	}
	
	private static void createUsers() throws InterruptedException{
		int numUsers = 2;
		users = new ArrayList<LoginModel>();
		String numUsersStr = System.getProperty("numUsers");
		
		if(StringUtils.hasText(numUsersStr)){
			numUsers = Integer.parseInt(numUsersStr);
		}
		
		for(int i = 0; i < numUsers; i++){
			LoginModel loginModel = new LoginModel(String.valueOf(i), String.valueOf(i));
			users.add(loginModel);
			mongoTemplate.insert(loginModel);
		}
		
		Thread.sleep(1000);
	}
}
