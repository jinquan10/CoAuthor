package com.nwm.coauthor.service.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.nwm.coauthor.service.resource.request.NewStory;

public class StoryModel extends BaseModel {
	private String storyId;
	private String leaderFbId;    	// - changeable by the leader only
    private String title;
    private Boolean isPublished;
    private List<String> fbFriends;
    private Long likes;    		// - changeable by public
    private String lastFriendWithEntry; // - changed when an entry is submitted
    private String lastEntry; 
    private Long storyLastUpdated;  // - changed when anything above is updated
    private Integer currEntryCharCount;
    private Integer rating;
    
    private List<String> entries;
    
	public static StoryModel createStoryModelFromRequest(String fbId, NewStory request){
    	StoryModel storyModel = new StoryModel();
    	
    	List<String> entries = new ArrayList<String>();
    	entries.add(request.getEntry());
    	
    	storyModel.setStoryId(new ObjectId().toString());
    	storyModel.setLeaderFbId(fbId);
    	storyModel.setTitle(request.getTitle());
    	storyModel.setIsPublished(false);
        storyModel.setLikes(0L);
        storyModel.setLastFriendWithEntry(fbId);
        storyModel.setLastEntry(request.getEntry());
        storyModel.setStoryLastUpdated(new Date().getTime());
        storyModel.setCurrEntryCharCount(request.getEntry().length());
        
        return storyModel;
    }
    
	public String getLeaderFbId() {
		return leaderFbId;
	}

	public void setLeaderFbId(String leaderFbId) {
		this.leaderFbId = leaderFbId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Boolean getIsPublished() {
		return isPublished;
	}

	public void setIsPublished(Boolean isPublished) {
		this.isPublished = isPublished;
	}

	public List<String> getFbFriends() {
		return fbFriends;
	}

	public void setFbFriends(List<String> fbFriends) {
		this.fbFriends = fbFriends;
	}

	public Long getLikes() {
		return likes;
	}

	public void setLikes(Long likes) {
		this.likes = likes;
	}

	public String getLastFriendWithEntry() {
		return lastFriendWithEntry;
	}

	public void setLastFriendWithEntry(String lastFriendWithEntry) {
		this.lastFriendWithEntry = lastFriendWithEntry;
	}

	public Long getStoryLastUpdated() {
		return storyLastUpdated;
	}

	public void setStoryLastUpdated(Long storyLastUpdated) {
		this.storyLastUpdated = storyLastUpdated;
	}

	public String getLastEntry() {
		return lastEntry;
	}

	public void setLastEntry(String lastEntry) {
		this.lastEntry = lastEntry;
	}

	public String getStoryId() {
		return storyId;
	}

	public void setStoryId(String storyId) {
		this.storyId = storyId;
	}

    public Integer getCurrEntryCharCount() {
        return currEntryCharCount;
    }

    public void setCurrEntryCharCount(Integer currEntryCharCount) {
        this.currEntryCharCount = currEntryCharCount;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

	public List<String> getEntries() {
		return entries;
	}

	public void setEntries(List<String> entries) {
		this.entries = entries;
	}
}
