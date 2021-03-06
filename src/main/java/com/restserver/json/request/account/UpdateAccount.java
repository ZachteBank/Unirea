package com.restserver.json.request.account;

public class UpdateAccount extends BaseRequest {
    private String username;
    private String email;
    private String password;

    public UpdateAccount(String token, String username, String email, String password) {
        super(token);
        this.username = username;
        this.email = email.toLowerCase();
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
