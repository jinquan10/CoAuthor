package com.nwm.coauthor.service.model;

import org.springframework.data.mongodb.core.index.IndexDirection;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "entry")
public class EntryModel extends BaseModel {
	@Indexed(direction = IndexDirection.DESCENDING, background = true)
    private String entry;
    
    public EntryModel (){
    	
    }

	public String getEntry() {
		return entry;
	}

	public void setEntry(String entry) {
		this.entry = entry;
	}
}
