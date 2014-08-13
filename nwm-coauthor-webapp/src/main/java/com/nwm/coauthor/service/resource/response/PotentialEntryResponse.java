package com.nwm.coauthor.service.resource.response;

public class PotentialEntryResponse extends EntryResponse {
	private Integer votes;
	private String id;
	
	public Integer getVotes() {
		return votes;
	}

	public void setVotes(Integer votes) {
		this.votes = votes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
