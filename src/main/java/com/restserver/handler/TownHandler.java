package com.restserver.handler;


import com.dbal.repository.AccessTokenRepository;
import com.dbal.repository.IRepository;
import com.dbal.specification.AccessTokenSpecification;
import com.dbal.specification.TownSpecification;
import com.google.gson.Gson;
import com.models.AccessToken;
import com.models.Player;
import com.models.Town;
import com.restserver.exception.PlayerHasTownException;
import com.restserver.factory.TownFactory;
import com.restserver.json.request.town.BaseTownRequest;
import com.restserver.json.request.town.TownId;
import com.restserver.json.response.Reply;
import com.restserver.json.response.Status;
import com.restserver.utils.accesstoken.AccessTokenLevel;
import com.restserver.utils.accesstoken.AccessTokenUtil;

public class TownHandler implements ITownHandler {
    private IRepository townRepository;
    private AccessTokenRepository accessTokenRepository;
    private Gson gson = new Gson();

    public TownHandler(IRepository townRepository, IRepository accessTokenRepository) {
        this.townRepository = townRepository;
        this.accessTokenRepository = (AccessTokenRepository) accessTokenRepository;
    }

    @Override
    public Reply getTown(TownId data){
        AccessToken accessToken = accessTokenRepository.findOne(AccessTokenSpecification.getByAccessToken(data.getToken()));
        if(!AccessTokenUtil.checkAccess(accessToken, AccessTokenLevel.LOGGEDIN)){
            return new Reply(Status.NOAUTH, "Accesstoken not valid");
        }
        Town town = (Town) townRepository.findOne(data.getTownId());
        if(town == null){
            return new Reply(Status.NOTFOUND, "No town found");
        } else {
            return new Reply(Status.OK, gson.toJson(town));
        }
        
    }

    @Override
    public Reply createTown(Player player) {
        if(player == null){
            return null;
        }

        try {
            Town town = TownFactory.createTown(player);
            //return new Reply(Status.OK, )
        } catch (PlayerHasTownException e) {
            return new Reply(Status.ERROR, e.getMessage());
        }
        return null;
    }
}