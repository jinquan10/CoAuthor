package com.nwm.coauthor.service.resource.response;

import com.nwm.coauthor.service.resource.BaseResource;

public class EntryResponse extends BaseResource {
    private String entry;

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}
}
