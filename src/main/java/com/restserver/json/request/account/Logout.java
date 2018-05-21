package com.restserver.json.request.account;

import com.models.AccessToken;

public class Logout extends BaseRequest{
    private String username;

    public Logout(AccessToken token, String username) {
        super(token);
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
