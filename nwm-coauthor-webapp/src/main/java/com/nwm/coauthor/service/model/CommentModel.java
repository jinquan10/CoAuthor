package com.nwm.coauthor.service.model;


public class CommentModel extends BaseModel{
	private String storyId;
	private String fbId;
	private String comment;
	
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
	public String getStoryId() {
		return storyId;
	}
	public void setStoryId(String storyId) {
		this.storyId = storyId;
	}
}
