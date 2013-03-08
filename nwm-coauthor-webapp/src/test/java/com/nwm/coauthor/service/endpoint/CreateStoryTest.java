package com.nwm.coauthor.service.endpoint;

import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.BadRequestException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.service.builder.CreateStoryBuilder;
import com.nwm.coauthor.service.builder.UserBuilder;
import com.nwm.coauthor.service.model.UserModel;
import com.nwm.coauthor.service.resource.request.CreateStoryRequest;
import com.nwm.coauthor.service.resource.response.CreateStoryResponse;

public class CreateStoryTest extends TestSetup {
    @Test
    public void createStorySuccessTest() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, InterruptedException {
        List<UserModel> users = UserBuilder.createUsers(null);

        for (int i = 0; i < users.size(); i++) {
            ResponseEntity<CreateStoryResponse> response = storyClient.createStory(users.get(i).getCoToken(), CreateStoryBuilder.createValidStory(users, i, null));

            Assert.assertEquals(HttpStatus.CREATED, response.getStatusCode());
            Assert.assertNotNull(response.getBody());
            Assert.assertTrue(StringUtils.hasText(response.getBody().getStoryId()));
        }
    }

    @Test(expected = AuthenticationUnauthorizedException.class)
    public void createStoryNullCoTokenTest() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, InterruptedException {
        List<UserModel> users = UserBuilder.createUsers(null);

        storyClient.createStory(null, CreateStoryBuilder.createValidStory(users, 0, null));
    }

    @Test(expected = AuthenticationUnauthorizedException.class)
    public void createStoryEmptyStringCoTokenTest() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, InterruptedException {
        List<UserModel> users = UserBuilder.createUsers(null);

        storyClient.createStory("", CreateStoryBuilder.createValidStory(users, 0, null));
    }

    @Test
    public void createStory_EmptyCreateStoryRequestTest() throws SomethingWentWrongException, AuthenticationUnauthorizedException, InterruptedException {
        List<UserModel> users = UserBuilder.createUsers(null);

        for (int i = 0; i < users.size(); i++) {
            try {
                storyClient.createStory(users.get(i).getCoToken(), new CreateStoryRequest());
            } catch (BadRequestException e) {
                Map<String, String> batchErrors = e.getBatchErrors();

                Assert.assertEquals(e.toString(), 3, batchErrors.size());
            }
        }
    }

    @Test
    public void createStoryNullResourceTest() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, InterruptedException {
        List<UserModel> users = UserBuilder.createUsers(null);

        for (int i = 0; i < users.size(); i++) {
            try {
                storyClient.createStory(users.get(i).getCoToken(), null);
            } catch (BadRequestException e) {
                Map<String, String> batchErrors = e.getBatchErrors();

                Assert.assertEquals(e.toString(), 3, batchErrors.size());
            }
        }
    }

    @Test
    public void createStoryLengthyTitleTest() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, InterruptedException {
        List<UserModel> users = UserBuilder.createUsers(null);

        for (int i = 0; i < users.size(); i++) {
            try {
                CreateStoryRequest request = CreateStoryBuilder.createValidStory(users, i, null);
                request.setTitle("123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");
                storyClient.createStory(users.get(i).getCoToken(), request);
            } catch (BadRequestException e) {
                Map<String, String> batchErrors = e.getBatchErrors();

                Assert.assertEquals(e.toString(), 1, batchErrors.size());
            }
        }
    }
}
