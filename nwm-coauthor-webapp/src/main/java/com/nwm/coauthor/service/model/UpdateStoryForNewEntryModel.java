package com.nwm.coauthor.service.model;

import java.util.Date;

import com.nwm.coauthor.service.resource.response.StoryResponse;

public class UpdateStoryForNewEntryModel extends BaseModel{
    private Long storyLastUpdated;
    private String storyId;
    private String lastEntry;
    private String lastFriendWithEntry;
    private Integer currEntryCharCount;
    
    public StoryResponse mergeWithStoryResponse(StoryResponse response){
        return response;
    }
    
    public static UpdateStoryForNewEntryModel init(String storyId, String lastEntry, String lastFriendWithEntry, Integer currEntryCharCount){
        UpdateStoryForNewEntryModel model = new UpdateStoryForNewEntryModel();
        
        model.setStoryId(storyId);
        model.setLastEntry(lastEntry);
        model.setLastFriendWithEntry(lastFriendWithEntry);
        model.setCurrEntryCharCount(currEntryCharCount);
        model.setStoryLastUpdated(new Date().getTime());
        
        return model;
    }
    
    public Long getStoryLastUpdated() {
        return storyLastUpdated;
    }
    public void setStoryLastUpdated(Long storyLastUpdated) {
        this.storyLastUpdated = storyLastUpdated;
    }
    public String getStoryId() {
        return storyId;
    }
    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }
    public String getLastEntry() {
        return lastEntry;
    }
    public void setLastEntry(String lastEntry) {
        this.lastEntry = lastEntry;
    }
    public String getLastFriendWithEntry() {
        return lastFriendWithEntry;
    }
    public void setLastFriendWithEntry(String lastFriendWithEntry) {
        this.lastFriendWithEntry = lastFriendWithEntry;
    }
    public Integer getCurrEntryCharCount() {
        return currEntryCharCount;
    }
    public void setCurrEntryCharCount(Integer currEntryCharCount) {
        this.currEntryCharCount = currEntryCharCount;
    }
}
