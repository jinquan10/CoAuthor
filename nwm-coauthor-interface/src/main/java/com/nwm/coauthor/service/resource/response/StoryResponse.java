package com.nwm.coauthor.service.resource.response;

import java.util.List;

public class StoryResponse extends StoryInListResponse {
	private List<EntryResponse> entries;

	public List<EntryResponse> getEntries() {
		return entries;
	}

	public void setEntries(List<EntryResponse> entries) {
		this.entries = entries;
	}
}
