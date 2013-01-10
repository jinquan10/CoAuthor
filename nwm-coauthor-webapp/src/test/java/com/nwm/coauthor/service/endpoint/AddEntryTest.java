package com.nwm.coauthor.service.endpoint;

import org.junit.Test;
import org.springframework.http.ResponseEntity;

import com.nwm.coauthor.exception.AddEntryException;
import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.BadRequestException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.service.builder.CreateStoryBuilder;
import com.nwm.coauthor.service.model.LoginModel;
import com.nwm.coauthor.service.resource.request.AddEntryRequest;
import com.nwm.coauthor.service.resource.response.CreateStoryResponse;

public class AddEntryTest extends TestSetup{
	@Test
	public void takeTurns_AddingEntries_To_OneStory() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, AddEntryException{
		LoginModel user = users.get(0);
		
		// - Create a story with user0, and add the rest of the users as user0's friends
		ResponseEntity<CreateStoryResponse> response = storyClient.createStory(user.getCoToken(), CreateStoryBuilder.createValidStory(users, 0));
		
		String storyId = response.getBody().getStoryId();

		int currUser = 1;
		for(int i = 0; i < users.size() * 2; i++){
			AddEntryRequest request = new AddEntryRequest();
			request.setEntry("hahahaha");
			request.setStoryId(storyId);
			request.setVersion(i);

			if(currUser == users.size()){
				currUser = 0;
			}
			
			storyClient.addEntry(users.get(currUser++).getCoToken(), request);
		}
	}
}
