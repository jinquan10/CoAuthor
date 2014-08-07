package com.nwm.coauthor.service.manager;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.mongodb.WriteResult;
import com.nwm.coauthor.Constants;
import com.nwm.coauthor.exception.AlreadyAMemberException;
import com.nwm.coauthor.exception.AlreadyLikedException;
import com.nwm.coauthor.exception.AlreadyPublishedException;
import com.nwm.coauthor.exception.CannotGetEntriesException;
import com.nwm.coauthor.exception.ConsecutiveEntryBySameMemberException;
import com.nwm.coauthor.exception.MoreEntriesLeftException;
import com.nwm.coauthor.exception.NonMemberException;
import com.nwm.coauthor.exception.SomethingWentWrongException;
import com.nwm.coauthor.exception.StoryNotFoundException;
import com.nwm.coauthor.exception.UnpublishedStoryLikedException;
import com.nwm.coauthor.exception.UserIsNotLeaderException;
import com.nwm.coauthor.exception.UserLikingOwnStoryException;
import com.nwm.coauthor.exception.VersioningException;
import com.nwm.coauthor.service.dao.EntryDAOImpl;
import com.nwm.coauthor.service.dao.StoryDAOImpl;
import com.nwm.coauthor.service.dao.UserDAOImpl;
import com.nwm.coauthor.service.model.EntryModel;
import com.nwm.coauthor.service.model.PotentialEntryModel;
import com.nwm.coauthor.service.model.StoryModel;
import com.nwm.coauthor.service.resource.request.EntryRequest;
import com.nwm.coauthor.service.resource.request.NewFriendsRequest;
import com.nwm.coauthor.service.resource.request.NewStoryRequest;
import com.nwm.coauthor.service.resource.response.EntriesResponse;
import com.nwm.coauthor.service.resource.response.EntryResponse;
import com.nwm.coauthor.service.resource.response.StoryInListResponse;
import com.nwm.coauthor.service.resource.response.StoryResponse;

@Component
public class StoryManagerImpl {
    @Autowired
    private StoryDAOImpl storyDAO;
    @Autowired
    private UserDAOImpl userDAO;
    @Autowired
    private EntryDAOImpl entryDAO;
    
    int numCharToGet = 1000;
    
    public void createStory(Long timeZoneOffsetMinutes, String coToken, NewStoryRequest request) {
        String createdByDisplayName = null;
        
        if (!StringUtils.hasText(coToken)) {
            createdByDisplayName = Constants.ANONYMOUS_USER;
        } else {
            // - fetch the user's name
        }
        
        StoryModel newStoryModel = StoryModel.fromNewStory(timeZoneOffsetMinutes, coToken, createdByDisplayName, request);
        storyDAO.createStory(newStoryModel);
    }
    
    public StoryInListResponse changeTitle(String fbId, String storyId, String title) throws StoryNotFoundException, UserIsNotLeaderException, AlreadyPublishedException, SomethingWentWrongException {
        Long now = new Date().getTime();
        WriteResult writeResult = storyDAO.changeStoryTitle(fbId, storyId, title, now);
        
        StoryInListResponse story = null;
        
        if (writeResult.getN() == 0) {
            story = storyDAO.getStory(storyId);
            
            if (story == null) {
                throw new StoryNotFoundException();
            }
            
            throw new SomethingWentWrongException(String.format("fbId: %s | storyId: %s | title: %s", fbId, storyId, title));
        }
        
        return storyDAO.getStory(storyId);
    }
    
    public EntriesResponse getEntries(String fbId, String storyId, Integer beginIndex, Integer currChar) throws CannotGetEntriesException, StoryNotFoundException, MoreEntriesLeftException, VersioningException {
        if (canGetEntries(fbId, storyId)) {
            Integer totalChars = getTotalCharsForStory(storyId, currChar);
            
            int endIndex = beginIndex + numCharToGet;
            EntriesResponse response = getEntries(storyId, beginIndex, endIndex);
            
            if (endIndex < totalChars) {
                response.setNewBeginIndex(beginIndex + calculateNumCharsFromEntries(response.getEntries()));
                throw new MoreEntriesLeftException(response);
            } else {
                return response;
            }
        } else {
            throw new CannotGetEntriesException();
        }
    }
    
    public StoryInListResponse getMyStory(String fbId, String storyId) throws StoryNotFoundException, NonMemberException {
        StoryInListResponse myStory = storyDAO.getStory(storyId);
        
        if (myStory == null) {
            throw new StoryNotFoundException();
        }
        
        return myStory;
    }
    
    private Integer calculateNumCharsFromEntries(List<EntryResponse> entries) {
        int numChars = 0;
        
        for (int i = 0; i < entries.size(); i++) {
            numChars += entries.get(i).getEntry().length();
        }
        
        return numChars;
    }
    
    private Integer getTotalCharsForStory(String storyId, Integer currChar) throws VersioningException {
        Integer totalChars = storyDAO.getTotalChars(storyId);
        
        if (!totalChars.equals(currChar)) {
            throw new VersioningException();
        }
        return totalChars;
    }
    
    private boolean canGetEntries(String fbId, String storyId) throws StoryNotFoundException {
        StoryInListResponse story = storyDAO.getStory(storyId);
        
        if (story == null) {
            throw new StoryNotFoundException();
        }
        
        return false;
    }
    
    private EntriesResponse getEntries(String storyId, Integer min, Integer max) {
        List<EntryResponse> entries = entryDAO.getEntries(storyId, min, max);
        
        EntriesResponse response = new EntriesResponse();
        response.setEntries(entries);
        return response;
    }
    
    private StoryInListResponse parseAddEntryExceptions(String fbId, String storyId, Integer charCountForVersioning) throws StoryNotFoundException, NonMemberException, VersioningException, ConsecutiveEntryBySameMemberException {
        
        StoryInListResponse story = storyDAO.getStory(storyId);
        
        if (story == null) {
            throw new StoryNotFoundException();
        }
        
        return story;
    }
    
    // public List<PrivateStoryResponse> getStoriesByFbId(String fbId) throws
    // StoryNotFoundException {
    // List<PrivateStoryResponse> stories = storyDAO.getStoriesByFbId(fbId);
    //
    // if (stories == null || stories.size() == 0) {
    // throw new StoryNotFoundException();
    // }
    //
    // return stories;
    // }
    
    // public String addEntry(String fbId, AddEntryModel request) throws
    // AddEntryException, StoryNotFoundException, AddEntryVersionException {
    // WriteResult result = storyDAO.addEntry(fbId, request);
    //
    // if (result.getN() == 0) {
    // PrivateStoryResponse privateStory =
    // storyDAO.getPrivateStory(request.getStoryId());
    //
    // if (request.getVersion() != privateStory.getVersion()) {
    // throw new AddEntryVersionException();
    // }
    //
    // throw new AddEntryException();
    // }
    //
    // return request.getEntry().getEntryId();
    // }
    //
    public StoryInListResponse likeStory(String fbId, String storyId) throws AlreadyLikedException, StoryNotFoundException, UserLikingOwnStoryException, UnpublishedStoryLikedException, SomethingWentWrongException {
        checkLikeStoryRequirements(fbId, storyId);
        checkLikeUserRequirements(fbId, storyId);
        return persistLikeStory(fbId, storyId);
    }
    
    private StoryInListResponse persistLikeStory(String fbId, String storyId) throws SomethingWentWrongException {
        WriteResult userLikeResult = userDAO.likeStory(fbId, storyId);
        
        if (userLikeResult.getN() == 0) {
            throw new SomethingWentWrongException(String.format("fbId: %s | storyId: %s", fbId, storyId));
        }
        
        return storyDAO.likeStory(storyId);
    }
    
    private void checkLikeUserRequirements(String fbId, String storyId) throws AlreadyLikedException {
        boolean isStoryLiked = userDAO.isStoryLiked(fbId, storyId);
        
        if (isStoryLiked) {
            throw new AlreadyLikedException();
        }
    }
    
    private void checkLikeStoryRequirements(String fbId, String storyId) throws StoryNotFoundException, UserLikingOwnStoryException, UnpublishedStoryLikedException {
        StoryInListResponse privateStory = storyDAO.getStory(storyId);
        
        if (privateStory == null) {
            throw new StoryNotFoundException();
        }
    }
    
    public StoryInListResponse newFriends(String fbId, String storyId, NewFriendsRequest request) throws StoryNotFoundException, AlreadyAMemberException, NonMemberException, SomethingWentWrongException {
        StoryInListResponse newFriendsResponse = storyDAO.newFriends(fbId, storyId, request.getNewFriends());
        
        if (newFriendsResponse == null) {
            newFriendsResponse = storyDAO.getStory(storyId);
            
            if (newFriendsResponse == null) {
                throw new StoryNotFoundException();
            }
            
            if (isAlreadyAMember(request, newFriendsResponse)) {
                throw new AlreadyAMemberException();
            }
            
            throw new SomethingWentWrongException(String.format("fbId: %s | storyId: %s | request: %s", fbId, storyId, request.getNewFriends().toString()));
        }
        
        return newFriendsResponse;
    }
    
    private boolean isAlreadyAMember(NewFriendsRequest request, StoryInListResponse newFriendsResponse) {
        
        return false;
    }
    
    public List<StoryInListResponse> getTopViewStories() {
        List<StoryInListResponse> stories = storyDAO.getTopViewStories(Constants.TOP_VIEW_STORIES_COUNT);
        
        // StringBuffer strb = new StringBuffer();
        // strb.append(constants.serviceUrl)
        // .append(Constants.TOP_VIEW_STORIES_PATH)
        // .append("/");
        //
        // addSelfLink(stories, strb.toString());
        
        return stories;
    }
    
    public StoryResponse getStory(String id) {
        return storyDAO.getStory(id);
    }
    
    public void entryRequest(Long timeZoneOffsetMinutes, String coToken, EntryRequest entry, String storyId) {
        Long now = DateTime.now().getMillis();
        
        String createdByDisplayName = null;
        
        if (!StringUtils.hasText(coToken)) {
            createdByDisplayName = Constants.ANONYMOUS_USER;
        } else {
            // - fetch the user's name
        }
        
        PotentialEntryModel e = new PotentialEntryModel();
        
        e.setVotes(0);
        e.setEntry(entry.getEntry());
        e.setId(UUID.randomUUID().toString());
        e.setCreatedByDisplayName(createdByDisplayName);
        e.setCreatedById(coToken);
        e.setCreatedOn(now);
        e.setLastUpdated(now);
        e.setTimeZoneOffsetMinutes(timeZoneOffsetMinutes);
        
        storyDAO.startNextEntryTimer(storyId, now + Constants.NEXT_ENTRY_DURATION);
        storyDAO.queueNextEntry(storyId, e);
    }
    
    public void incrementStoryViews(String id) {
        storyDAO.incrementStoryViews(id);
    }

    public void voteForEntry(String coToken, String storyId, String entryId) {
        storyDAO.voteForEntry(coToken, storyId, entryId);
    }

    public void pickNextEntry(String storyId) {
        StoryModel s = storyDAO.getStoryInternal(storyId);
        
        Long expirationTime = s.getNextEntryAvailableAt();
        Long now = new Date().getTime();
        
        if (now < expirationTime) { // - prevent hacks
            return;
        }
        
        int max = 0;
        PotentialEntryModel entry = null;
        
        for (PotentialEntryModel pEntry : s.getPotentialEntries()) {
            if (pEntry.getVotes() > max) {
                entry = pEntry;
            }
        }
        
        if (max == 0) {
            int randomEntryIndex = (int)(Math.random() * s.getPotentialEntries().size());
            entry = s.getPotentialEntries().get(randomEntryIndex);
        }
        
        EntryModel pickedEntry = (EntryModel)entry;
        storyDAO.assignNextEntry(storyId, pickedEntry);
    }
}
