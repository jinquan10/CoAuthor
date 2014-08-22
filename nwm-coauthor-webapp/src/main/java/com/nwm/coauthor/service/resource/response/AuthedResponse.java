package com.nwm.coauthor.service.resource.response;

public class AuthedResponse {
    private String coToken;

    public AuthedResponse(String coToken){
        this.coToken = coToken;
    }
    
    public String getCoToken() {
        return coToken;
    }

    public void setCoToken(String coToken) {
        this.coToken = coToken;
    }
}
