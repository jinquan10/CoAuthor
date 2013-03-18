package com.nwm.coauthor.service.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import com.nwm.coauthor.service.model.EntryModel;

@Component
public class EntryDAOImpl {
	@Autowired
	@Qualifier("mongoTemplate")
	private MongoTemplate mongoTemplate;

	public void addEntry(EntryModel newEntryModel) {
		mongoTemplate.insert(newEntryModel); 
	}
}
