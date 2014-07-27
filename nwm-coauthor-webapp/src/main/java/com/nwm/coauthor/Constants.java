package com.nwm.coauthor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constants {
	public static final String ANONYMOUS_USER = "Anonymous";
	public static final Integer TOP_VIEW_STORIES_COUNT = 10;
	public static final String STORY_COLLECTION = "story";

	public static final String TOP_VIEW_STORIES_PATH = "/stories/top-view-stories";
	public static final String CREATE_STORY_PATH = "/stories";
	public static final String GET_STORY_PATH = "/stories/{id}";
	
	@Value("${service.url}")
	public String serviceUrl;
}
