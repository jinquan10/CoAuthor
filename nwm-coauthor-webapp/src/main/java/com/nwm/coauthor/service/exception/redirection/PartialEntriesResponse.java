package com.nwm.coauthor.service.exception.redirection;

import com.nwm.coauthor.service.resource.response.EntriesResponse;

public class PartialEntriesResponse extends Exception {
    private EntriesResponse entries;
    
    public PartialEntriesResponse(EntriesResponse entries){
        this.entries = entries;
    }

    public EntriesResponse getEntries() {
        return entries;
    }

    public void setEntries(EntriesResponse entries) {
        this.entries = entries;
    }
}
