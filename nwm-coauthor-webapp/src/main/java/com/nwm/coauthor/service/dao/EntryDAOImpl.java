package com.nwm.coauthor.service.dao;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

import com.nwm.coauthor.service.model.EntryModel;
import com.nwm.coauthor.service.resource.response.EntryResponse;

@Component
public class EntryDAOImpl {
	@Autowired
	@Qualifier("mongoTemplate")
	private MongoTemplate mongoTemplate;

	public List<EntryResponse> getEntries(String storyId, Integer beginIndex, Integer endIndex) {
		Query query = new Query();
		
		Criteria c = new Criteria();
		c.andOperator(where("storyId").is(storyId), where("currCharCount").lte(endIndex), where("currCharCount").gt(beginIndex));

		query.addCriteria(c);
		query.fields().exclude("storyId").exclude("currCharCount");
		
		return mongoTemplate.find(query, EntryResponse.class, "entryModel");
	}
	
	public void addEntry(EntryModel newEntryModel) {
		mongoTemplate.insert(newEntryModel); 
	}
	
	
}
