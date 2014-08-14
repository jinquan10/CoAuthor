package com.nwm.coauthor.service.resource.response;

import com.nwm.coauthor.Praises;
import com.nwm.coauthor.service.resource.BaseResource;

public class StoryInListResponse extends BaseResource{
	private String id;
    private String title;

    private Integer views;
    private Integer charCount;
    private Integer stars;
    private Integer entriesCount;
    
    private EntryResponse firstEntry;
    private EntryResponse lastEntry;
    
    private Praises praises = new Praises();
    
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
    public Integer getEntriesCount() {
        return entriesCount;
    }
    public void setEntriesCount(Integer entriesCount) {
        this.entriesCount = entriesCount;
    }
    public Praises getPraises() {
        return praises;
    }
    public void setPraises(Praises praises) {
        this.praises = praises;
    }
}
