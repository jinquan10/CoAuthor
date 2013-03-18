package com.nwm.coauthor.service.endpoint;


// - TODO: test for a user having no private stories, assert story not found exception
public class GetPrivateStoriesTest extends BaseTest {
//    @Test(expected = StoryNotFoundException.class)
//    public void userWithNoPrivateStories_GetPrivateStories_Assert_StoryNotFoundException() throws InterruptedException, AuthenticationUnauthorizedException, SomethingWentWrongException,
//            StoryNotFoundException {
//        List<UserModel> users = UserBuilder.createUsers(1);
//
//        UserModel user = users.get(0);
//        storyClient.getPrivateStories(user.getCoToken());
//    }
//
//    @Test
//    public void getPrivateStoriesSuccess() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, InterruptedException, StoryNotFoundException {
//        List<UserModel> users = UserBuilder.createUsers(null);
//
//        for (int i = 0; i < users.size(); i++) {
//            storyClient.createStory(users.get(i).getCoToken(), CreateStoryBuilder.createValidStory(users, i, null));
//        }
//
//        for (int i = 0; i < users.size(); i++) {
//            ResponseEntity<PrivateStoriesResponseWrapper> response = storyClient.getPrivateStories(users.get(i).getCoToken());
//
//            PrivateStoriesResponseWrapper body = response.getBody();
//            Assert.assertNotNull(body);
//
//            List<PrivateStoryResponse> stories = body.getStories();
//            Assert.assertNotNull(stories);
//
//            Assert.assertTrue(stories.size() == users.size());
//
//            for (int j = 0; j < users.size(); j++) {
//                PrivateStoryResponse story = stories.get(j);
//
//                Assert.assertTrue(StringUtils.hasText(story.get_id()));
//                Assert.assertTrue(StringUtils.hasText(story.getLastFriendEntry()));
//                Assert.assertFalse(story.getIsPublished());
//                Assert.assertNotNull(story.getEntries());
//                Assert.assertTrue(story.getEntries().size() == 1);
//                Assert.assertNotNull(story.getFbFriends());
//                Assert.assertNotNull(story.getNumCharacters());
//                Assert.assertTrue(StringUtils.hasText(story.getLeaderFbId()));
//                Assert.assertTrue(story.getVersion() == 0);
//                Assert.assertEquals(story.getLikes(), new Integer(0));
//            }
//        }
//    }
//
//    @Test
//    public void getPrivateStories_UserShouldSeeOneEntry_WhenMoreThanOneEntryIsSubmitted() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException,
//            AddEntryException, InterruptedException, StoryNotFoundException, AddEntryVersionException {
//        List<UserModel> users = UserBuilder.createUsers(null);
//
//        UserModel user = users.get(0);
//
//        // - Create a story with user0, and add the rest of the users as user0's
//        // friends
//        ResponseEntity<NewStoryResponse> response = storyClient.createStory(user.getCoToken(), CreateStoryBuilder.createValidStory(users, 0, null));
//
//        String storyId = response.getBody().getStoryId();
//
//        EntryRequest request = new EntryRequest();
//        request.setEntry("hahahaha");
//        request.setStoryId(storyId);
//        request.setVersion(0);
//
//        storyClient.addEntry(users.get(1).getCoToken(), request);
//
//        ResponseEntity<PrivateStoriesResponseWrapper> privateStoriesResponse = storyClient.getPrivateStories(user.getCoToken());
//
//        PrivateStoriesResponseWrapper body = privateStoriesResponse.getBody();
//        Assert.assertNotNull(body);
//
//        List<PrivateStoryResponse> stories = body.getStories();
//        Assert.assertNotNull(stories);
//
//        Assert.assertTrue(stories.size() == 1);
//
//        for (int j = 0; j < users.size(); j++) {
//            PrivateStoryResponse story = stories.get(0);
//
//            Assert.assertTrue(StringUtils.hasText(story.get_id()));
//            Assert.assertTrue(StringUtils.hasText(story.getLastFriendEntry()));
//            Assert.assertFalse(story.getIsPublished());
//            Assert.assertNotNull(story.getEntries());
//            Assert.assertTrue(story.getEntries().size() == 1);
//            Assert.assertNotNull(story.getFbFriends());
//            Assert.assertNotNull(story.getNumCharacters());
//            Assert.assertTrue(StringUtils.hasText(story.getLeaderFbId()));
//            Assert.assertTrue(story.getVersion() == 1);
//        }
//    }
}
