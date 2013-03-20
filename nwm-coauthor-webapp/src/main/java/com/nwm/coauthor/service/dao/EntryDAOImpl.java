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

	public List<EntryResponse> getEntries(String storyId, Integer min, Integer max) {
		Query query = new Query();
		
		Criteria c = new Criteria();
		c.andOperator(where("storyId").is(storyId), where("currCharCount").lt(max), where("currCharCount").gte(min));

		query.addCriteria(c);
		query.addCriteria(where("currCharCount").gte(max)).limit(1);
		query.fields().exclude("storyId").exclude("currCharCount");
		
		return mongoTemplate.find(query, EntryResponse.class);
	}
	
	public void addEntry(EntryModel newEntryModel) {
		mongoTemplate.insert(newEntryModel); 
	}
	
	
}
