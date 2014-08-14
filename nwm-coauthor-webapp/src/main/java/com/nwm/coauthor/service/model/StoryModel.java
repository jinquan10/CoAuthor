package com.nwm.coauthor.service.model;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.nwm.coauthor.Constants;
import com.nwm.coauthor.Praises;
import com.nwm.coauthor.service.resource.request.NewStoryRequest;

@Document(collection = Constants.STORY_COLLECTION)
public class StoryModel extends BaseModel {
	@Id
	private String id;
    private String title;

    @Indexed(direction = IndexDirection.DESCENDING, background = true)
    private Integer views;
    
    @Indexed(background = true)
    private String genre;

    private Integer charCount;
    private Integer stars;
    private Integer entriesCount;
    
    private List<EntryModel> entries;
    
    private EntryModel firstEntry;
    private EntryModel lastEntry;

    private Long nextEntryAvailableAt;
    private List<PotentialEntryModel> potentialEntries;
    
    private Praises praises;
    
    public static StoryModel fromNewStory(Long timeZoneOffsetMinutes, String coToken, String createdByDisplayName, NewStoryRequest request){
    	Long now = DateTime.now().getMillis();

    	EntryModel entry = entryFromNewStory(timeZoneOffsetMinutes, coToken, createdByDisplayName, request.getEntry(), now);
    	
    	List<EntryModel> theEntries = new ArrayList<EntryModel>();
    	theEntries.add(entry);

    	StoryModel storyModel = new StoryModel();
    	
    	storyModel.setCreatedById(coToken);
    	storyModel.setCreatedByDisplayName(createdByDisplayName);
    	storyModel.setCreatedOn(now);
    	storyModel.setLastUpdated(now);
    	storyModel.setTimeZoneOffsetMinutes(timeZoneOffsetMinutes);
    	storyModel.setEntriesCount(1);
    	storyModel.setTitle(request.getTitle());
    	storyModel.setViews(1);
    	storyModel.setCharCount(request.getEntry().length());
    	storyModel.setEntries(theEntries);
    	storyModel.setFirstEntry(entry);
    	storyModel.setPraises(new Praises());
    	
        return storyModel;
    }

    private static EntryModel entryFromNewStory(Long timeZoneOffsetMinutes, String coToken, String createdByDisplayName, String entry, Long now) {
    	EntryModel entryModel = new EntryModel();

    	entryModel.setCreatedById(coToken);
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

	public EntryModel getFirstEntry() {
		return firstEntry;
	}

	public void setFirstEntry(EntryModel firstEntry) {
		this.firstEntry = firstEntry;
	}

	public EntryModel getLastEntry() {
		return lastEntry;
	}

	public void setLastEntry(EntryModel lastEntry) {
		this.lastEntry = lastEntry;
	}

    public Long getNextEntryAvailableAt() {
        return nextEntryAvailableAt;
    }

    public void setNextEntryAvailableAt(Long nextEntryAvailableAt) {
        this.nextEntryAvailableAt = nextEntryAvailableAt;
    }
    public Integer getEntriesCount() {
        return entriesCount;
    }

    public void setEntriesCount(Integer entriesCount) {
        this.entriesCount = entriesCount;
    }

    public List<PotentialEntryModel> getPotentialEntries() {
        return potentialEntries;
    }

    public void setPotentialEntries(List<PotentialEntryModel> potentialEntries) {
        this.potentialEntries = potentialEntries;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Praises getPraises() {
        return praises;
    }

    public void setPraises(Praises praises) {
        this.praises = praises;
    }
}
