package com.nwm.coauthor.service.resource.response;

import java.util.List;

import com.nwm.coauthor.service.resource.BaseResource;

public class StoryResponse extends BaseResource{
	private String storyId;
	private String leaderFbId;    	// - changeable by the leader only
    private String title;
    private Integer numCharacters;
    private Boolean isPublished;
    private List<String> fbFriends;
    private Long likes;    		// - changeable by public
    private String lastFriendWithEntry; // - changed when an entry is submitted
    private String lastEntry; 
    private Long storyLastUpdated;  // - changed when anything above is updated
    private Integer currEntryCount;    	// - changed when entry is added
    
	public String getStoryId() {
		return storyId;
	}
	public void setStoryId(String storyId) {
		this.storyId = storyId;
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
	public Integer getNumCharacters() {
		return numCharacters;
	}
	public void setNumCharacters(Integer numCharacters) {
		this.numCharacters = numCharacters;
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
	public String getLastEntry() {
		return lastEntry;
	}
	public void setLastEntry(String lastEntry) {
		this.lastEntry = lastEntry;
	}
	public Long getStoryLastUpdated() {
		return storyLastUpdated;
	}
	public void setStoryLastUpdated(Long storyLastUpdated) {
		this.storyLastUpdated = storyLastUpdated;
	}
	public Integer getCurrEntryCount() {
		return currEntryCount;
	}
	public void setCurrEntryCount(Integer currEntryCount) {
		this.currEntryCount = currEntryCount;
	}
}
