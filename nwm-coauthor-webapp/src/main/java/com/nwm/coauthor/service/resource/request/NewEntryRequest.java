package com.nwm.coauthor.service.resource.request;

public class NewEntryRequest {
    private String entry;
    private Integer charCountForVersioning;

    public static NewEntryRequest newEntry(String entry, Integer charCountForVersioning){
        NewEntryRequest request = new NewEntryRequest();
        request.setEntry(entry);
        request.setCharCountForVersioning(charCountForVersioning);
        
        return request;
    }
    
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
