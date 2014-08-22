package com.nwm.coauthor.service.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class BaseModel {
    private String createdById;
    private String createdByDisplayName;
    
    private Long createdOn;
    private Long lastUpdated;
    
    private Long timeZoneOffsetMinutes;
    
    public BaseModel() {
        this.lastUpdated = this.createdOn = System.currentTimeMillis();
    }
    
    public String getCreatedByDisplayName() {
        return createdByDisplayName;
    }
    
    public void setCreatedByDisplayName(String createdByDisplayName) {
        this.createdByDisplayName = createdByDisplayName;
    }
    
    public Long getCreatedOn() {
        return createdOn;
    }
    
    public void setCreatedOn(Long createdOn) {
        this.createdOn = createdOn;
    }
    
    public Long getLastUpdated() {
        return lastUpdated;
    }
    
    public void setLastUpdated(Long lastUpdated) {
        this.lastUpdated = lastUpdated;
    }
    
    public String getCreatedById() {
        return createdById;
    }
    
    public void setCreatedById(String createdById) {
        this.createdById = createdById;
    }
    
    public Long getTimeZoneOffsetMinutes() {
        return timeZoneOffsetMinutes;
    }
    
    public void setTimeZoneOffsetMinutes(Long timeZoneOffsetMinutes) {
        this.timeZoneOffsetMinutes = timeZoneOffsetMinutes;
    }
}
