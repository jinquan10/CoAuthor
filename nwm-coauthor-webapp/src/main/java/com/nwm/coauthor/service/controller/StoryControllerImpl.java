package com.nwm.coauthor.service.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.nwm.coauthor.exception.AlreadyAMemberException;
import com.nwm.coauthor.exception.AlreadyLikedException;
import com.nwm.coauthor.exception.AlreadyPublishedException;
import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.BadRequestException;
import com.nwm.coauthor.exception.CannotGetEntriesException;
import com.nwm.coauthor.exception.ConsecutiveEntryBySameMemberException;
import com.nwm.coauthor.exception.MoreEntriesLeftException;
import com.nwm.coauthor.exception.NoTitleForPublishingException;
import com.nwm.coauthor.exception.NonMemberException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.exception.StoryNotFoundException;
import com.nwm.coauthor.exception.UnpublishedStoryLikedException;
import com.nwm.coauthor.exception.UserIsNotLeaderException;
import com.nwm.coauthor.exception.UserLikingOwnStoryException;
import com.nwm.coauthor.exception.VersioningException;
import com.nwm.coauthor.service.manager.AuthenticationManagerImpl;
import com.nwm.coauthor.service.manager.StoryManagerImpl;
import com.nwm.coauthor.service.resource.request.ChangeTitleRequest;
import com.nwm.coauthor.service.resource.request.NewEntryRequest;
import com.nwm.coauthor.service.resource.request.NewFriendsRequest;
import com.nwm.coauthor.service.resource.request.NewStory;
import com.nwm.coauthor.service.resource.response.EntriesResponse;
import com.nwm.coauthor.service.resource.response.StoriesResponse;
import com.nwm.coauthor.service.resource.response.StoryResponse;

@Controller
@RequestMapping(value = "/story", produces = "application/json", consumes = "application/json")
public class StoryControllerImpl extends BaseControllerImpl implements StoryController {
    int minCharsPerEntry = 3;
    int maxCharsPerEntry = 1000;
    int minCharsTitle = 3;
    int maxCharsTitle = 1000;
    int minFriends = 1;

    @Autowired
    private AuthenticationManagerImpl authenticationManager;

    @Autowired
    private StoryManagerImpl storyManager;

    @Override
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<StoryResponse> createStory(@RequestHeader("Authorization") String coToken, @RequestBody NewStory createStoryRequest) throws SomethingWentWrongException,
            AuthenticationUnauthorizedException, BadRequestException {
        validateCreateStoryRequest(createStoryRequest);

//        String fbId = authenticationManager.authenticateCOTokenForFbId(coToken);
        StoryResponse storyResponse = storyManager.createStory(coToken, createStoryRequest);

        return new ResponseEntity<StoryResponse>(storyResponse, HttpStatus.CREATED);
    }

    @Override
    @RequestMapping(value = "/mine", method = RequestMethod.GET)
    public ResponseEntity<StoriesResponse> getMyStories(@RequestHeader("Authorization") String coToken) throws AuthenticationUnauthorizedException, SomethingWentWrongException {
        String fbId = authenticationManager.authenticateCOTokenForFbId(coToken);

        StoriesResponse myStories = storyManager.getMyStories(fbId);

        return new ResponseEntity<StoriesResponse>(myStories, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/{storyId}/entries/{beginIndex}/clientCharVersion/{clientCharVersion}", method = RequestMethod.GET)
    public ResponseEntity<EntriesResponse> getEntries(@RequestHeader("Authorization") String coToken, @PathVariable String storyId, @PathVariable Integer beginIndex,
            @PathVariable Integer clientCharVersion) throws BadRequestException, AuthenticationUnauthorizedException, CannotGetEntriesException, StoryNotFoundException, VersioningException,
            MoreEntriesLeftException {
        validateGetEntries(beginIndex, clientCharVersion);

        String fbId = authenticationManager.authenticateCOTokenForFbId(coToken);

        EntriesResponse entries = storyManager.getEntries(fbId, storyId, beginIndex, clientCharVersion);
        return new ResponseEntity<EntriesResponse>(entries, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/{storyId}/entry", method = RequestMethod.POST)
    public ResponseEntity<StoryResponse> newEntry(@RequestHeader("Authorization") String coToken, @PathVariable String storyId, @RequestBody NewEntryRequest newEntryRequest)
            throws BadRequestException, AuthenticationUnauthorizedException, VersioningException, StoryNotFoundException, NonMemberException, ConsecutiveEntryBySameMemberException {
        validateNewEntry(newEntryRequest);

        String fbId = authenticationManager.authenticateCOTokenForFbId(coToken);

        StoryResponse response = storyManager.newEntry(fbId, storyId, newEntryRequest.getEntry(), newEntryRequest.getCharCountForVersioning());
        return new ResponseEntity<StoryResponse>(response, HttpStatus.CREATED);
    }

    @Override
    @RequestMapping(value = "/{storyId}/mine", method = RequestMethod.GET)
    public ResponseEntity<StoryResponse> getMyStory(@RequestHeader("Authorization") String coToken, @PathVariable String storyId) throws BadRequestException, AuthenticationUnauthorizedException,
            StoryNotFoundException, NonMemberException {
        validateRequestStoryId(storyId);

        String fbId = authenticationManager.authenticateCOTokenForFbId(coToken);
        StoryResponse response = storyManager.getMyStory(fbId, storyId);

        return new ResponseEntity<StoryResponse>(response, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/{storyId}/like", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<StoryResponse> likeStory(@RequestHeader("Authorization") String coToken, @PathVariable String storyId) throws BadRequestException, AuthenticationUnauthorizedException, AlreadyLikedException,
            StoryNotFoundException, UserLikingOwnStoryException, UnpublishedStoryLikedException, SomethingWentWrongException {
        validateRequestStoryId(storyId);

        String fbId = authenticationManager.authenticateCOTokenForFbId(coToken);

        return new ResponseEntity<StoryResponse>(storyManager.likeStory(fbId, storyId), HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/{storyId}/publish", method = RequestMethod.POST)
    public ResponseEntity<StoryResponse> publishStory(@RequestHeader("Authorization") String coToken, @PathVariable String storyId) throws BadRequestException, AuthenticationUnauthorizedException, StoryNotFoundException,
            UserIsNotLeaderException, NoTitleForPublishingException, SomethingWentWrongException {
        validateRequestStoryId(storyId);

        String fbId = authenticationManager.authenticateCOTokenForFbId(coToken);

        return new ResponseEntity<StoryResponse>(storyManager.publishStory(fbId, storyId), HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/{storyId}/title", method = RequestMethod.PUT)
    public ResponseEntity<StoryResponse> changeTitle(@RequestHeader("Authorization") String coToken, @PathVariable String storyId, @RequestBody ChangeTitleRequest request) throws SomethingWentWrongException,
            AuthenticationUnauthorizedException, BadRequestException, UserIsNotLeaderException, StoryNotFoundException, AlreadyPublishedException {

        validateChangeTitleRequest(request);
        String fbId = authenticationManager.authenticateCOTokenForFbId(coToken);
        
        return new ResponseEntity<StoryResponse>(storyManager.changeTitle(fbId, storyId, request.getTitle()), HttpStatus.OK);
    }    

    @Override
    @RequestMapping(value = "/{storyId}/friends", method = RequestMethod.POST)
    public ResponseEntity<StoryResponse> newFriends(@RequestHeader("Authorization") String coToken, @PathVariable String storyId, @RequestBody NewFriendsRequest request) throws SomethingWentWrongException, BadRequestException, AuthenticationUnauthorizedException, StoryNotFoundException, AlreadyAMemberException, NonMemberException {
        validateNewFriends(request, storyId);
        
        String fbId = authenticationManager.authenticateCOTokenForFbId(coToken);
        
        return new ResponseEntity<StoryResponse>(storyManager.newFriends(fbId, storyId, request), HttpStatus.OK);
    }    
    
    private void validateNewFriends(NewFriendsRequest request, String storyId) throws BadRequestException {
        boolean isError = false;
        Map<String, String> batchErrors = new HashMap<String, String>();

        List<String> friends = request.getNewFriends();

        if (friends == null) {
            batchErrors.put("newFriends", "The friends list can't be null or empty.");
            isError = true;
        } else if (friends.size() == 0) {
            batchErrors.put("newFriends", "The friends list can't be null or empty.");
            isError = true;
        }
        
        if (!StringUtils.hasText(storyId)) {
            batchErrors.put("storyId", "The storyId must not be null or empty.");
            isError = true;
        }
        
        if (isError) {
            throw new BadRequestException(batchErrors);
        }
    }
    
    private void validateNewEntry(NewEntryRequest newEntryRequest) throws BadRequestException {
        boolean isError = false;
        Map<String, String> batchErrors = new HashMap<String, String>();

        if (newEntryRequest.getCharCountForVersioning() == null) {
            batchErrors.put("charCountForVersioning", "The char count can't be null.");
            isError = true;
        }

        if (!StringUtils.hasText(newEntryRequest.getEntry())) {
            batchErrors.put("entry", "The entry can't be null or empty.");
            isError = true;
        } else if (newEntryRequest.getEntry().length() < minCharsPerEntry) {
            batchErrors.put("entry", String.format("The entry must be at least %s characters long.", minCharsPerEntry));
            isError = true;
        } else if (newEntryRequest.getEntry().length() > maxCharsPerEntry) {
            batchErrors.put("entry", String.format("The entry must be at most %s characters long.", maxCharsPerEntry));
            isError = true;
        }

        if (isError) {
            throw new BadRequestException(batchErrors);
        }
    }

    private void validateGetEntries(Integer beginIndex, Integer currChar) throws BadRequestException {
        boolean isError = false;
        Map<String, String> batchErrors = new HashMap<String, String>();

        if (beginIndex == null) {
            batchErrors.put("beginIndex", "The beginIndex can't be null.");
            isError = true;
        }

        if (currChar == null) {
            batchErrors.put("currChar", "currChar can't be null.");
            isError = true;
        }

        if (isError) {
            throw new BadRequestException(batchErrors);
        }
    }

    protected void validateRequestStoryId(String storyId) throws BadRequestException {
        boolean isError = false;
        Map<String, String> batchErrors = new HashMap<String, String>();

        if (!StringUtils.hasText(storyId)) {
            batchErrors.put("storyId", "The storyId must not be null or empty.");
            isError = true;
        }

        if (isError) {
            throw new BadRequestException(batchErrors);
        }
    }

    private void validateChangeTitleRequest(ChangeTitleRequest request) throws BadRequestException {
        boolean isError = false;
        Map<String, String> batchErrors = new HashMap<String, String>();

        if (!StringUtils.hasText(request.getTitle())) {
            batchErrors.put("title", "The title must not be null or empty.");
            isError = true;
        }

        if (StringUtils.hasText(request.getTitle())) {
            if (request.getTitle().length() > maxCharsTitle) {
                batchErrors.put("title", "The length of your title must not exceed " + maxCharsTitle);
                isError = true;
            }
            
            if (request.getTitle().length() < minCharsTitle) {
                batchErrors.put("title", "The length of your title must be at least " + minCharsTitle);
                isError = true;
            }
        }
        
        if (isError) {
            throw new BadRequestException(batchErrors);
        }
    }

    protected void validateCreateStoryRequest(NewStory createStoryRequest) throws BadRequestException {
        boolean isError = false;
        Map<String, String> batchErrors = new HashMap<String, String>();

        if (createStoryRequest.getEntry() == null) {
            batchErrors.put("entry", "You must fill out an entry.");
            isError = true;
        } else if (createStoryRequest.getEntry().length() < minCharsPerEntry) {
            batchErrors.put("entry", "Your entry must be at least " + minCharsPerEntry + " characters long.");
            isError = true;
        } else if (createStoryRequest.getEntry().length() > maxCharsPerEntry) {
            batchErrors.put("entry", "Your entry exceeds the number of characters specified.");
            isError = true;
        }

        if (StringUtils.hasText(createStoryRequest.getTitle())) {
            if (createStoryRequest.getTitle().length() > maxCharsTitle) {
                batchErrors.put("title", "The length of your title must not exceed " + maxCharsTitle);
                isError = true;
            }
            
            if (createStoryRequest.getTitle().length() < minCharsTitle) {
                batchErrors.put("title", "The length of your title must be at least " + minCharsTitle);
                isError = true;
            }
        }

        if (isError) {
            throw new BadRequestException(batchErrors);
        }
    }

    @Override
    public ResponseEntity<StoryResponse> rateStory(String coToken, String storyId, Integer rating) throws SomethingWentWrongException {
        // TODO Auto-generated method stub
        return null;
    }
}
