package com.nwm.coauthor.service.resource.request;

import com.nwm.coauthor.service.resource.BaseResource;

public class CommentRequest extends BaseResource{
	private String storyId;
	private String comment;
	private Long ordinal;
	
	public String getStoryId() {
		return storyId;
	}
	public void setStoryId(String storyId) {
		this.storyId = storyId;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public Long getOrdinal() {
		return ordinal;
	}
	public void setOrdinal(Long ordinal) {
		this.ordinal = ordinal;
	}
}
