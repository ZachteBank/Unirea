package com.restserver.utils.accesstoken;

import com.dbal.repository.AccessTokenRepository;
import com.dbal.repository.PlayerRepository;
import com.dbal.specification.AccessTokenSpecification;
import com.models.AccessToken;
import com.models.Player;
import com.restserver.accesstoken.AccessTokenFactory;
import com.restserver.accesstoken.IAccessTokenFactory;

public class AccessTokenUtil {

    private static IAccessTokenFactory accessTokenFactory = new AccessTokenFactory();
    private static AccessTokenRepository accessTokenRepository = new AccessTokenRepository();

    public static boolean checkAccess(String accessTokenString, AccessTokenLevel accessLevel){
        if(accessLevel == null || accessTokenString.isEmpty()){
            throw new IllegalArgumentException("AccessTokenString is empty or AccessTokenLevel is null");
        }

        AccessToken accessToken = accessTokenRepository.findOne(AccessTokenSpecification.getByAccessToken(accessTokenString));

        return checkAccess(accessToken, accessLevel);
    }

    public static boolean checkAccess(AccessToken accessToken, AccessTokenLevel accessLevel){
        if(accessLevel == null){
            throw new IllegalArgumentException("AccessToken is null or AccessTokenLevel is null");
        }

        if(accessToken == null){
            return accessLevel == AccessTokenLevel.NoLogin;
        }

        if(accessToken.isExpired()){
            return accessLevel == AccessTokenLevel.NoLogin;
        }
        accessToken.refresh();
        return true;
    }

    public static AccessToken newToken(Player player, AccessTokenLevel tokenLevel){
        return accessTokenFactory.newToken(player, tokenLevel);
    }
}
