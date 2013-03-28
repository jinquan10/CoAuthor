package com.nwm.coauthor.service.endpoint;

import org.junit.Test;

import com.nwm.coauthor.exception.BadRequestException;
import com.nwm.coauthor.exception.ConsecutiveEntryBySameMemberException;
import com.nwm.coauthor.exception.NonMemberException;
import com.nwm.coauthor.exception.StoryNotFoundException;
import com.nwm.coauthor.exception.VersioningException;

public class NewEntryTest extends BaseTest {
    @Test
    public void newEntry(){
        
    }
    
    @Test
    public void multipleTripsToGetAllEntries(){
        
    }
    
    @Test(expected = StoryNotFoundException.class)
    public void storyNotFound() {
        
    }
    
    @Test(expected = NonMemberException.class)
    public void nonMember() {
        
    }    

    @Test(expected = VersioningException.class)
    public void invalidVersion() {
        
    }    

    @Test(expected = ConsecutiveEntryBySameMemberException.class)
    public void consecutiveEntryBySameMember() {
        
    }    
    
    @Test(expected = BadRequestException.class)
    public void nullVersion() {
        
    }    
    
    @Test(expected = BadRequestException.class)
    public void nullEntry() {
        
    }        

    @Test(expected = BadRequestException.class)
    public void emptyEntry() {
        
    }        
    
    @Test(expected = BadRequestException.class)
    public void lessThanMinCharEntry() {
        
    }        

    @Test(expected = BadRequestException.class)
    public void moreThanMaxCharEntry() {
        
    }      
    
    // @Test
    // public void takeTurns_AddingEntries_To_OneStory() throws
    // SomethingWentWrongException, AuthenticationUnauthorizedException,
    // BadRequestException, AddEntryException, InterruptedException,
    // StoryNotFoundException, AddEntryVersionException {
    // List<UserModel> users = UserBuilder.createUsers(null);
    //
    // UserModel user = users.get(0);
    //
    // // - Create a story with user0, and add the rest of the users as user0's
    // // friends
    // ResponseEntity<NewStoryResponse> response =
    // storyClient.createStory(user.getCoToken(),
    // CreateStoryBuilder.createValidStory(users, 0, null));
    //
    // String storyId = response.getBody().getStoryId();
    //
    // int currUser = 1;
    // for (int i = 0; i < users.size() * 2; i++) {
    // EntryRequest request = new EntryRequest();
    // request.setEntry("hahahaha");
    // request.setStoryId(storyId);
    // request.setVersion(i);
    //
    // if (currUser == users.size()) {
    // currUser = 0;
    // }
    //
    // ResponseEntity<AddEntryResponse> entryId =
    // storyClient.addEntry(users.get(currUser++).getCoToken(), request);
    // Assert.assertNotNull(entryId);
    // Assert.assertEquals(HttpStatus.CREATED, entryId.getStatusCode());
    // Assert.assertNotNull(entryId.getBody());
    // Assert.assertNotNull(entryId.getBody().getEntryId());
    // }
    // }
    //
    // @Test(expected = BadRequestException.class)
    // public void addEntry_For_NonExistantStoryId() throws
    // InterruptedException, SomethingWentWrongException,
    // AuthenticationUnauthorizedException, BadRequestException,
    // AddEntryException,
    // StoryNotFoundException, AddEntryVersionException {
    // List<UserModel> users = UserBuilder.createUsers(null);
    //
    // UserModel user = users.get(0);
    //
    // ResponseEntity<NewStoryResponse> response =
    // storyClient.createStory(user.getCoToken(),
    // CreateStoryBuilder.createValidStory(users, 0, null));
    //
    // EntryRequest request = new EntryRequest();
    // request.setEntry("hahahaha");
    // request.setStoryId("asdf");
    // request.setVersion(0);
    //
    // storyClient.addEntry(users.get(1).getCoToken(), request);
    // }
    //
    // @Test(expected = BadRequestException.class)
    // public void addEntry_For_NullStoryId() throws InterruptedException,
    // SomethingWentWrongException, AuthenticationUnauthorizedException,
    // BadRequestException, AddEntryException,
    // StoryNotFoundException, AddEntryVersionException {
    // List<UserModel> users = UserBuilder.createUsers(null);
    //
    // UserModel user = users.get(0);
    //
    // ResponseEntity<NewStoryResponse> response =
    // storyClient.createStory(user.getCoToken(),
    // CreateStoryBuilder.createValidStory(users, 0, null));
    //
    // EntryRequest request = new EntryRequest();
    // request.setEntry("hahahaha");
    // request.setStoryId(null);
    // request.setVersion(0);
    //
    // storyClient.addEntry(users.get(1).getCoToken(), request);
    // }
    //
    // @Test(expected = BadRequestException.class)
    // public void addEntry_For_EmptyStoryId() throws InterruptedException,
    // SomethingWentWrongException, AuthenticationUnauthorizedException,
    // BadRequestException, AddEntryException,
    // StoryNotFoundException, AddEntryVersionException {
    // List<UserModel> users = UserBuilder.createUsers(null);
    //
    // UserModel user = users.get(0);
    //
    // ResponseEntity<NewStoryResponse> response =
    // storyClient.createStory(user.getCoToken(),
    // CreateStoryBuilder.createValidStory(users, 0, null));
    //
    // EntryRequest request = new EntryRequest();
    // request.setEntry("hahahaha");
    // request.setStoryId("");
    // request.setVersion(0);
    //
    // storyClient.addEntry(users.get(1).getCoToken(), request);
    // }
    //
    // @Test(expected = BadRequestException.class)
    // public void addEntry_For_EmptyEntry() throws InterruptedException,
    // SomethingWentWrongException, AuthenticationUnauthorizedException,
    // BadRequestException, AddEntryException,
    // StoryNotFoundException, AddEntryVersionException {
    // List<UserModel> users = UserBuilder.createUsers(null);
    //
    // UserModel user = users.get(0);
    //
    // ResponseEntity<NewStoryResponse> response =
    // storyClient.createStory(user.getCoToken(),
    // CreateStoryBuilder.createValidStory(users, 0, null));
    //
    // EntryRequest request = new EntryRequest();
    // request.setEntry("");
    // request.setStoryId(response.getBody().getStoryId());
    // request.setVersion(0);
    //
    // storyClient.addEntry(users.get(1).getCoToken(), request);
    // }
    //
    // @Test(expected = AddEntryException.class)
    // public void addEntry_For_LengthyEntry() throws InterruptedException,
    // SomethingWentWrongException, AuthenticationUnauthorizedException,
    // BadRequestException, AddEntryException,
    // StoryNotFoundException, AddEntryVersionException {
    // List<UserModel> users = UserBuilder.createUsers(null);
    //
    // UserModel user = users.get(0);
    //
    // ResponseEntity<NewStoryResponse> response =
    // storyClient.createStory(user.getCoToken(),
    // CreateStoryBuilder.createValidStory(users, 0, null, 10));
    //
    // EntryRequest request = new EntryRequest();
    // request.setEntry("01234567890");
    // request.setStoryId(response.getBody().getStoryId());
    // request.setVersion(0);
    //
    // storyClient.addEntry(users.get(1).getCoToken(), request);
    // }
    //
    // @Test(expected = BadRequestException.class)
    // public void addEntry_For_NullEntry() throws InterruptedException,
    // SomethingWentWrongException, AuthenticationUnauthorizedException,
    // BadRequestException, AddEntryException, StoryNotFoundException,
    // AddEntryVersionException {
    // List<UserModel> users = UserBuilder.createUsers(null);
    //
    // UserModel user = users.get(0);
    //
    // ResponseEntity<NewStoryResponse> response =
    // storyClient.createStory(user.getCoToken(),
    // CreateStoryBuilder.createValidStory(users, 0, null));
    //
    // EntryRequest request = new EntryRequest();
    // request.setEntry(null);
    // request.setStoryId(response.getBody().getStoryId());
    // request.setVersion(0);
    //
    // storyClient.addEntry(users.get(1).getCoToken(), request);
    // }
    //
    // @Test(expected = BadRequestException.class)
    // public void addEntry_For_NullVersion() throws InterruptedException,
    // SomethingWentWrongException, AuthenticationUnauthorizedException,
    // BadRequestException, AddEntryException,
    // StoryNotFoundException, AddEntryVersionException {
    // List<UserModel> users = UserBuilder.createUsers(null);
    //
    // UserModel user = users.get(0);
    //
    // ResponseEntity<NewStoryResponse> response =
    // storyClient.createStory(user.getCoToken(),
    // CreateStoryBuilder.createValidStory(users, 0, null));
    //
    // EntryRequest request = new EntryRequest();
    // request.setEntry("haha");
    // request.setStoryId(response.getBody().getStoryId());
    // request.setVersion(null);
    //
    // storyClient.addEntry(users.get(1).getCoToken(), request);
    // }
    //
    // @Test(expected = AddEntryVersionException.class)
    // public void addEntry_For_BadVersion() throws InterruptedException,
    // SomethingWentWrongException, AuthenticationUnauthorizedException,
    // BadRequestException, AddEntryException,
    // StoryNotFoundException, AddEntryVersionException {
    // List<UserModel> users = UserBuilder.createUsers(null);
    //
    // UserModel user = users.get(0);
    //
    // ResponseEntity<NewStoryResponse> response =
    // storyClient.createStory(user.getCoToken(),
    // CreateStoryBuilder.createValidStory(users, 0, null));
    //
    // EntryRequest request = new EntryRequest();
    // request.setEntry("haha");
    // request.setStoryId(response.getBody().getStoryId());
    // request.setVersion(1);
    //
    // storyClient.addEntry(users.get(1).getCoToken(), request);
    // }
    //
    // @Test(expected = AddEntryException.class)
    // public void addEntry_When_NotYourTurn() throws InterruptedException,
    // SomethingWentWrongException, AuthenticationUnauthorizedException,
    // BadRequestException, AddEntryException,
    // StoryNotFoundException, AddEntryVersionException {
    // List<UserModel> users = UserBuilder.createUsers(null);
    //
    // UserModel user = users.get(0);
    //
    // ResponseEntity<NewStoryResponse> response =
    // storyClient.createStory(user.getCoToken(),
    // CreateStoryBuilder.createValidStory(users, 0, null));
    //
    // EntryRequest request = new EntryRequest();
    // request.setEntry("haha");
    // request.setStoryId(response.getBody().getStoryId());
    // request.setVersion(0);
    //
    // storyClient.addEntry(users.get(0).getCoToken(), request);
    // }
    //
    // @Test(expected = AddEntryException.class)
    // public void addEntry_To_StoryThat_UserDoesNotBelong() throws
    // InterruptedException, SomethingWentWrongException,
    // AuthenticationUnauthorizedException, BadRequestException,
    // AddEntryException,
    // StoryNotFoundException, AddEntryVersionException {
    // List<UserModel> users = UserBuilder.createUsers(null);
    //
    // UserModel user = users.get(0);
    //
    // ResponseEntity<NewStoryResponse> response =
    // storyClient.createStory(user.getCoToken(),
    // CreateStoryBuilder.createValidStory(users, 0, null));
    //
    // EntryRequest request = new EntryRequest();
    // request.setEntry("haha");
    // request.setStoryId(response.getBody().getStoryId());
    // request.setVersion(0);
    //
    // List<UserModel> usersNotBelonging = UserBuilder.createUsers(null);
    //
    // storyClient.addEntry(usersNotBelonging.get(0).getCoToken(), request);
    // }
}
