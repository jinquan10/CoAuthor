package com.nwm.coauthor.service.manager;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nwm.coauthor.exception.AddEntryException;
import com.nwm.coauthor.exception.AlreadyLikedException;
import com.nwm.coauthor.exception.StoryNotFoundException;
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
	
	public String createStory(StoryModel createStoryModel){
		storyDAO.createStory(createStoryModel);
		return createStoryModel.get_id().toString();
	}
	
	public List<PrivateStoryResponse> getStoriesByFbId(String fbId){
		return storyDAO.getStoriesByFbId(fbId);
	}
	
	public String addEntry(String fbId, AddEntryModel request) throws AddEntryException{
		storyDAO.addEntry(fbId, request);
		return request.getEntry().getEntryId();
	}
	
	public PrivateStoryResponse getPrivateStory(String fbId, ObjectId storyId) throws StoryNotFoundException{
		return storyDAO.getPrivateStory(fbId, storyId);
	}
	
	public void likeStory(String fbId, ObjectId storyId) throws AlreadyLikedException{
		// - see if user belongs to the story
		// - see if user already liked story
		// - like the story
		
		PrivateStoryResponse privateStory = storyDAO.getPrivateStory(fbId, storyId);
		boolean isStoryLiked = userDAO.isStoryLiked(fbId, storyId);
		
		if(isStoryLiked){
			throw new AlreadyLikedException();
		}
	}
}
