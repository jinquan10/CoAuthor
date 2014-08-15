package com.nwm.coauthor.service.dao;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.FindAndModifyOptions;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

import com.mongodb.WriteResult;
import com.nwm.coauthor.Constants;
import com.nwm.coauthor.Praises;
import com.nwm.coauthor.service.model.EntryModel;
import com.nwm.coauthor.service.model.StoryModel;
import com.nwm.coauthor.service.model.TotalCharsModel;
import com.nwm.coauthor.service.model.UpdateStoryForNewEntryModel;
import com.nwm.coauthor.service.resource.response.StoryInListResponse;
import com.nwm.coauthor.service.resource.response.StoryResponse;

@Component
public class StoryDAOImpl {
	@Autowired
	@Qualifier("mongoTemplate")
	private MongoTemplate mongoTemplate;

	public void createStory(StoryModel storyModel) {
		mongoTemplate.insert(storyModel);
	}

	public Integer getTotalChars(String storyId) {
		Query q = new Query(where("storyId").is(storyId));

		q.fields().include("currEntryCharCount");

		TotalCharsModel model = mongoTemplate.findOne(q, TotalCharsModel.class, "storyModel");

		return model.getCurrEntryCharCount();
	}

	public List<StoryInListResponse> getMyStories(String fbId) {
		Query q = new Query();
		Criteria c = new Criteria();
		c.orOperator(where("leaderFbId").is(fbId), where("fbFriends").is(fbId));
		q.addCriteria(c);

		return mongoTemplate.find(q, StoryInListResponse.class, "storyModel");
	}

	public StoryResponse getStory(String id) {
		return mongoTemplate.findById(id, StoryResponse.class, Constants.STORY_COLLECTION);
	}

	public WriteResult updateStoryForAddingEntry(UpdateStoryForNewEntryModel model) {
		Query q = new Query(where("storyId").is(model.getStoryId()));

		Update u = new Update();
		u.set("currEntryCharCount", model.getCurrEntryCharCount());
		u.set("lastEntry", model.getLastEntry());
		u.set("storyLastUpdated", model.getStoryLastUpdated());
		u.set("lastFriendWithEntry", model.getLastFriendWithEntry());

		return mongoTemplate.updateFirst(q, u, StoryModel.class);
	}

	public StoryInListResponse likeStory(String storyId) {
		Query query = new Query();
		query.addCriteria(where("storyId").is(storyId));

		Update update = new Update();
		update.inc("likes", 1);

		return mongoTemplate.findAndModify(query, update, FindAndModifyOptions.options().returnNew(true), StoryInListResponse.class, "storyModel");
	}

	// public List<PrivateStoryResponse> getStoriesByFbId(String fbId) {
	// Criteria c = new Criteria();
	// c.orOperator(where("leaderFbId").is(fbId), where("fbFriends").is(fbId));
	//
	// Query q = new Query();
	// q.fields().slice("entries", 1);
	// q.addCriteria(c);
	//
	// return mongoTemplate.find(q, PrivateStoryResponse.class, "storyModel");
	// }
	//
	// public WriteResult addEntry(String fbId, AddEntryModel request) {
	// Criteria c = new Criteria();
	// Criteria orC = new Criteria();
	//
	// c.andOperator(where("_id").is(request.getStoryId()),
	// where("lastFriendEntry").ne(fbId),
	// where("numCharacters").gte(request.getEntry().getEntry().length()),
	// where("version").is(request.getVersion()),
	// orC.orOperator(where("fbFriends").is(fbId),
	// where("leaderFbId").is(fbId)));
	//
	// Update update = new Update();
	// update.push("entries", request.getEntry());
	// update.inc("version", 1);
	// update.set("lastFriendEntry", fbId);
	//
	// Query q = new Query();
	// q.addCriteria(c);
	//
	// return mongoTemplate.updateFirst(q, update, StoryModel.class);
	// }
	//
	// public PrivateStoryResponse getPrivateStory(ObjectId storyId) {
	// Query q = new Query(where("_id").is(storyId));
	//
	// return mongoTemplate.findOne(q, PrivateStoryResponse.class,
	// "storyModel");
	// }
	//

	//
	public WriteResult publishStory(String fbId, String storyId, Long now) {
		Criteria criteria = new Criteria();
		criteria.andOperator(where("leaderFbId").is(fbId), where("storyId").is(storyId), where("title").ne(null), where("title").ne(""));

		Query query = new Query(criteria);

		Update update = new Update();
		update.set("isPublished", true);
		update.set("storyLastUpdated", now);

		return mongoTemplate.updateFirst(query, update, StoryModel.class);
	}

	public WriteResult changeStoryTitle(String fbId, String storyId, String title, Long now) {
		Criteria criteria = new Criteria();
		criteria.andOperator(where("storyId").is(storyId), where("leaderFbId").is(fbId), where("isPublished").is(false));

		Update update = new Update();
		update.set("title", title);
		update.set("storyLastUpdated", now);

		return mongoTemplate.updateFirst(new Query(criteria), update, StoryModel.class);
	}

	public StoryInListResponse newFriends(String fbId, String storyId, List<String> newFriends) {
		Query q = new Query();

		Criteria a = new Criteria();
		a.orOperator(where("leaderFbId").is(fbId), where("fbFriends").is(fbId));

		Criteria b = new Criteria();
		b.andOperator(where("storyId").is(storyId), where("fbFriends").nin(newFriends), where("leaderFbId").nin(newFriends), a);

		q.addCriteria(b);

		Update update = new Update();
		update.set("storyLastUpdated", new Date().getTime());
		update.pushAll("fbFriends", newFriends.toArray());

		return mongoTemplate.findAndModify(q, update, FindAndModifyOptions.options().returnNew(true), StoryInListResponse.class, "storyModel");
	}

	public List<StoryInListResponse> getTopViewStories(Integer topViewCount) {
		Query query = new Query();
		query.with(new Sort(Sort.Direction.DESC, "views"));

		return mongoTemplate.find(query, StoryInListResponse.class, Constants.STORY_COLLECTION);
	}

	public void startNextEntryTimer(String storyId, Long nextEntryAvailableAt) {
		Query q = new Query();

		Criteria c = new Criteria();
		c.andOperator(Criteria.where("_id").is(new ObjectId(storyId)), Criteria.where("nextEntryAvailableAt").exists(false));

		q.addCriteria(c);

		Update u = new Update();
		u.set("nextEntryAvailableAt", nextEntryAvailableAt);

		mongoTemplate.updateFirst(q, u, Constants.STORY_COLLECTION);
	}

	public void queueNextEntry(String storyId, EntryModel entry) {
		Query q = new Query();

		q.addCriteria(Criteria.where("_id").is(new ObjectId(storyId)));

		Update u = new Update();
		u.push("potentialEntries", entry);
		u.inc("potentialEntriesCount", 1);

		mongoTemplate.updateFirst(q, u, Constants.STORY_COLLECTION);
	}

	public void incrementStoryViews(String id) {
		Query q = new Query();
		q.addCriteria(Criteria.where("_id").is(new ObjectId(id)));

		Update u = new Update();
		u.inc("views", 1);

		mongoTemplate.updateFirst(q, u, Constants.STORY_COLLECTION);
	}

	public void voteForEntry(String coToken, String storyId, String entryId) {
		Query q = new Query();
		q.addCriteria(Criteria.where("_id").is(new ObjectId(storyId)));
		q.addCriteria(Criteria.where("potentialEntries").elemMatch(
				Criteria.where("_id").is(entryId)
						.andOperator(new Criteria().orOperator(Criteria.where("votedAuthors").nin(coToken), Criteria.where("votedAuthors").exists(false)))));

		Update u = new Update();
		u.addToSet("potentialEntries.$.votedAuthors", coToken);
		u.inc("potentialEntries.$.votes", 1);

		mongoTemplate.updateFirst(q, u, Constants.STORY_COLLECTION);
	}

	public StoryModel getStoryInternal(String storyId) {
		return mongoTemplate.findById(storyId, StoryModel.class, Constants.STORY_COLLECTION);
	}

	public void assignEntry(String storyId, EntryModel pickedEntry) {
		Query q = new Query();
		q.addCriteria(Criteria.where("_id").is(new ObjectId(storyId)));

		Update u = new Update();
		u.unset("potentialEntries");
		u.unset("nextEntryAvailableAt");
		u.inc("entriesCount", 1);
		u.set("potentialEntriesCount", 0);
		u.set("lastEntry", pickedEntry);
		u.set("lastUpdated", new Date().getTime());
		u.push("entries", pickedEntry);

		mongoTemplate.updateFirst(q, u, Constants.STORY_COLLECTION);
	}

	public void incrementPraise(String coToken, String storyId, String praise) {
		String dbPraise = "praises." + praise;
		String authors = dbPraise + ".authors";

		Query q = new Query();
		q.addCriteria(Criteria.where("_id").is(new ObjectId(storyId)));
		q.addCriteria(Criteria.where(authors).nin(coToken));

		Update u = new Update();
		u.inc(dbPraise + ".count", 1);
		u.push(authors, coToken);

		mongoTemplate.updateFirst(q, u, Constants.STORY_COLLECTION);
	}

	public Praises getPraises(String storyId) {
		Query q = new Query();
		q.addCriteria(Criteria.where("_id").is(new ObjectId(storyId)));
		q.fields().include("praises");

		StoryModel story = mongoTemplate.findOne(q, StoryModel.class);
		return story.getPraises();
	}
}
