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
import java.util.Random;
import memory.CollectiveMemory;
import static utility.Action.getRandomAction;

public class Main implements IAntAI {

    private final Random rnd = new Random();
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
            
            // QUEEN
        } else if (thisAnt.getAntType().equals(EAntType.QUEEN)) {
            if (possibleActions.contains(EAction.LayEgg)) {
                action = EAction.LayEgg;
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

        StringBuilder actions = new StringBuilder();
        for (EAction a : possibleActions) {
            actions.append(a.toString());
            actions.append(", ");
        }
        System.out.println("Available actions: " + actions.toString());
        System.out.println("ID: " + thisAnt.antID() + " chose action: " + action);
        return action;
    }

    @Override
    public void onStartTurn(IAntInfo thisAnt, int turn
    ) {
        this.turn = turn;
        System.out.println("ID: " + thisAnt.antID() + " onStartTurn(" + turn + ")");
    }

    @Override
    public void onAttacked(IAntInfo thisAnt, int dir, IAntInfo attacker, int damage
    ) {
        System.out.println("ID: " + thisAnt.antID() + " onAttacked: " + damage + " damage");
    }

    @Override
    public void onDeath(IAntInfo thisAnt
    ) {
        collectiveMemory.removeAnt(thisAnt);
        System.out.println("ID: " + thisAnt.antID() + " onDeath");
    }

    @Override
    public void onLayEgg(IAntInfo thisAnt, List<EAntType> types, IEgg egg
    ) {
        int index = breeding.getBreedingAction(collectiveMemory.getAnts(), turn);
        EAntType type = types.get(index);
        System.out.println("ID: " + thisAnt.antID() + " onLayEgg: " + type);
        egg.set(type, this);
    }

    @Override
    public void onHatch(IAntInfo thisAnt, ILocationInfo thisLocation, int worldSizeX, int worldSizeY
    ) {
        collectiveMemory.addAnt(thisAnt);
        if (thisAnt.getAntType().getTypeName().equals("Queen")) {
            collectiveMemory.setQueenSpawn(thisLocation);
        }
        System.out.println("ID: " + thisAnt.antID() + " onHatch");
        collectiveMemory.saveWorldSizeX(worldSizeX);
        collectiveMemory.saveWorldSizeY(worldSizeY);
    }

}
