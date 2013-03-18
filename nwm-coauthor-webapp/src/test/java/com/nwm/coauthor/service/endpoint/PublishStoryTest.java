package com.nwm.coauthor.service.endpoint;



public class PublishStoryTest extends BaseTest {
//    @Test(expected = UserIsNotLeaderException.class)
//    public void userIsNotLeader() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, InterruptedException, StoryNotFoundException, UserIsNotLeaderException, NoTitleForPublishingException, UnauthorizedException {
//        UserModel leader = UserBuilder.createUser();
//        List<UserModel> friends = UserBuilder.createUsers(2);
//        
//        NewStoryRequest request = CreateStoryBuilder.init().fbFriends(UserBuilder.exchangeForFbIds(friends)).title("the one").build();
//
//        ResponseEntity<NewStoryResponse> storyResponse = storyClient.createStory(leader.getCoToken(), request);
//        try{
//            storyClient.publishStory(friends.get(0).getCoToken(), storyResponse.getBody().getStoryId());
//        }finally{
//            ResponseEntity<PrivateStoryResponse> storyForEditResponse = storyClient.getStoryForEdit(leader.getCoToken(), storyResponse.getBody().getStoryId());
//            Assert.assertEquals(false, storyForEditResponse.getBody().getIsPublished());            
//        }
//    }
//
//    @Test(expected = UserIsNotLeaderException.class)
//    public void userIsANonMember() throws InterruptedException, SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException, UserIsNotLeaderException, NoTitleForPublishingException, UnauthorizedException {
//        UserModel leader = UserBuilder.createUser();
//        UserModel nonMember = UserBuilder.createUser();
//
//        NewStoryRequest storyRequest = CreateStoryBuilder.init().title("The one").build();
//        ResponseEntity<NewStoryResponse> storyResponse = storyClient.createStory(leader.getCoToken(), storyRequest);
//        try{
//            storyClient.publishStory(nonMember.getCoToken(), storyResponse.getBody().getStoryId());
//        }finally{
//            ResponseEntity<PrivateStoryResponse> storyForEditResponse = storyClient.getStoryForEdit(leader.getCoToken(), storyResponse.getBody().getStoryId());
//            Assert.assertEquals(false, storyForEditResponse.getBody().getIsPublished());            
//        }
//    }
//
//    @Test(expected = NoTitleForPublishingException.class)
//    public void hasNullTitle() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException, UserIsNotLeaderException, NoTitleForPublishingException, UnauthorizedException {
//        UserModel leader = UserBuilder.createUser();
//
//        NewStoryRequest storyRequest = CreateStoryBuilder.init().title(null).build();
//        ResponseEntity<NewStoryResponse> storyResponse = storyClient.createStory(leader.getCoToken(), storyRequest);
//        try{
//            storyClient.publishStory(leader.getCoToken(), storyResponse.getBody().getStoryId());
//        }finally{
//            ResponseEntity<PrivateStoryResponse> storyForEditResponse = storyClient.getStoryForEdit(leader.getCoToken(), storyResponse.getBody().getStoryId());
//            Assert.assertEquals(false, storyForEditResponse.getBody().getIsPublished());            
//        }
//    }
//
//    @Test(expected = StoryNotFoundException.class)
//    public void nonExistantStory() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException, UserIsNotLeaderException, NoTitleForPublishingException {
//        UserModel leader = UserBuilder.createUser();
//
//        storyClient.publishStory(leader.getCoToken(), new ObjectId().toString());
//    }    
//    
//    @Test
//    public void publishesStory() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException, UserIsNotLeaderException, NoTitleForPublishingException, UnauthorizedException {
//        UserModel leader = UserBuilder.createUser();
//
//        NewStoryRequest storyRequest = CreateStoryBuilder.init().title("The one").build();
//        ResponseEntity<NewStoryResponse> storyResponse = storyClient.createStory(leader.getCoToken(), storyRequest);
//        
//        try{
//            storyClient.publishStory(leader.getCoToken(), storyResponse.getBody().getStoryId());
//        }finally{
//            ResponseEntity<PrivateStoryResponse> storyForEditResponse = storyClient.getStoryForEdit(leader.getCoToken(), storyResponse.getBody().getStoryId());
//            Assert.assertEquals(true, storyForEditResponse.getBody().getIsPublished());            
//        }
//    }
//    
//    @Test(expected = NoTitleForPublishingException.class)
//    public void hasEmptyTitle() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, StoryNotFoundException, UserIsNotLeaderException, NoTitleForPublishingException, UnauthorizedException {
//        UserModel leader = UserBuilder.createUser();
//
//        NewStoryRequest storyRequest = CreateStoryBuilder.init().title("").build();
//        ResponseEntity<NewStoryResponse> storyResponse = storyClient.createStory(leader.getCoToken(), storyRequest);
//        
//        try{
//            storyClient.publishStory(leader.getCoToken(), storyResponse.getBody().getStoryId());
//        }finally{
//            ResponseEntity<PrivateStoryResponse> storyForEditResponse = storyClient.getStoryForEdit(leader.getCoToken(), storyResponse.getBody().getStoryId());
//            Assert.assertEquals(false, storyForEditResponse.getBody().getIsPublished());            
//        }
//    }    
}
