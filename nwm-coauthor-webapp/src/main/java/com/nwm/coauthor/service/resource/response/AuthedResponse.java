package com.nwm.coauthor.service.resource.response;

public class AuthedResponse {
    private String coToken;
    private String username;
    
    public AuthedResponse(String coToken, String username){
        this.coToken = coToken;
        this.username = username;
    }
    
    public String getCoToken() {
        return coToken;
    }

    public void setCoToken(String coToken) {
        this.coToken = coToken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
