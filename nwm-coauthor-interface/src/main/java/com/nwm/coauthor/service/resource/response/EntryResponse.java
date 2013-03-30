package com.nwm.coauthor.service.resource.response;

import com.nwm.coauthor.service.resource.BaseResource;

public class EntryResponse extends BaseResource{
    private String fbId;
    private String entry;
    private Integer currCharCount;
    
	public String getFbId() {
		return fbId;
	}
	public void setFbId(String fbId) {
		this.fbId = fbId;
	}
	public String getEntry() {
		return entry;
	}
	public void setEntry(String entry) {
		this.entry = entry;
	}
    public Integer getCurrCharCount() {
        return currCharCount;
    }
    public void setCurrCharCount(Integer currCharCount) {
        this.currCharCount = currCharCount;
    }
}
