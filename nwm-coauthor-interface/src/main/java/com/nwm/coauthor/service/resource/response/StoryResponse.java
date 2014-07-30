package com.nwm.coauthor.service.resource.response;

import java.util.List;

public class StoryResponse extends StoryInListResponse {
	private Long nextEntryAvailableAt;
	private List<EntryResponse> entries;
	private List<PotentialEntryResponse> potentialEntries;

	public List<EntryResponse> getEntries() {
		return entries;
	}

	public void setEntries(List<EntryResponse> entries) {
		this.entries = entries;
	}
	
	public Long getNextEntryAvailableAt() {
		return nextEntryAvailableAt;
	}

	public void setNextEntryAvailableAt(Long nextEntryAvailableAt) {
		this.nextEntryAvailableAt = nextEntryAvailableAt;
	}

	public List<PotentialEntryResponse> getPotentialEntries() {
		return potentialEntries;
	}

	public void setPotentialEntries(List<PotentialEntryResponse> potentialEntries) {
		this.potentialEntries = potentialEntries;
	}
}
