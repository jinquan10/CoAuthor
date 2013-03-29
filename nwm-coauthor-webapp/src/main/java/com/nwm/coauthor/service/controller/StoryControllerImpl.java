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
import com.nwm.coauthor.exception.ConsecutiveEntryBySameMemberException;
import com.nwm.coauthor.exception.NonMemberException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.exception.StoryNotFoundException;
import com.nwm.coauthor.exception.VersioningException;
import com.nwm.coauthor.service.manager.AuthenticationManagerImpl;
import com.nwm.coauthor.service.manager.StoryManagerImpl;
import com.nwm.coauthor.service.resource.request.NewEntryRequest;
import com.nwm.coauthor.service.resource.request.NewStoryRequest;
import com.nwm.coauthor.service.resource.response.EntriesResponse;
import com.nwm.coauthor.service.resource.response.StoriesResponse;
import com.nwm.coauthor.service.resource.response.StoryResponse;

@Controller
@RequestMapping(value = "/story", produces = "application/json", consumes = "application/json")
public class StoryControllerImpl extends BaseControllerImpl implements StoryController {
    int minCharsPerEntry = 3;
    int maxCharsPerEntry = 1000;
    int maxCharsTitle = 1000;
    int minFriends = 1;
	
	@Autowired
    private AuthenticationManagerImpl authenticationManager;

    @Autowired
    private StoryManagerImpl storyManager;

    @Override
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<StoryResponse> createStory(@RequestHeader("Authorization") String coToken, @RequestBody NewStoryRequest createStoryRequest) throws SomethingWentWrongException,
            AuthenticationUnauthorizedException, BadRequestException {
        validateCreateStoryRequest(createStoryRequest);

        String fbId = authenticationManager.authenticateCOTokenForFbId(coToken);
        StoryResponse storyResponse = storyManager.createStory(fbId, createStoryRequest);

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
    @RequestMapping(value = "/{storyId}/entries/{beginIndex}", method = RequestMethod.GET)
    public ResponseEntity<EntriesResponse> getEntries(@RequestHeader("Authorization") String coToken, @PathVariable String storyId, @PathVariable Integer beginIndex) throws BadRequestException, AuthenticationUnauthorizedException, CannotGetEntriesException, StoryNotFoundException{
    	validateGetEntries(beginIndex);
    	
    	String fbId = authenticationManager.authenticateCOTokenForFbId(coToken);
    	
    	EntriesResponse entries = storyManager.getEntries(fbId, storyId, beginIndex);
        return new ResponseEntity<EntriesResponse>(entries, HttpStatus.PARTIAL_CONTENT);
    }
    
    @Override
    @RequestMapping(value = "/{storyId}/entry", method = RequestMethod.POST)
    public ResponseEntity<StoryResponse> newEntry(@RequestHeader("Authorization") String coToken, @PathVariable String storyId, @RequestBody NewEntryRequest newEntryRequest) throws BadRequestException, AuthenticationUnauthorizedException, VersioningException, StoryNotFoundException, NonMemberException, ConsecutiveEntryBySameMemberException{
        validateNewEntry(newEntryRequest);
        
        String fbId = authenticationManager.authenticateCOTokenForFbId(coToken);

        StoryResponse response = storyManager.newEntry(fbId, storyId, newEntryRequest.getEntry(), newEntryRequest.getCharCountForVersioning());
        return new ResponseEntity<StoryResponse>(response, HttpStatus.CREATED);
    }
    
	private void validateNewEntry(NewEntryRequest newEntryRequest) throws BadRequestException {
	    boolean isError = false;
        Map<String, String> batchErrors = new HashMap<String, String>();
        
        if(newEntryRequest.getCharCountForVersioning() == null){
            batchErrors.put("charCountForVersioning", "The char count can't be null.");
            isError = true;         
        }

        if(!StringUtils.hasText(newEntryRequest.getEntry())){
            batchErrors.put("entry", "The entry can't be null or empty.");
            isError = true;         
        }else if(newEntryRequest.getEntry().length() < minCharsPerEntry){
            batchErrors.put("entry", String.format("The entry must be at least %s characters long.", minCharsPerEntry));
            isError = true;         
        }else if(newEntryRequest.getEntry().length() > maxCharsPerEntry){
            batchErrors.put("entry", String.format("The entry must be at most %s characters long.", maxCharsPerEntry));
            isError = true;
        }
        
        if (isError) {
            throw new BadRequestException(batchErrors);
        }
    }

    private void validateGetEntries(Integer beginIndex) throws BadRequestException {
		boolean isError = false;
		Map<String, String> batchErrors = new HashMap<String, String>();
		
		if(beginIndex == null){
            batchErrors.put("beginIndex", "The beginIndex can't be null.");
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

        if (createStoryRequest.getFbFriends() == null || createStoryRequest.getFbFriends().size() < minFriends) {
            batchErrors.put("fbFriends", "You must include at least " + minFriends + " Facebook friend.");
            isError = true;
        }

        if (StringUtils.hasText(createStoryRequest.getTitle())) {
            if (createStoryRequest.getTitle().length() > maxCharsTitle) {
                batchErrors.put("title", "The length of your title must not exceed " + maxCharsTitle);
                isError = true;
            }
        }

        if (isError) {
            throw new BadRequestException(batchErrors);
        }
    }
}
