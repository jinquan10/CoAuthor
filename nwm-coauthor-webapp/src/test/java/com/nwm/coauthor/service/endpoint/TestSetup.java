package com.nwm.coauthor.service.endpoint;

import java.io.IOException;

import java.net.UnknownHostException;

import org.junit.BeforeClass;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.ResponseEntity;

import com.mongodb.Mongo;
import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.service.client.AuthenticationClient;
import com.nwm.coauthor.service.client.StoryClient;
import com.nwm.coauthor.service.resource.request.AuthenticateFBRequest;
import com.nwm.coauthor.service.resource.response.AuthenticationResponse;

public class TestSetup {
	protected static MongoTemplate mongoTemplate = null;
	protected static String coToken = null;
	
	protected StoryClient storyClient = new StoryClient();
	
	@BeforeClass
	public static void beforeClass() throws IOException, SomethingWentWrongException, AuthenticationUnauthorizedException{
		initMongo();
		authenticateForCoToken();
	}
	
	private static void initMongo() throws UnknownHostException{
		mongoTemplate = new MongoTemplate(new Mongo("localhost", 27017), "coauthor", new UserCredentials("", ""));
		mongoTemplate.getDb().dropDatabase();		
	}
	
	private static void authenticateForCoToken() throws SomethingWentWrongException, AuthenticationUnauthorizedException{
		AuthenticationClient client = new AuthenticationClient();
		String fbToken = System.getProperty("fbToken");
		
		ResponseEntity<AuthenticationResponse> response = client.authenticateFB(new AuthenticateFBRequest(fbToken));
		
		coToken = response.getBody().getCoToken();
	}
}
