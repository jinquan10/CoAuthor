package com.nwm.coauthor.service.manager;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nwm.coauthor.service.dao.StoryDAOImpl;
import com.nwm.coauthor.service.model.StoryModel;
import com.nwm.coauthor.service.resource.response.GetPrivateStoryResponse;

@Component
public class StoryManagerImpl {
	@Autowired
	private StoryDAOImpl storyDAO;
	
	public void createStory(StoryModel createStoryModel){
		storyDAO.createStory(createStoryModel);
	}
	
	public List<GetPrivateStoryResponse> getStoriesByFbId(String fbId){
		return storyDAO.getStoriesByFbId(fbId);
	}
}
