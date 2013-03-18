package com.nwm.coauthor.service.model;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

import com.nwm.coauthor.service.resource.request.NewStoryRequest;

public class StoryModel extends BaseModel {
	private ObjectId _id;
    private String leaderFbId;    	// - changeable by the leader only
    private String title;
    private Integer numCharacters;
    private Boolean isPublished;
    private List<String> fbFriends;
    private Integer likes;    		// - changeable by public
    private String lastFriendWithEntry; // - changed when an entry is submitted
    private Date storyLastUpdated;  // - changed when anything above is updated
    private Long entryOrdinal;    	// - changed when entry is added
    private Long commentOrdinal;    // - changed when comment is added

	public static StoryModel createStoryModelFromRequest(String fbId, NewStoryRequest request){
    	StoryModel storyModel = new StoryModel();
    	
    	storyModel.set_id(new ObjectId());
    	storyModel.setLeaderFbId(fbId);
    	storyModel.setTitle(request.getTitle());
    	storyModel.setNumCharacters(request.getNumCharacters());
    	storyModel.setIsPublished(false);
        storyModel.setFbFriends(request.getFbFriends());
        storyModel.setLikes(0);
        storyModel.setLastFriendWithEntry(fbId);
        storyModel.setStoryLastUpdated(new Date());
        storyModel.setEntryOrdinal(0L);
        storyModel.setCommentOrdinal(0L);
        
        return storyModel;
    }
    
    public ObjectId get_id() {
		return _id;
	}

	public void set_id(ObjectId _id) {
		this._id = _id;
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

	public Integer getLikes() {
		return likes;
	}

	public void setLikes(Integer likes) {
		this.likes = likes;
	}

	public String getLastFriendWithEntry() {
		return lastFriendWithEntry;
	}

	public void setLastFriendWithEntry(String lastFriendWithEntry) {
		this.lastFriendWithEntry = lastFriendWithEntry;
	}

	public Date getStoryLastUpdated() {
		return storyLastUpdated;
	}

	public void setStoryLastUpdated(Date storyLastUpdated) {
		this.storyLastUpdated = storyLastUpdated;
	}

	public Long getEntryOrdinal() {
		return entryOrdinal;
	}

	public void setEntryOrdinal(Long entryOrdinal) {
		this.entryOrdinal = entryOrdinal;
	}

	public Long getCommentOrdinal() {
		return commentOrdinal;
	}

	public void setCommentOrdinal(Long commentOrdinal) {
		this.commentOrdinal = commentOrdinal;
	}
}
