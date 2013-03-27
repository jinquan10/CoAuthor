package com.nwm.coauthor.service.resource.request;

public class NewEntryRequest {
    private String entry;
    private Integer charCountForVersioning;
    
    public String getEntry() {
        return entry;
    }
    public void setEntry(String entry) {
        this.entry = entry;
    }
    public Integer getCharCountForVersioning() {
        return charCountForVersioning;
    }
    public void setCharCountForVersioning(Integer charCountForVersioning) {
        this.charCountForVersioning = charCountForVersioning;
    }
}
