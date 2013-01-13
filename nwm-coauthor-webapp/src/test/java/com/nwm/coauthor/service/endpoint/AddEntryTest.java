package com.nwm.coauthor.service.endpoint;

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.nwm.coauthor.exception.AddEntryException;
import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.BadRequestException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.service.builder.CreateStoryBuilder;
import com.nwm.coauthor.service.model.LoginModel;
import com.nwm.coauthor.service.resource.request.AddEntryRequest;
import com.nwm.coauthor.service.resource.response.AddEntryResponse;
import com.nwm.coauthor.service.resource.response.CreateStoryResponse;

public class AddEntryTest extends TestSetup{
	@Test
	public void takeTurns_AddingEntries_To_OneStory() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, AddEntryException, InterruptedException{
		List<LoginModel> users = createUsers();
		
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
			
			ResponseEntity<AddEntryResponse> entryId = storyClient.addEntry(users.get(currUser++).getCoToken(), request);
			Assert.assertNotNull(entryId);
			Assert.assertEquals(HttpStatus.CREATED, entryId.getStatusCode());
			Assert.assertNotNull(entryId.getBody());
			Assert.assertNotNull(entryId.getBody().getEntryId());
		}
	}
	
	@Test(expected = AddEntryException.class)
	public void addEntry_For_NonExistantStoryId() throws InterruptedException, SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, AddEntryException{
		List<LoginModel> users = createUsers();
		
		LoginModel user = users.get(0);
		
		ResponseEntity<CreateStoryResponse> response = storyClient.createStory(user.getCoToken(), CreateStoryBuilder.createValidStory(users, 0));
		
		AddEntryRequest request = new AddEntryRequest();
		request.setEntry("hahahaha");
		request.setStoryId("asdf");
		request.setVersion(0);
		
		storyClient.addEntry(users.get(1).getCoToken(), request);
	}
	
	@Test(expected = BadRequestException.class)
	public void addEntry_For_NullStoryId() throws InterruptedException, SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, AddEntryException{
		List<LoginModel> users = createUsers();
		
		LoginModel user = users.get(0);
		
		ResponseEntity<CreateStoryResponse> response = storyClient.createStory(user.getCoToken(), CreateStoryBuilder.createValidStory(users, 0));
		
		AddEntryRequest request = new AddEntryRequest();
		request.setEntry("hahahaha");
		request.setStoryId(null);
		request.setVersion(0);
		
		storyClient.addEntry(users.get(1).getCoToken(), request);
	}
	
	@Test(expected = BadRequestException.class)
	public void addEntry_For_EmptyStoryId() throws InterruptedException, SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, AddEntryException{
		List<LoginModel> users = createUsers();
		
		LoginModel user = users.get(0);
		
		ResponseEntity<CreateStoryResponse> response = storyClient.createStory(user.getCoToken(), CreateStoryBuilder.createValidStory(users, 0));
		
		AddEntryRequest request = new AddEntryRequest();
		request.setEntry("hahahaha");
		request.setStoryId("");
		request.setVersion(0);
		
		storyClient.addEntry(users.get(1).getCoToken(), request);
	}

	@Test(expected = BadRequestException.class)
	public void addEntry_For_EmptyEntry() throws InterruptedException, SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, AddEntryException{
		List<LoginModel> users = createUsers();
		
		LoginModel user = users.get(0);
		
		ResponseEntity<CreateStoryResponse> response = storyClient.createStory(user.getCoToken(), CreateStoryBuilder.createValidStory(users, 0));
		
		AddEntryRequest request = new AddEntryRequest();
		request.setEntry("");
		request.setStoryId(response.getBody().getStoryId());
		request.setVersion(0);
		
		storyClient.addEntry(users.get(1).getCoToken(), request);
	}
	
	@Test(expected = AddEntryException.class)
	public void addEntry_For_LengthyEntry() throws InterruptedException, SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, AddEntryException{
		List<LoginModel> users = createUsers();
		
		LoginModel user = users.get(0);
		
		ResponseEntity<CreateStoryResponse> response = storyClient.createStory(user.getCoToken(), CreateStoryBuilder.createValidStory(users, 0, 10));
		
		AddEntryRequest request = new AddEntryRequest();
		request.setEntry("01234567890");
		request.setStoryId(response.getBody().getStoryId());
		request.setVersion(0);
		
		storyClient.addEntry(users.get(1).getCoToken(), request);
	}	
	
	@Test(expected = BadRequestException.class)
	public void addEntry_For_NullEntry() throws InterruptedException, SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, AddEntryException{
		List<LoginModel> users = createUsers();
		
		LoginModel user = users.get(0);
		
		ResponseEntity<CreateStoryResponse> response = storyClient.createStory(user.getCoToken(), CreateStoryBuilder.createValidStory(users, 0));
		
		AddEntryRequest request = new AddEntryRequest();
		request.setEntry(null);
		request.setStoryId(response.getBody().getStoryId());
		request.setVersion(0);
		
		storyClient.addEntry(users.get(1).getCoToken(), request);
	}
	
	@Test(expected = BadRequestException.class)
	public void addEntry_For_NullVersion() throws InterruptedException, SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, AddEntryException{
		List<LoginModel> users = createUsers();
		
		LoginModel user = users.get(0);
		
		ResponseEntity<CreateStoryResponse> response = storyClient.createStory(user.getCoToken(), CreateStoryBuilder.createValidStory(users, 0));
		
		AddEntryRequest request = new AddEntryRequest();
		request.setEntry("haha");
		request.setStoryId(response.getBody().getStoryId());
		request.setVersion(null);
		
		storyClient.addEntry(users.get(1).getCoToken(), request);
	}
	
	@Test(expected = AddEntryException.class)
	public void addEntry_For_BadVersion() throws InterruptedException, SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, AddEntryException{
		List<LoginModel> users = createUsers();
		
		LoginModel user = users.get(0);
		
		ResponseEntity<CreateStoryResponse> response = storyClient.createStory(user.getCoToken(), CreateStoryBuilder.createValidStory(users, 0));
		
		AddEntryRequest request = new AddEntryRequest();
		request.setEntry("haha");
		request.setStoryId(response.getBody().getStoryId());
		request.setVersion(1);
		
		storyClient.addEntry(users.get(1).getCoToken(), request);
	}

	@Test(expected = AddEntryException.class)
	public void addEntry_When_NotYourTurn() throws InterruptedException, SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, AddEntryException{
		List<LoginModel> users = createUsers();
		
		LoginModel user = users.get(0);
		
		ResponseEntity<CreateStoryResponse> response = storyClient.createStory(user.getCoToken(), CreateStoryBuilder.createValidStory(users, 0));
		
		AddEntryRequest request = new AddEntryRequest();
		request.setEntry("haha");
		request.setStoryId(response.getBody().getStoryId());
		request.setVersion(0);
		
		storyClient.addEntry(users.get(0).getCoToken(), request);
	}	
	
	@Test(expected = AddEntryException.class)
	public void addEntry_To_StoryThat_UserDoesNotBelong() throws InterruptedException, SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, AddEntryException{
		List<LoginModel> users = createUsers();
		
		LoginModel user = users.get(0);
		
		ResponseEntity<CreateStoryResponse> response = storyClient.createStory(user.getCoToken(), CreateStoryBuilder.createValidStory(users, 0));
		
		AddEntryRequest request = new AddEntryRequest();
		request.setEntry("haha");
		request.setStoryId(response.getBody().getStoryId());
		request.setVersion(0);
		
		List<LoginModel> usersNotBelonging = createUsers();
		
		storyClient.addEntry(usersNotBelonging.get(0).getCoToken(), request);
	}	
}
