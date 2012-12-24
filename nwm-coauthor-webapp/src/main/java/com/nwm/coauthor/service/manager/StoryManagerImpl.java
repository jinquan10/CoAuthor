package com.nwm.coauthor.service.manager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nwm.coauthor.service.dao.StoryDAOImpl;
import com.nwm.coauthor.service.model.CreateStoryModel;

@Component
public class StoryManagerImpl {
	@Autowired
	private StoryDAOImpl storyDAO;
	
	public void createStory(CreateStoryModel createStoryModel){
		storyDAO.createStory(createStoryModel);
	}
}
