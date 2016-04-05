package ai;

import aiantwars.EAction;
import aiantwars.EAntType;
import aiantwars.IAntAI;
import aiantwars.IAntInfo;
import aiantwars.IEgg;
import aiantwars.ILocationInfo;
import ant.CarrierLogic;
import behaviour.Breeding;
import java.util.List;
import memory.CollectiveMemory;
import static utility.Action.getRandomAction;
import utility.Debug;
import static utility.Debug.println;

public class JT_Destroyer implements IAntAI {

    private final CollectiveMemory collectiveMemory = CollectiveMemory.getInstance();
    private final Breeding breeding = Breeding.getInstance();
    private int turn;

    @Override
    public EAction chooseAction(IAntInfo thisAnt, ILocationInfo thisLocation, List<ILocationInfo> visibleLocations, List<EAction> possibleActions) {
        
        visibleLocations.add(thisLocation);
        collectiveMemory.addVisibleLocations(visibleLocations);

        EAction action = null;

        // ALL
        if (possibleActions.contains(EAction.EatFood) && thisAnt.getHitPoints() < 10) {
            action = EAction.EatFood;

            // CARRIER
        } else if (thisAnt.getAntType().equals(EAntType.CARRIER)) {
            CarrierLogic carrierLogic = new CarrierLogic(thisAnt, thisLocation, possibleActions);
            action = carrierLogic.getAction();
            println("Carrier: chose action: " + action.toString());

            // QUEEN
        } else if (thisAnt.getAntType().equals(EAntType.QUEEN)) {
            if (possibleActions.contains(EAction.LayEgg)) {
                action = EAction.LayEgg;
            } else if (possibleActions.contains(EAction.PickUpFood)) {
                action = EAction.PickUpFood;
            } else {
                action = getRandomAction(possibleActions);
            }

            // SCOUT
        } else if (thisAnt.getAntType().equals(EAntType.SCOUT)) {
            action = getRandomAction(possibleActions);

            //WARRIOR
        } else if (thisAnt.getAntType().equals(EAntType.WARRIOR)) {
            if (possibleActions.contains(EAction.Attack) && visibleLocations.get(0).getAnt().getTeamInfo().getTeamID() != thisAnt.getTeamInfo().getTeamID()) {
                action = EAction.Attack;
            } else {
                getRandomAction(possibleActions);
            }
        }

        return action;
    }

    @Override
    public void onStartTurn(IAntInfo thisAnt, int turn
    ) {
        this.turn = turn;
        println("System: ID: " + thisAnt.antID() + " onStartTurn(" + turn + ")");
    }

    @Override
    public void onAttacked(IAntInfo thisAnt, int dir, IAntInfo attacker, int damage) {
        println("System: ID: " + thisAnt.antID() + " onAttacked: " + damage + " damage");
    }

    @Override
    public void onDeath(IAntInfo thisAnt) {
        collectiveMemory.removeAnt(thisAnt);
        println("System: ID: " + thisAnt.antID() + " onDeath");
    }

    @Override
    public void onLayEgg(IAntInfo thisAnt, List<EAntType> types, IEgg egg) {
        int index = breeding.getBreedingAction(collectiveMemory.getAnts(), turn);
        EAntType type = types.get(index);
        println("System: ID: " + thisAnt.antID() + " onLayEgg: " + type);
        egg.set(type, this);
    }

    @Override
    public void onHatch(IAntInfo thisAnt, ILocationInfo thisLocation, int worldSizeX, int worldSizeY) {
        collectiveMemory.addAnt(thisAnt);
        if (thisAnt.getAntType().getTypeName().equals("Queen")) {
            collectiveMemory.setQueenSpawn(thisLocation);
        }
        println("System: ID: " + thisAnt.antID() + " onHatch");
        
        collectiveMemory.saveWorldSizeX(worldSizeX);
        collectiveMemory.saveWorldSizeY(worldSizeY);
    }

}
