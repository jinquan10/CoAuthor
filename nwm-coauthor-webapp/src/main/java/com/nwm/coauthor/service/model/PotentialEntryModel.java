package com.nwm.coauthor.service.model;

import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;

public class PotentialEntryModel extends EntryModel {
    private Integer votes;
    
    @Indexed(unique = true)
    private List<String> votedAuthors;

    public Integer getVotes() {
        return votes;
    }

    public void setVotes(Integer votes) {
        this.votes = votes;
    }

    public List<String> getVotedAuthors() {
        return votedAuthors;
    }

    public void setVotedAuthors(List<String> votedAuthors) {
        this.votedAuthors = votedAuthors;
    }
}
