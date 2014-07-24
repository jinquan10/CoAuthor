package com.nwm.coauthor.service.model;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;

import com.nwm.coauthor.service.resource.request.NewStory;

public class StoryModel extends BaseModel {
	@Id
	private String id;
    private String title;

    private Integer views;
    private Integer charCount;
    private Integer stars;
    
    private List<EntryModel> entries;

    public static StoryModel fromNewStory(Long timeZoneOffsetMinutes, String coToken, String createdByDisplayName, NewStory request){
    	Long now = DateTime.now().getMillis();

    	EntryModel entry = entryFromNewStory(timeZoneOffsetMinutes, coToken, createdByDisplayName, request.getEntry(), now);
    	
    	List<EntryModel> theEntries = new ArrayList<EntryModel>();
    	theEntries.add(entry);

    	StoryModel storyModel = new StoryModel();
    	
    	storyModel.setCoToken(coToken);
    	storyModel.setCreatedByDisplayName(createdByDisplayName);
    	storyModel.setCreatedOn(now);
    	storyModel.setLastUpdated(now);
    	storyModel.setTimeZoneOffsetMinutes(timeZoneOffsetMinutes);
    	
    	storyModel.setTitle(request.getTitle());
    	storyModel.setViews(1);
    	storyModel.setCharCount(request.getEntry().length());
    	storyModel.setEntries(theEntries);
    	
        return storyModel;
    }

    private static EntryModel entryFromNewStory(Long timeZoneOffsetMinutes, String coToken, String createdByDisplayName, String entry, Long now) {
    	EntryModel entryModel = new EntryModel();

    	entryModel.setCharCount(entry.length());
    	entryModel.setCoToken(coToken);
    	entryModel.setCreatedByDisplayName(createdByDisplayName);
    	entryModel.setCreatedOn(now);
    	entryModel.setEntry(entry);
    	entryModel.setLastUpdated(now);
    	entryModel.setTimeZoneOffsetMinutes(timeZoneOffsetMinutes);
    	
    	return entryModel;
    }
    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public List<EntryModel> getEntries() {
		return entries;
	}

	public void setEntries(List<EntryModel> entries) {
		this.entries = entries;
	}

	public Integer getViews() {
		return views;
	}

	public void setViews(Integer views) {
		this.views = views;
	}

	public Integer getCharCount() {
		return charCount;
	}

	public void setCharCount(Integer charCount) {
		this.charCount = charCount;
	}

	public Integer getStars() {
		return stars;
	}

	public void setStars(Integer stars) {
		this.stars = stars;
	}
}
