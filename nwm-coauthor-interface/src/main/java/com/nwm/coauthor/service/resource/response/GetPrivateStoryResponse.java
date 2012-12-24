package com.nwm.coauthor.service.resource.response;

import java.util.List;

import com.nwm.coauthor.service.resource.BaseResource;
import com.nwm.coauthor.service.resource.StoryEntryResource;

public class GetPrivateStoryResponse extends BaseResource {
	private String leaderFbId;
	private String title;
	private Integer numCharacters;
	private List<StoryEntryResource> entries;
	private List<String> fbFriends;
	private String lastFriendEntry;
	private Boolean isPublished;
	
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
	public List<String> getFbFriends() {
		return fbFriends;
	}
	public void setFbFriends(List<String> fbFriends) {
		this.fbFriends = fbFriends;
	}
	public String getLastFriendEntry() {
		return lastFriendEntry;
	}
	public void setLastFriendEntry(String lastFriendEntry) {
		this.lastFriendEntry = lastFriendEntry;
	}
	public Boolean getIsPublished() {
		return isPublished;
	}
	public void setIsPublished(Boolean isPublished) {
		this.isPublished = isPublished;
	}
	public List<StoryEntryResource> getEntries() {
		return entries;
	}
	public void setEntries(List<StoryEntryResource> entries) {
		this.entries = entries;
	}
	public String getLeaderFbId() {
		return leaderFbId;
	}
	public void setLeaderFbId(String leaderFbId) {
		this.leaderFbId = leaderFbId;
	}
}
