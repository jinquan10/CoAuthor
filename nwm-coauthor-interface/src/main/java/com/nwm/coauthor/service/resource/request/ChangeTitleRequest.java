package com.nwm.coauthor.service.resource.request;

import com.nwm.coauthor.service.resource.BaseResource;

public class ChangeTitleRequest extends BaseResource{
    private String title;

    public static ChangeTitleRequest initWithTitle(String title){
        ChangeTitleRequest request = new ChangeTitleRequest();
        request.setTitle(title);

        return request;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
