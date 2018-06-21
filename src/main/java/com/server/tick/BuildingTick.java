package com.server.tick;

import com.dbal.repository.TownRepository;
import com.logging.LogLevel;
import com.logging.Logger;
import com.models.BuildingQueue;
import com.models.Town;

import java.util.List;
import java.util.Set;


public class BuildingTick implements Runnable {
    public void update() {
        Logger.getInstance().log("BuildingTick Running", LogLevel.INFORMATION);
        TownRepository townRepository = new TownRepository();
        List<Town> towns = townRepository.findAllNoDuplicates(null);
        for (Town town : towns) {
            if (!town.getBuildingQueues().isEmpty()) {
                Set<BuildingQueue> queues = town.getBuildingQueues();
                for (BuildingQueue queue : queues) {
                    queue.setValue(queue.getValue() - 5);
                }
            }
        }
    }

    @Override
    public void run() {
        Logger.getInstance().log("BuildingTick Running", LogLevel.INFORMATION);

    }
}
