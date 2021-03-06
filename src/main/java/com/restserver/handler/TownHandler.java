package com.restserver.handler;

import com.dbal.repository.AccessTokenRepository;
import com.dbal.repository.IRepository;
import com.dbal.repository.PlayerRepository;
import com.dbal.repository.TownRepository;
import com.dbal.specification.AccessTokenSpecification;
import com.google.gson.Gson;
import com.models.*;
import com.restserver.exception.PlayerHasTownException;
import com.restserver.factory.TownFactory;
import com.restserver.json.response.Reply;
import com.restserver.json.response.Status;
import com.restserver.json.response.town.ArmyResponse;
import com.restserver.json.response.town.BuildingResponse;
import com.restserver.json.response.town.ResourceResponse;
import com.restserver.json.response.town.TownResponse;

import java.util.*;

public class TownHandler implements ITownHandler {
    private TownRepository townRepository;
    private PlayerRepository playerRepository = new PlayerRepository();
    private AccessTokenRepository accessTokenRepository = new AccessTokenRepository();
    private IBuildingHandler buildingHandler = new BuildingHandler();
    private Gson gson = new Gson();

    public TownHandler(IRepository townRepository) {
        this.townRepository = (TownRepository) townRepository;
    }

    @Override
    public Reply getTown(int townId) {
        Town town = townRepository.findOne(townId);
        if (town == null) {
            return new Reply(Status.NOTFOUND, "No town found");
        } else {
            TownResponse townResponse = new TownResponse(townResourcesToMap(town), townBuildingsToMap(town), townArmyToMap(town), town.getX(), town.getY(), town.getPlayer().getUsername(), town.getPlayer().getPlayerId(), town.getName(), town.getId());
            return new Reply(Status.OK, gson.toJson(townResponse));
        }
    }

    @Override
    public Reply getTownByAccesstoken(String token) {
        AccessToken accessToken = accessTokenRepository.findOne(AccessTokenSpecification.getByAccessToken(token));
        int townId = 0;
        for (Town town : accessToken.getPlayer().getTowns()){
            townId = town.getId();
        }
        Town town = townRepository.findOne(townId);
        if (town == null) {
            return new Reply(Status.NOTFOUND, "No town found");
        } else {
            TownResponse townResponse = new TownResponse(townResourcesToMap(town), townBuildingsToMap(town), townArmyToMap(town), town.getX(), town.getY(), town.getPlayer().getUsername(), town.getPlayer().getPlayerId(), town.getName(), town.getId());
            return new Reply(Status.OK, gson.toJson(townResponse));
        }
    }

    @Override
    public Reply getTownsByPlayer(Player player) {
        Set<Town> towns = player.getTowns();
        if (towns.isEmpty()) {
            return new Reply(Status.NOTFOUND, "No town found");
        } else {
            Set<TownResponse> townResponseSet = new HashSet<>();
            for (Town town : towns) {
                townResponseSet.add(new TownResponse(townResourcesToMap(town), townBuildingsToMap(town), townArmyToMap(town), town.getX(), town.getY(), town.getPlayer().getUsername(), town.getPlayer().getPlayerId(), town.getName(), town.getId()));
            }
            return new Reply(Status.OK, gson.toJson(townResponseSet));
        }

    }

    @Override
    public Reply getTownsByPlayerId(int playerId) {
        Player player = playerRepository.findOne(playerId);
        return getTownsByPlayer(player);
    }

    @Override
    public Reply createTown(Player player) {
        if (player == null) {
            return null;
        }

        try {
            Town town = TownFactory.createTown(player);
            return getTown(town.getId());
        } catch (PlayerHasTownException e) {
            return new Reply(Status.ERROR, e.getMessage());
        }

    }

    public ArrayList<ResourceResponse> townResourcesToMap(Town town){
        ArrayList<ResourceResponse> townResources = new ArrayList<>();
        for (TownResources resources : town.getTownResources()){
            ResourceResponse resourceResponse = new ResourceResponse();
            resourceResponse.setAmount(resources.getValue());
            resourceResponse.setId(resources.getResource().getId());
            townResources.add(resourceResponse);
        }
        return townResources;
    }

    public ArrayList<ArmyResponse> townArmyToMap(Town town){
        ArrayList<ArmyResponse> townArmy = new ArrayList<>();
        if (town.getTownArmies().isEmpty()){
            for (int i = 1; i < 4; i++){
                ArmyResponse armyResponse = new ArmyResponse();
                armyResponse.setAmount(0);
                armyResponse.setId(i);
                townArmy.add(armyResponse);
            }
        } else {
            for (TownArmy army : town.getTownArmies()) {
                ArmyResponse armyResponse = new ArmyResponse();
                armyResponse.setAmount(army.getValue());
                armyResponse.setId(army.getArmy().getId());
                townArmy.add(armyResponse);
            }
        }
        return townArmy;
    }

    public ArrayList<BuildingResponse> townBuildingsToMap(Town town){
        ArrayList<BuildingResponse> townBuildings = new ArrayList<>();
        for (TownBuilding buildings : town.getTownBuildings()){
            BuildingResponse buildingResponse = new BuildingResponse();
            buildingResponse.setId(buildings.getBuilding().getId());
            buildingResponse.setLevel(buildings.getLevel());
            buildingResponse.setName(buildings.getBuilding().getName());
            Map<String, Integer> cost = buildingHandler.getResourceRequirements(buildingResponse.getLevel(),buildingResponse.getId());
            ArrayList<ResourceResponse> resourceCost = new ArrayList<>();
            for (Map.Entry<String, Integer> pair : cost.entrySet()) {
                ResourceResponse resourceResponse = new ResourceResponse();
                if (pair.getKey().equals("Oil")){
                    resourceResponse.setId(1);
                } else if (pair.getKey().equals("Iron")){
                    resourceResponse.setId(2);
                } else if (pair.getKey().equals("Wood")){
                    resourceResponse.setId(3);
                }
                resourceResponse.setAmount(pair.getValue());
                resourceCost.add(resourceResponse);
            }
            buildingResponse.setUpgradeCost(resourceCost);
            if (buildings.getBuilding().getId() < 4){
                buildingResponse.setResourceProduction((int) buildingHandler.calculateResourceProduction(buildings.getLevel()));
            }
            townBuildings.add(buildingResponse);
        }
        return townBuildings;
    }
}