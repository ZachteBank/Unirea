package com.restserver.accesstoken;

import com.models.AccessToken;
import com.models.Player;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class AccessTokenFactory implements IAccessTokenFactory {
    @Override
    public AccessToken newToken(Player player) {
        return new AccessToken(generateUUID(), player, new Date(System.currentTimeMillis()), generateExpirationDate());
    }

    private String generateUUID(){
        final String uuid = UUID.randomUUID().toString().replace("-", "");
        System.out.println("uuid = " + uuid);
        return uuid;
    }

    private Date generateExpirationDate(int expiration){
        long expirationTime = TimeUnit.MILLISECONDS.convert(expiration, TimeUnit.MINUTES);
        return new Date(System.currentTimeMillis() + expirationTime);
    }
}