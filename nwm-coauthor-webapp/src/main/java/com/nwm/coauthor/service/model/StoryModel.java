package com.nwm.coauthor.service.model;

import java.util.List;

import org.bson.types.ObjectId;

public class StoryModel extends BaseModel {
    private ObjectId _id;
    private String leaderFbId;
    private String title;
    private Integer numCharacters;
    private List<StoryEntryModel> entries;
    private List<String> fbFriends;
    private String lastFriendEntry;
    private Boolean isPublished;
    private Integer version;
    private Integer likes;

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

    public List<StoryEntryModel> getEntries() {
        return entries;
    }

    public void setEntries(List<StoryEntryModel> entries) {
        this.entries = entries;
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

    public String getLeaderFbId() {
        return leaderFbId;
    }

    public void setLeaderFbId(String leaderFbId) {
        this.leaderFbId = leaderFbId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }
}
