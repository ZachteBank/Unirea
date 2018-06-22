package com.restserver.handler;

import com.dbal.repository.TownArmyRepository;
import com.dbal.repository.TownRepository;
import com.models.Town;
import com.models.TownArmy;
import com.restserver.json.request.TrainArmy;
import com.restserver.json.response.Reply;
import com.restserver.json.response.Status;

public class ArmyHandler {

    public Reply trainArmy(TrainArmy trainArmy) {
        TownArmy townArmy = new TownArmy();
        townArmy.setArmy(trainArmy.getArmy());
        TownRepository townRepository = new TownRepository();
        Town town = townRepository.findOne(trainArmy.getTownId());
        if (town == null) {
            return new Reply(Status.ERROR, "something went wrong");
        }
        townArmy.setTown(town);
        townArmy.setValue(trainArmy.getValue());
        TownArmyRepository townArmyRepository = new TownArmyRepository();
        townArmyRepository.save(townArmy);
        return new Reply(Status.OK, "succeeded");
    }
}