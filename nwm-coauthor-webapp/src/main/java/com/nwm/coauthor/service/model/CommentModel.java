package com.nwm.coauthor.service.model;

import org.bson.types.ObjectId;

public class CommentModel extends BaseModel{
	private ObjectId storyId;
	private String fbId;
	private String comment;
	private long ordinal;
	
	public ObjectId getStoryId() {
		return storyId;
	}
	public void setStoryId(ObjectId storyId) {
		this.storyId = storyId;
	}
	public String getFbId() {
		return fbId;
	}
	public void setFbId(String fbId) {
		this.fbId = fbId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public long getOrdinal() {
		return ordinal;
	}
	public void setOrdinal(long ordinal) {
		this.ordinal = ordinal;
	}	
}
