package com.nwm.coauthor.service.resource.request;

import com.nwm.coauthor.service.util.RequiredField;

public class LogoutRequest {
    @RequiredField
    private String coToken;

    public String getCoToken() {
        return coToken;
    }

    public void setCoToken(String coToken) {
        this.coToken = coToken;
    }
}
