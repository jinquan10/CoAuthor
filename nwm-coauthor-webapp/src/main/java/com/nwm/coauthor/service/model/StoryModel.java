package com.nwm.coauthor.service.model;

import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

public class StoryModel extends BaseModel {
    private ObjectId _id;
    private String leaderFbId;    	// - changeable by the leader only
    private String title;
    private Integer numCharacters;
    private Boolean isPublished;
    private List<String> fbFriends;
    private Integer likes;    		// - changeable by public
    private String latestFriendWithEntry; // - changed when an entry is submitted
    private Date storyLastUpdated;  // - changed when anything above is updated
    private long entryOrdinal;    	// - changed when entry is added
    private long commentOrdinal;    // - changed when comment is added
}
