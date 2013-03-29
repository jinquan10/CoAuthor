package com.nwm.coauthor.service.resource.response;

import java.util.List;

import com.nwm.coauthor.service.resource.BaseResource;

public class EntriesResponse extends BaseResource{
	private List<EntryResponse> entries;
	private Integer newBeginIndex;
	
	public List<EntryResponse> getEntries() {
		return entries;
	}

	public void setEntries(List<EntryResponse> entries) {
		this.entries = entries;
	}

    public Integer getNewBeginIndex() {
        return newBeginIndex;
    }

    public void setNewBeginIndex(Integer newBeginIndex) {
        this.newBeginIndex = newBeginIndex;
    }
}
