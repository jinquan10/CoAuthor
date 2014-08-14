package com.nwm.coauthor;


public class Constants {
	public static final String ANONYMOUS_USER = "Anonymous";
	public static final Integer TOP_VIEW_STORIES_COUNT = 10;
	public static final String STORY_COLLECTION = "story";

	public static final String TOP_VIEW_STORIES_PATH = "/stories/top-view-stories";
	public static final String CREATE_STORY_PATH = "/stories";
	public static final String GET_STORY_PATH = "/stories/{id}";
	public static final String INCREMENT_STORY_VIEWS_PATH = "/stories/{id}/increment-views";
	public static final String PICK_ENTRY_PATH = "/stories/{storyId}/pick-entry";
	public static final String INCREMENT_PRAISE = "/stories/{storyId}/increment-praise/{praise}";
	
	public static final String ENTRY_REQUEST_PATH = "/stories/{storyId}/entries";
	public static final String ENTRY_VOTE_PATH = "/stories/{storyId}/entries/{entryId}/vote";
//    public static final Long NEXT_ENTRY_DURATION = 3600000L; // 1 hour in millis
	public static final Long NEXT_ENTRY_DURATION = 10000L; // 1 hour in millis
    
	public static final Long EXPIRATION_PADDING = 5000L; // 5 seconds
}
