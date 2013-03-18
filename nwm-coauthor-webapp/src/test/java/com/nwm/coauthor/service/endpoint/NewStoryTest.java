package com.nwm.coauthor.service.endpoint;

import static org.junit.Assert.*;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.BadRequestException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.service.builder.NewStoryBuilder;
import com.nwm.coauthor.service.builder.UserBuilder;
import com.nwm.coauthor.service.model.UserModel;
import com.nwm.coauthor.service.resource.request.NewStoryRequest;
import com.nwm.coauthor.service.resource.response.NewStoryResponse;

public class NewStoryTest extends BaseTest {
    @Test
    public void newStorySuccess() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, InterruptedException {
        UserModel leader = UserBuilder.createUser();
        
        ResponseEntity<NewStoryResponse> newStoryResponse = storyClient.createStory(leader.getCoToken(), NewStoryBuilder.init().build());
        assertEquals(HttpStatus.CREATED, newStoryResponse.getStatusCode());
        assertNotNull(newStoryResponse.getBody());
        assertNotNull(newStoryResponse.getBody().getStoryId());
        assertNotNull(newStoryResponse.getBody().getStoryLastUpdated());
    }

    @Test(expected = AuthenticationUnauthorizedException.class)
    public void createStoryNullCoTokenTest() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, InterruptedException {
        List<UserModel> users = UserBuilder.createUsers(null);

        storyClient.createStory(null, NewStoryBuilder.createValidStory(users, 0, null));
    }

    @Test(expected = AuthenticationUnauthorizedException.class)
    public void createStoryEmptyStringCoTokenTest() throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException, InterruptedException {
        List<UserModel> users = UserBuilder.createUsers(null);

        storyClient.createStory("", NewStoryBuilder.createValidStory(users, 0, null));
    }

    @Test
    public void createStory_EmptyCreateStoryRequestTest() throws SomethingWentWrongException, AuthenticationUnauthorizedException, InterruptedException {
        List<UserModel> users = UserBuilder.createUsers(null);

        for (int i = 0; i < users.size(); i++) {
            try {
                storyClient.createStory(users.get(i).getCoToken(), new NewStoryRequest());
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
                NewStoryRequest request = NewStoryBuilder.createValidStory(users, i, null);
                request.setTitle("123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890");
                storyClient.createStory(users.get(i).getCoToken(), request);
            } catch (BadRequestException e) {
                Map<String, String> batchErrors = e.getBatchErrors();

                Assert.assertEquals(e.toString(), 1, batchErrors.size());
            }
        }
    }
}
