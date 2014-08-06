package com.nwm.coauthor.service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

public class EntryModel extends BaseModel {
    @Id
    @Indexed(background = true)
    private String id;
    private String entry;
    
    public EntryModel (){
    	
    }

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
