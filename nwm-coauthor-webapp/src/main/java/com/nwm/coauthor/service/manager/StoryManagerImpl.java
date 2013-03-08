package com.nwm.coauthor.service.manager;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.mongodb.WriteResult;
import com.nwm.coauthor.exception.AddEntryException;
import com.nwm.coauthor.exception.AddEntryVersionException;
import com.nwm.coauthor.exception.AlreadyLikedException;
import com.nwm.coauthor.exception.NoTitleForPublishingException;
import com.nwm.coauthor.exception.StoryNotFoundException;
import com.nwm.coauthor.exception.UnauthorizedException;
import com.nwm.coauthor.exception.UserIsNotLeaderException;
import com.nwm.coauthor.exception.UserLikingOwnStoryException;
import com.nwm.coauthor.service.dao.StoryDAOImpl;
import com.nwm.coauthor.service.dao.UserDAOImpl;
import com.nwm.coauthor.service.model.AddEntryModel;
import com.nwm.coauthor.service.model.StoryModel;
import com.nwm.coauthor.service.resource.response.PrivateStoryResponse;

@Component
public class StoryManagerImpl {
    @Autowired
    private StoryDAOImpl storyDAO;
    @Autowired
    private UserDAOImpl userDAO;

    public String createStory(StoryModel createStoryModel) {
        storyDAO.createStory(createStoryModel);
        return createStoryModel.get_id().toString();
    }

    public List<PrivateStoryResponse> getStoriesByFbId(String fbId) throws StoryNotFoundException {
        List<PrivateStoryResponse> stories = storyDAO.getStoriesByFbId(fbId);

        if (stories == null || stories.size() == 0) {
            throw new StoryNotFoundException();
        }

        return stories;
    }

    public String addEntry(String fbId, AddEntryModel request) throws AddEntryException, StoryNotFoundException, AddEntryVersionException {
        WriteResult result = storyDAO.addEntry(fbId, request);

        if (result.getN() == 0) {
            PrivateStoryResponse privateStory = storyDAO.getPrivateStory(request.getStoryId());

            if (request.getVersion() != privateStory.getVersion()) {
                throw new AddEntryVersionException();
            }

            throw new AddEntryException();
        }

        return request.getEntry().getEntryId();
    }

    public PrivateStoryResponse getStoryForEdit(String fbId, ObjectId storyId) throws StoryNotFoundException, UnauthorizedException {
        PrivateStoryResponse privateStory = storyDAO.getPrivateStory(storyId);

        if (privateStory == null) {
            throw new StoryNotFoundException();
        }

        if (!privateStory.getLeaderFbId().equals(fbId)) {
            if (!privateStory.getFbFriends().contains(fbId)) {
                throw new UnauthorizedException();
            }
        }

        return privateStory;
    }

    public void likeStory(String fbId, ObjectId storyId) throws AlreadyLikedException, StoryNotFoundException, UserLikingOwnStoryException {
        checkIsUserLikingOwnStory(fbId, storyId);
        checkStoryAlreadyLiked(fbId, storyId);
        persistLikeStory(fbId, storyId);
    }

    private void persistLikeStory(String fbId, ObjectId storyId) {
        userDAO.likeStory(fbId, storyId);
        storyDAO.likeStory(storyId);
    }

    private void checkStoryAlreadyLiked(String fbId, ObjectId storyId) throws AlreadyLikedException {
        boolean isStoryLiked = userDAO.isStoryLiked(fbId, storyId);

        if (isStoryLiked) {
            throw new AlreadyLikedException();
        }
    }

    private void checkIsUserLikingOwnStory(String fbId, ObjectId storyId) throws StoryNotFoundException, UserLikingOwnStoryException {
        PrivateStoryResponse privateStory = storyDAO.getPrivateStory(storyId);

        if (privateStory == null) {
            throw new StoryNotFoundException();
        }

        if (privateStory.getLeaderFbId().equals(fbId)) {
            throw new UserLikingOwnStoryException();
        }

        if (privateStory.getFbFriends().contains(fbId)) {
            throw new UserLikingOwnStoryException();
        }
    }

    public void publishStory(String fbId, ObjectId storyId) throws StoryNotFoundException, UserIsNotLeaderException, NoTitleForPublishingException {
        WriteResult result = storyDAO.publishStory(fbId, storyId);
        
        if(result.getN() == 0){
            PrivateStoryResponse privateStory = storyDAO.getPrivateStory(storyId);
            
            if(privateStory == null){
                throw new StoryNotFoundException();
            }
            
            if(!privateStory.getLeaderFbId().equals(fbId)){
                throw new UserIsNotLeaderException();
            }
            
            if(privateStory.getTitle() == null){
                throw new NoTitleForPublishingException();
            }
        }
    }
}
