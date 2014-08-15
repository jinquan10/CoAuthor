package com.nwm.coauthor.service.model;

import java.util.Set;

import org.springframework.data.mongodb.core.index.Indexed;

public class PotentialEntryModel extends EntryModel {
    private Integer votes;
    
    @Indexed(background = true)
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
