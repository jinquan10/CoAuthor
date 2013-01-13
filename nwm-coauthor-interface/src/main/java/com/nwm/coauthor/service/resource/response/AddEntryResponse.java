package com.nwm.coauthor.service.resource.response;

import com.nwm.coauthor.service.resource.BaseResource;

public class AddEntryResponse extends BaseResource{
	private String entryId;

	public AddEntryResponse() {
	}
	
	public AddEntryResponse(String entryId){
		this.entryId = entryId;
	}
	
	public String getEntryId() {
		return entryId;
	}

	public void setEntryId(String entryId) {
		this.entryId = entryId;
	}
}
