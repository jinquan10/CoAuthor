package com.nwm.coauthor.service.model;

public class RatingModel extends BaseModel {
    private String storyId;
    private String fbId;
    private Integer rating;
    
    public String getStoryId() {
        return storyId;
    }
    public void setStoryId(String storyId) {
        this.storyId = storyId;
    }
    public String getFbId() {
        return fbId;
    }
    public void setFbId(String fbId) {
        this.fbId = fbId;
    }
    public Integer getRating() {
        return rating;
    }
    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
