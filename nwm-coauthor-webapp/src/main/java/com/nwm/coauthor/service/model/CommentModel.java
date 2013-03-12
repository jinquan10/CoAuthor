package com.nwm.coauthor.service.model;

public class CommentModel extends BaseModel{
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
}
