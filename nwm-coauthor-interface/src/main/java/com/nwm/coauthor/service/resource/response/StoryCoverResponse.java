package com.nwm.coauthor.service.resource.response;

import com.nwm.coauthor.service.resource.BaseResource;

public class StoryCoverResponse extends BaseResource{
	private String storyId;
	private String leaderFbId;
	private String title;
	private Boolean isPublished;
	private Long likes;
	private String lastFriendWithEntry;
	private String lastEntry;
	private Long storyLastUpdated;
	
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
	public Boolean getIsPublished() {
		return isPublished;
	}
	public void setIsPublished(Boolean isPublished) {
		this.isPublished = isPublished;
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
}
