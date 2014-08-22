package com.nwm.coauthor.service.resource.request;

import com.nwm.coauthor.service.util.DisplayName;
import com.nwm.coauthor.service.util.DisplayOrder;
import com.nwm.coauthor.service.util.HintText;
import com.nwm.coauthor.service.util.MaxLength;
import com.nwm.coauthor.service.util.MinLength;
import com.nwm.coauthor.service.util.RequiredField;

public class NativeAuthRequest {
    @RequiredField
    @DisplayOrder(1)
    @MinLength(3)
    @MaxLength(100)
    @DisplayName("3 to 100 characters")
    @HintText("Username")
    private String username;
    
    @RequiredField
    @DisplayOrder(2)
    @MinLength(4)
    @MaxLength(100)
    @DisplayName("4 to 100 characters")
    @HintText("Password")
    private String password;

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
}
