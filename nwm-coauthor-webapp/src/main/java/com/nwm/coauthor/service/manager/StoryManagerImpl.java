package com.nwm.coauthor.service.manager;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nwm.coauthor.exception.AddEntryException;
import com.nwm.coauthor.exception.StoryNotFoundException;
import com.nwm.coauthor.service.dao.StoryDAOImpl;
import com.nwm.coauthor.service.model.AddEntryModel;
import com.nwm.coauthor.service.model.StoryModel;
import com.nwm.coauthor.service.resource.response.PrivateStoryResponse;

@Component
public class StoryManagerImpl {
	@Autowired
	private StoryDAOImpl storyDAO;

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
	
	public void likeStory(String fbId, ObjectId storyId){
		storyDAO.likeStory(fbId, storyId);
	}
}
