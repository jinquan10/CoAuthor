package com.nwm.coauthor;

import java.util.Set;

import org.springframework.data.mongodb.core.index.Indexed;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class Praise {
    private int count;
    
    @JsonIgnore
    @Indexed(background = true)
    private Set<String> authors;
    
    public Set<String> getAuthors() {
        return authors;
    }
    public void setAuthors(Set<String> authors) {
        this.authors = authors;
    }
    public int getCount() {
        return count;
    }
    public void setCount(int count) {
        this.count = count;
    }
}
