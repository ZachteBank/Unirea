package com.restserver.handler;

import com.models.Building;

public interface IBuildingHandler {
    Building getResourceBuilding(int townId, int buildingId);
}