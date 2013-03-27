package com.nwm.coauthor.service.manager;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.mongodb.WriteResult;
import com.nwm.coauthor.exception.AlreadyPublishedException;
import com.nwm.coauthor.exception.CannotGetEntriesException;
import com.nwm.coauthor.exception.ConsecutiveNewEntryException;
import com.nwm.coauthor.exception.NonMemberOrLeaderException;
import com.nwm.coauthor.exception.StoryNotFoundException;
import com.nwm.coauthor.exception.VersioningException;
import com.nwm.coauthor.service.dao.CommentDAOImpl;
import com.nwm.coauthor.service.dao.EntryDAOImpl;
import com.nwm.coauthor.service.dao.StoryDAOImpl;
import com.nwm.coauthor.service.dao.UserDAOImpl;
import com.nwm.coauthor.service.model.EntryModel;
import com.nwm.coauthor.service.model.StoryModel;
import com.nwm.coauthor.service.resource.request.NewStoryRequest;
import com.nwm.coauthor.service.resource.response.EntriesResponse;
import com.nwm.coauthor.service.resource.response.EntryResponse;
import com.nwm.coauthor.service.resource.response.StoriesResponse;
import com.nwm.coauthor.service.resource.response.StoryResponse;

@Component
public class StoryManagerImpl {
    @Autowired
    private StoryDAOImpl storyDAO;
    @Autowired
    private UserDAOImpl userDAO;
    @Autowired
    private CommentDAOImpl commentDAO;
    @Autowired
    private EntryDAOImpl entryDAO;
    
    int numCharToGet = 100000;
    
    public StoryResponse createStory(String fbId, NewStoryRequest request) {
    	StoryModel newStoryModel = StoryModel.createStoryModelFromRequest(fbId, request);
    	EntryModel newEntryModel = EntryModel.newEntryModel(newStoryModel.getStoryId(), fbId, request.getEntry(), newStoryModel.getCurrEntryCharCount()); 
    	
        storyDAO.createStory(newStoryModel);
        entryDAO.addEntry(newEntryModel);
        
        StoryResponse storyResponse = new StoryResponse();
        BeanUtils.copyProperties(newStoryModel, storyResponse);
        
        return storyResponse;
    }

	public StoriesResponse getMyStories(String fbId) {
		return StoriesResponse.wrapStoryCovers(storyDAO.getMyStories(fbId));
	}

	public EntriesResponse getEntries(String fbId, String storyId, Integer beginIndex) throws CannotGetEntriesException{
		if(canGetEntries(fbId, storyId)){
	        int endIndex = beginIndex + numCharToGet;
		    
	        return getEntries(storyId, beginIndex, endIndex);
		}else{
			throw new CannotGetEntriesException(); 
		}
	}

    private boolean canGetEntries(String fbId, String storyId) {
        StoryModel story = storyDAO.getStory(storyId);
        
        if(story.getLeaderFbId().equals(fbId)){
            return true;
        }
        
        if(story.getFbFriends().contains(fbId)){
            return true;
        }
        
        if(story.getIsPublished() != null && story.getIsPublished()){
            return true;
        }
        
        return false;
    }

    private EntriesResponse getEntries(String storyId, Integer min, Integer max) {
        List<EntryResponse> entries = entryDAO.getEntries(storyId, min, max);
        
        EntriesResponse response = new EntriesResponse();
        response.setEntries(entries);
        return response;
    }

    public void addEntry(String fbId, String storyId, String entry, Integer charCountForVersioning) throws VersioningException, StoryNotFoundException, NonMemberOrLeaderException, ConsecutiveNewEntryException {
        WriteResult result = storyDAO.updateStoryForAddingEntry(fbId, storyId, entry, charCountForVersioning);
        parseAddEntryExceptions(fbId, storyId, charCountForVersioning, result);
        entryDAO.addEntry(EntryModel.newEntryModel(storyId, fbId, entry, charCountForVersioning));
    }

    private void parseAddEntryExceptions(String fbId, String storyId, Integer charCountForVersioning, WriteResult result) throws StoryNotFoundException, NonMemberOrLeaderException,
            VersioningException, ConsecutiveNewEntryException {
        if(result.getN() == 0){
            StoryModel storyModel = storyDAO.getStory(storyId);

            if(storyModel == null){
                throw new StoryNotFoundException();
            }
            
            if(!storyModel.getLeaderFbId().equals(fbId) && !storyModel.getFbFriends().contains(fbId)){
                throw new NonMemberOrLeaderException();
            }
            
            if(storyModel.getCurrEntryCharCount() != charCountForVersioning){
                throw new VersioningException();
            }
            
            if(storyModel.getLastFriendWithEntry().equals(fbId)){
                throw new ConsecutiveNewEntryException();
            }
        }
    }

//    public List<PrivateStoryResponse> getStoriesByFbId(String fbId) throws StoryNotFoundException {
//        List<PrivateStoryResponse> stories = storyDAO.getStoriesByFbId(fbId);
//
//        if (stories == null || stories.size() == 0) {
//            throw new StoryNotFoundException();
//        }
//
//        return stories;
//    }

//    public String addEntry(String fbId, AddEntryModel request) throws AddEntryException, StoryNotFoundException, AddEntryVersionException {
//        WriteResult result = storyDAO.addEntry(fbId, request);
//
//        if (result.getN() == 0) {
//            PrivateStoryResponse privateStory = storyDAO.getPrivateStory(request.getStoryId());
//
//            if (request.getVersion() != privateStory.getVersion()) {
//                throw new AddEntryVersionException();
//            }
//
//            throw new AddEntryException();
//        }
//
//        return request.getEntry().getEntryId();
//    }

//    public PrivateStoryResponse getStoryForEdit(String fbId, ObjectId storyId) throws StoryNotFoundException, UnauthorizedException {
//        PrivateStoryResponse privateStory = storyDAO.getPrivateStory(storyId);
//
//        if (privateStory == null) {
//            throw new StoryNotFoundException();
//        }
//
//        if (!privateStory.getLeaderFbId().equals(fbId)) {
//            if (!privateStory.getFbFriends().contains(fbId)) {
//                throw new UnauthorizedException();
//            }
//        }
//
//        return privateStory;
//    }
//
//    public void likeStory(String fbId, ObjectId storyId) throws AlreadyLikedException, StoryNotFoundException, UserLikingOwnStoryException, UnpublishedStoryLikedException {
//        checkLikeStoryRequirements(fbId, storyId);
//        checkLikeUserRequirements(fbId, storyId);
//        persistLikeStory(fbId, storyId);
//    }
//
//    private void persistLikeStory(String fbId, ObjectId storyId) {
//        userDAO.likeStory(fbId, storyId);
//        storyDAO.likeStory(storyId);
//    }
//
//    private void checkLikeUserRequirements(String fbId, ObjectId storyId) throws AlreadyLikedException {
//        boolean isStoryLiked = userDAO.isStoryLiked(fbId, storyId);
//
//        if (isStoryLiked) {
//            throw new AlreadyLikedException();
//        }
//    }
//
//    private void checkLikeStoryRequirements(String fbId, ObjectId storyId) throws StoryNotFoundException, UserLikingOwnStoryException, UnpublishedStoryLikedException {
//        PrivateStoryResponse privateStory = storyDAO.getPrivateStory(storyId);
//
//        if (privateStory == null) {
//            throw new StoryNotFoundException();
//        }
//
//        if (privateStory.getLeaderFbId().equals(fbId)) {
//            throw new UserLikingOwnStoryException();
//        }
//
//        if (privateStory.getFbFriends().contains(fbId)) {
//            throw new UserLikingOwnStoryException();
//        }
//        
//        if (privateStory.getIsPublished() == null || !privateStory.getIsPublished()){
//            throw new UnpublishedStoryLikedException();
//        }
//    }
//
//    public void publishStory(String fbId, ObjectId storyId) throws StoryNotFoundException, UserIsNotLeaderException, NoTitleForPublishingException {
//        WriteResult result = storyDAO.publishStory(fbId, storyId);
//        
//        if(result.getN() == 0){
//            PrivateStoryResponse privateStory = storyDAO.getPrivateStory(storyId);
//            
//            if(privateStory == null){
//                throw new StoryNotFoundException();
//            }
//            
//            if(!privateStory.getLeaderFbId().equals(fbId)){
//                throw new UserIsNotLeaderException();
//            }
//            
//            if(!StringUtils.hasText(privateStory.getTitle())){
//                throw new NoTitleForPublishingException();
//            }
//        }
//    }
//
//    public void changeStoryTitle(String fbId, ObjectId storyId, String title) throws StoryNotFoundException, UserIsNotLeaderException, AlreadyPublishedException {
//        WriteResult result = storyDAO.changeStoryTitle(fbId, storyId, title);
//        
//        if(result.getN() == 0){
//            PrivateStoryResponse privateStory = storyDAO.getPrivateStory(storyId);
//            
//            if(privateStory == null){
//                throw new StoryNotFoundException();
//            }
//            
//            if(!privateStory.getLeaderFbId().equals(fbId)){
//                throw new UserIsNotLeaderException();
//            }
//            
//            if(privateStory.getIsPublished() != null && privateStory.getIsPublished()){
//                throw new AlreadyPublishedException();
//            }
//        }
//    }
}
