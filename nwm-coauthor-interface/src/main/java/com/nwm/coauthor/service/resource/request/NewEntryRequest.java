package com.nwm.coauthor.service.resource.request;

public class NewEntryRequest {
    private String entry;
    private Integer currEntryCharCount;
    
    public String getEntry() {
        return entry;
    }
    public void setEntry(String entry) {
        this.entry = entry;
    }
    public Integer getCurrEntryCharCount() {
        return currEntryCharCount;
    }
    public void setCurrEntryCharCount(Integer currEntryCharCount) {
        this.currEntryCharCount = currEntryCharCount;
    }
}
