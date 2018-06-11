package com.restserver.handler;

import com.dbal.repository.BuildingRepository;
import com.dbal.repository.TownBuildingRepository;
import com.dbal.repository.TownRepository;
import com.models.Building;
import com.models.Town;
import com.models.TownBuilding;
import com.restserver.buildings.resource.ResourceType;
import com.restserver.buildings.resource.models.BaseResourceBuilding;
import com.restserver.buildings.resource.models.IronBuilding;
import com.restserver.buildings.resource.models.OilBuilding;
import com.restserver.buildings.resource.models.WoodBuilding;

import java.util.Set;

public class BuildingHandler {

    TownBuildingRepository townBuildingRepository = new TownBuildingRepository();
    BuildingRepository buildingRepository = new BuildingRepository();
    TownRepository townRepository = new TownRepository();

    public TownBuilding getNormalTownBuildings(int townId, int buildingId){
        switch (buildingId){
            //HEADQUARTERS
            case 4:
                return null;
            //WAREHOUSE
            case 5:
                return null;
            //BARRACKS
            case 6:
                return null;
            //TRAINING GROUNDS
            case 7:
                return null;
            //FOUNDRY
            case 8:
                return null;
            //AMMUNITION DEPOT
            case 9:
                return null;
            //WALL
            case 10:
                return null;
        }
        return null;
    }
    
    public Building getResourceBuilding(int townId, int buildingId){
        
        Town town = townRepository.findOne(townId);
        if (town == null){
            return null;
        }
        Set<TownBuilding> townBuildings = town.getTownBuildings();
        
        switch (buildingId){
            //OIL
            case 1:
                OilBuilding oilBuilding = new OilBuilding();
                return constructResourceBuilding(townBuildings, oilBuilding);
            //IRON
            case 2:
                IronBuilding ironBuilding = new IronBuilding();
                return constructResourceBuilding(townBuildings, ironBuilding);
            //WOOD
            case 3:
                WoodBuilding woodBuilding = new WoodBuilding();
                return constructResourceBuilding(townBuildings, woodBuilding);
        }
        return null;
    }

    private Building constructResourceBuilding(Set<TownBuilding> townBuildings, BaseResourceBuilding building) {
        for (TownBuilding townBuilding : townBuildings){
            if(townBuilding.getBuilding().getId() == 3){
                building.setName(townBuilding.getBuilding().getName());
                building.setBuildingLevel(townBuilding.getLevel());
                building.setResourceType(ResourceType.WOOD);
            }
        }
        return building;
    }


}
