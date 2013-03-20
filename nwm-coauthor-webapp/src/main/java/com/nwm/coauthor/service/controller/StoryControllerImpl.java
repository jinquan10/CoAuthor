package com.nwm.coauthor.service.controller;

import java.util.HashMap;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.nwm.coauthor.exception.AuthenticationUnauthorizedException;
import com.nwm.coauthor.exception.BadRequestException;
import com.nwm.coauthor.exception.CannotGetEntriesException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.service.manager.AuthenticationManagerImpl;
import com.nwm.coauthor.service.manager.StoryManagerImpl;
import com.nwm.coauthor.service.resource.request.NewStoryRequest;
import com.nwm.coauthor.service.resource.response.EntriesResponse;
import com.nwm.coauthor.service.resource.response.StoriesResponse;
import com.nwm.coauthor.service.resource.response.NewStoryResponse;

@Controller
@RequestMapping(value = "/story", produces = "application/json", consumes = "application/json")
public class StoryControllerImpl extends BaseControllerImpl implements StoryController {
    @Autowired
    private AuthenticationManagerImpl authenticationManager;

    @Autowired
    private StoryManagerImpl storyManager;

    @Override
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<NewStoryResponse> createStory(@RequestHeader("Authorization") String coToken, @RequestBody NewStoryRequest createStoryRequest) throws SomethingWentWrongException,
            AuthenticationUnauthorizedException, BadRequestException {
        validateCreateStoryRequest(createStoryRequest);

        String fbId = authenticationManager.authenticateCOTokenForFbId(coToken);
        NewStoryResponse storyResponse = storyManager.createStory(fbId, createStoryRequest);

        return new ResponseEntity<NewStoryResponse>(storyResponse, HttpStatus.CREATED);
    }

    @Override
    @RequestMapping(value = "/mine", method = RequestMethod.GET)
    public ResponseEntity<StoriesResponse> getMyStories(@RequestHeader("Authorization") String coToken) throws AuthenticationUnauthorizedException, SomethingWentWrongException {
        String fbId = authenticationManager.authenticateCOTokenForFbId(coToken);

        StoriesResponse myStories = storyManager.getMyStories(fbId);

        return new ResponseEntity<StoriesResponse>(myStories, HttpStatus.OK);
    }

    @Override
    @RequestMapping(value = "/{storyId}/entries", method = RequestMethod.GET)
    public ResponseEntity<EntriesResponse> getEntries(@RequestHeader("Authorization") String coToken, @PathVariable String storyId, @RequestParam Integer min, @RequestParam Integer max) throws BadRequestException, AuthenticationUnauthorizedException, CannotGetEntriesException{
    	validateGetEntries(min, max);
    	
    	String fbId = authenticationManager.authenticateCOTokenForFbId(coToken);
    	
    	EntriesResponse entries = storyManager.getEntries(fbId, storyId, min, max);
    	return new ResponseEntity<EntriesResponse>(entries, HttpStatus.OK);
    }
    
	private void validateGetEntries(Integer min, Integer max) throws BadRequestException {
		boolean isError = false;
		Map<String, String> batchErrors = new HashMap<String, String>();
		
		if(min == null){
            batchErrors.put("min", "The min current characters can't be null.");
            isError = true;			
		}

		if(max == null){
            batchErrors.put("max", "The max current characters can't be null.");
            isError = true;			
		}
		
		if (isError) {
            throw new BadRequestException(batchErrors);
        }
	}

//    @Override
//    @RequestMapping(value = "/entry", method = RequestMethod.POST)
//    public ResponseEntity<AddEntryResponse> addEntry(@RequestHeader("Authorization") String coToken, @RequestBody EntryRequest entry) throws SomethingWentWrongException,
//            AuthenticationUnauthorizedException, BadRequestException, AddEntryException, StoryNotFoundException, AddEntryVersionException {
//        validateAddEntryRequest(entry);
//
//        String fbId = authenticationManager.authenticateCOTokenForFbId(coToken);
//        String entryId = storyManager.addEntry(fbId, new AddEntryModel(entry, fbId, convertStoryIdToObjectId(entry.getStoryId())));
//
//        return new ResponseEntity<AddEntryResponse>(new AddEntryResponse(entryId), HttpStatus.CREATED);
//    }
//
//    @Override
//    @RequestMapping(value = "/{storyId}/private", method = RequestMethod.GET)
//    public ResponseEntity<PrivateStoryResponse> getStoryForEdit(@RequestHeader("Authorization") String coToken, @PathVariable String storyId) throws SomethingWentWrongException, BadRequestException,
//            AuthenticationUnauthorizedException, StoryNotFoundException, UnauthorizedException {
//        validateRequestStoryId(storyId);
//
//        String fbId = authenticationManager.authenticateCOTokenForFbId(coToken);
//        PrivateStoryResponse response = storyManager.getStoryForEdit(fbId, convertStoryIdToObjectId(storyId));
//
//        return new ResponseEntity<PrivateStoryResponse>(response, HttpStatus.OK);
//    }
//
//    @Override
//    @RequestMapping(value = "/{storyId}/private/like", method = RequestMethod.POST)
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void likeStory(@RequestHeader("Authorization") String coToken, @PathVariable String storyId) throws BadRequestException, AuthenticationUnauthorizedException, AlreadyLikedException,
//            StoryNotFoundException, UserLikingOwnStoryException, UnpublishedStoryLikedException {
//        validateRequestStoryId(storyId);
//
//        String fbId = authenticationManager.authenticateCOTokenForFbId(coToken);
//
//        storyManager.likeStory(fbId, convertStoryIdToObjectId(storyId));
//    }
//
//    @Override
//    @RequestMapping(value = "/{storyId}/publish", method = RequestMethod.POST)
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void publishStory(@RequestHeader("Authorization") String coToken, @PathVariable String storyId) throws BadRequestException, AuthenticationUnauthorizedException, StoryNotFoundException,
//            UserIsNotLeaderException, NoTitleForPublishingException {
//        validateRequestStoryId(storyId);
//
//        String fbId = authenticationManager.authenticateCOTokenForFbId(coToken);
//
//        storyManager.publishStory(fbId, convertStoryIdToObjectId(storyId));
//    }
//
//    @Override
//    @RequestMapping(value = "/{storyId}/title", method = RequestMethod.PUT)
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void changeStoryTitle(@RequestHeader("Authorization") String coToken, @PathVariable String storyId, @RequestBody ChangeTitleRequest request) throws SomethingWentWrongException, AuthenticationUnauthorizedException, BadRequestException,
//            UserIsNotLeaderException, StoryNotFoundException, AlreadyPublishedException {
//
//        validateChangeStoryRequest(request);
//        String fbId = authenticationManager.authenticateCOTokenForFbId(coToken);
//        storyManager.changeStoryTitle(fbId, convertStoryIdToObjectId(storyId), request.getTitle());
//    }
//
//    private void validateChangeStoryRequest(ChangeTitleRequest request) throws BadRequestException {
//        boolean isError = false;
//        Map<String, String> batchErrors = new HashMap<String, String>();
//
//        if (!StringUtils.hasText(request.getTitle())) {
//            batchErrors.put("title", "The title must not be null or empty.");
//            isError = true;
//        }
//
//        if (isError) {
//            throw new BadRequestException(batchErrors);
//        }
//    }
//
//    protected void validateRequestStoryId(String storyId) throws BadRequestException {
//        boolean isError = false;
//        Map<String, String> batchErrors = new HashMap<String, String>();
//
//        if (!StringUtils.hasText(storyId)) {
//            batchErrors.put("storyId", "The storyId must not be null or empty.");
//            isError = true;
//        }
//
//        if (isError) {
//            throw new BadRequestException(batchErrors);
//        }
//    }
//
//    protected ObjectId convertStoryIdToObjectId(String storyId) throws BadRequestException {
//        try {
//            return new ObjectId(storyId);
//        } catch (IllegalArgumentException e) {
//            Map<String, String> batchErrors = new HashMap<String, String>();
//            batchErrors.put("storyId", "The storyId is not a valid storyId.");
//
//            throw new BadRequestException(batchErrors);
//        }
//    }
//
//    protected void validateAddEntryRequest(EntryRequest entry) throws BadRequestException {
//        boolean isError = false;
//        Map<String, String> batchErrors = new HashMap<String, String>();
//
//        if (!StringUtils.hasText(entry.getEntry())) {
//            batchErrors.put("entry", "The entry must not be empty.");
//            isError = true;
//        }
//
//        if (!StringUtils.hasText(entry.getStoryId())) {
//            batchErrors.put("storyId", "The storyId must not be empty.");
//            isError = true;
//        }
//
//        if (entry.getVersion() == null) {
//            batchErrors.put("version", "The version must not be empty.");
//            isError = true;
//        }
//
//        if (isError) {
//            throw new BadRequestException(batchErrors);
//        }
//    }
//
//    protected StoryModel createStoryModelFromRequest() {
//    	StoryModel model = new StoryModel();
//        model.set_id(new ObjectId());
//        
//        return model;
//    }
//
    protected void validateCreateStoryRequest(NewStoryRequest createStoryRequest) throws BadRequestException {
        boolean isError = false;
        Map<String, String> batchErrors = new HashMap<String, String>();

        if (createStoryRequest.getNumCharacters() == null || createStoryRequest.getNumCharacters() < 1) {
            batchErrors.put("numCharacters", "There must be at least 1 character per entry.");
            isError = true;
        } else if (createStoryRequest.getNumCharacters() > 1000) {
            batchErrors.put("numCharacters", "There must be less than 1000 characters per entry.");
            isError = true;
        }

        if (createStoryRequest.getEntry() == null) {
            batchErrors.put("entry", "You must fill out an entry.");
            isError = true;
        } else if (createStoryRequest.getEntry().length() < 1) {
            batchErrors.put("entry", "Your entry must be at least 1 character long.");
            isError = true;
        } else if (createStoryRequest.getNumCharacters() != null && createStoryRequest.getEntry().length() > createStoryRequest.getNumCharacters()) {
            batchErrors.put("entry", "Your entry exceeds the number of characters specified.");
            isError = true;
        }

        if (createStoryRequest.getFbFriends() == null || createStoryRequest.getFbFriends().size() < 1) {
            batchErrors.put("fbFriends", "You must include at least one Facebook friend.");
            isError = true;
        }

        if (StringUtils.hasText(createStoryRequest.getTitle())) {
            if (createStoryRequest.getTitle().length() > 100) {
                batchErrors.put("title", "The length of your title must not exceed 100.");
                isError = true;
            }
        }

        if (isError) {
            throw new BadRequestException(batchErrors);
        }
    }
}
