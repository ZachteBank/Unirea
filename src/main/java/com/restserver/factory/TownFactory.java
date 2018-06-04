package com.restserver.factory;

import com.dbal.repository.TownRepository;
import com.models.*;
import com.restserver.buildings.resource.factory.IResourceBuildingFactory;
import com.restserver.buildings.resource.factory.ResourceBuildingFactory;
import com.restserver.exception.PlayerHasTownException;

import java.util.HashSet;
import java.util.Set;

public class TownFactory implements ITownFactory {
    private static TownRepository townRepository = new TownRepository();
    private static IResourceBuildingFactory resourceBuildingFactory = new ResourceBuildingFactory();

    public static Town createTown(Player player) throws PlayerHasTownException {
        if(player == null){
            throw new IllegalArgumentException("Player is null");
        }
        if(player.getTowns().size() != 0){
            throw new PlayerHasTownException();
        }

        Town town = new Town(player, "My first town");

        TownBuilding ironBuilding = new TownBuilding();
        ironBuilding.setBuilding(resourceBuildingFactory.createIronBuilding());
        town.addTownBuilding(ironBuilding);

        TownBuilding oilBuilding = new TownBuilding();
        oilBuilding.setBuilding(resourceBuildingFactory.createOilBuilding());
        town.addTownBuilding(oilBuilding);

        TownBuilding woodBuilding = new TownBuilding();
        woodBuilding.setBuilding(resourceBuildingFactory.createWoodBuilding());
        town.addTownBuilding(woodBuilding);

        //TODO: add basic buildings
        //TODO: add resources

        townRepository.save(town);
        return town;
    }
}
