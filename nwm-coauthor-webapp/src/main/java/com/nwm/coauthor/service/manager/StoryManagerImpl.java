package com.nwm.coauthor.service.manager;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.List;

import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.nwm.coauthor.exception.AddEntryException;
import com.nwm.coauthor.exception.AddEntryVersionException;
import com.nwm.coauthor.exception.AlreadyLikedException;
import com.nwm.coauthor.exception.StoryNotFoundException;
import com.nwm.coauthor.exception.UnauthorizedException;
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

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public String createStory(StoryModel createStoryModel){
		storyDAO.createStory(createStoryModel);
		return createStoryModel.get_id().toString();
	}
	
	public List<PrivateStoryResponse> getStoriesByFbId(String fbId) throws StoryNotFoundException{
	    List<PrivateStoryResponse> stories = storyDAO.getStoriesByFbId(fbId);
	    
	    if(stories == null || stories.size() == 0){
	        throw new StoryNotFoundException();
	    }
	    
		return stories;
	}
	
	public String addEntry(String fbId, AddEntryModel request) throws AddEntryException, StoryNotFoundException, AddEntryVersionException{
		StoryModel model = storyDAO.addEntry(fbId, request);
		
		if(model == null){
		    PrivateStoryResponse privateStory = storyDAO.getPrivateStory(request.getStoryId());		    
		    
		    if(request.getVersion() != privateStory.getVersion()){
		    	throw new AddEntryVersionException();
		    }
		    
			logger.error("addEntry(): Add entry failed.");
//			logger.error("addEntry(): Add entry failed.\nHere is why: [_id | {}] [fbFriends | {}] [lastFriendEntry | {}] [numCharacters | {}] [version | {}]", request.getStoryId(), fbId, fbId, request.getEntry().getEntry().length(), request.getVersion());
			
			throw new AddEntryException();
		}
		
		return request.getEntry().getEntryId();
	}
	
	public PrivateStoryResponse getStoryForEdit(String fbId, ObjectId storyId) throws StoryNotFoundException, UnauthorizedException{
	    PrivateStoryResponse privateStory = storyDAO.getPrivateStory(storyId);
	    
	    if(!privateStory.getLeaderFbId().equals(fbId)){
	        if(!privateStory.getFbFriends().contains(fbId)){
	            throw new UnauthorizedException();
	        }
	    }
	    
	    return privateStory;
	}
	
	public void likeStory(String fbId, ObjectId storyId) throws AlreadyLikedException, StoryNotFoundException{
		checkStoryExists(storyId);
		checkStoryAlreadyLiked(fbId, storyId);
		persistLikeStory(fbId, storyId);
	}

	private void persistLikeStory(String fbId, ObjectId storyId) {
		userDAO.likeStory(fbId, storyId);
		storyDAO.likeStory(storyId);
	}

	private void checkStoryAlreadyLiked(String fbId, ObjectId storyId)
			throws AlreadyLikedException {
		boolean isStoryLiked = userDAO.isStoryLiked(fbId, storyId);
		
		if(isStoryLiked){
			throw new AlreadyLikedException();
		}
	}

	private void checkStoryExists(ObjectId storyId)
			throws StoryNotFoundException {
		PrivateStoryResponse privateStory = storyDAO.getPrivateStory(storyId);
		
		if(privateStory == null){
			throw new StoryNotFoundException();
		}
	}
}
