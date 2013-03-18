package com.nwm.coauthor.service.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nwm.coauthor.service.dao.CommentDAOImpl;
import com.nwm.coauthor.service.dao.EntryDAOImpl;
import com.nwm.coauthor.service.dao.StoryDAOImpl;
import com.nwm.coauthor.service.dao.UserDAOImpl;
import com.nwm.coauthor.service.model.EntryModel;
import com.nwm.coauthor.service.model.StoryModel;
import com.nwm.coauthor.service.resource.request.NewStoryRequest;
import com.nwm.coauthor.service.resource.response.NewStoryResponse;

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
    
    public NewStoryResponse createStory(String fbId, NewStoryRequest request) {
    	StoryModel newStoryModel = StoryModel.createStoryModelFromRequest(fbId, request);
    	EntryModel newEntryModel = EntryModel.newEntryModel(newStoryModel.get_id(), fbId, request.getEntry(), newStoryModel.getEntryOrdinal()); 
    	
        storyDAO.createStory(newStoryModel);
        entryDAO.addEntry(newEntryModel);
        
        return NewStoryResponse.newStoryResponse(newStoryModel.get_id().toString(), newStoryModel.getStoryLastUpdated().getTime());
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
