package com.nwm.coauthor.service.resource.response;

import com.nwm.coauthor.service.resource.BaseResource;

public class StoryResponse extends BaseResource{
	private String id;
    private String title;

    private Integer views;
    private Integer charCount;
    private Integer stars;
    
    private EntryResponse firstEntry;
    private EntryResponse lastEntry;
    
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
	public EntryResponse getFirstEntry() {
		return firstEntry;
	}
	public void setFirstEntry(EntryResponse firstEntry) {
		this.firstEntry = firstEntry;
	}
	public EntryResponse getLastEntry() {
		return lastEntry;
	}
	public void setLastEntry(EntryResponse lastEntry) {
		this.lastEntry = lastEntry;
	}
}
