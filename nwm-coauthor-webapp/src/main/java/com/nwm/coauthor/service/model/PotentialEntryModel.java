package com.nwm.coauthor.service.model;

import java.util.Set;

public class PotentialEntryModel extends EntryModel {
    private Integer votes;
    
    private Set<String> votedAuthors;

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public Set<String> getVotedAuthors() {
        return votedAuthors;
    }

    public void setVotedAuthors(Set<String> votedAuthors) {
        this.votedAuthors = votedAuthors;
    }
}
