package com.nwm.coauthor.service.resource.response;

import com.nwm.coauthor.service.resource.BaseResource;

public class LikeResponse extends BaseResource{
    private Long likes;

    public Long getLikes() {
        return likes;
    }

    public void setLikes(Long likes) {
        this.likes = likes;
    }
}
