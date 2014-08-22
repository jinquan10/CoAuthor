package com.nwm.coauthor.service.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.nwm.coauthor.Constants;

@Document(collection = Constants.USER_COLLECTION)
public class UserModel extends BaseModel {
    @Id
    private String id;
    
    @Indexed(background = true)
    private String coToken;
    
    @Indexed(background = true)
    private String username;
    private String password;
    
    private Long lastLogin;
    
    public UserModel() {
        super();
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Long lastLogin) {
        this.lastLogin = lastLogin;
    }
}
