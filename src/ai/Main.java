package ai;

import aiantwars.EAction;
import aiantwars.EAntType;
import aiantwars.IAntAI;
import aiantwars.IAntInfo;
import aiantwars.IEgg;
import aiantwars.ILocationInfo;
import behaviour.food.FoodMain;
import behaviour.Breeding;
import java.util.List;
import java.util.Random;
import memory.CollectiveMemory;

public class Main implements IAntAI {

    private final Random rnd = new Random();
    private final CollectiveMemory collectiveMemory = CollectiveMemory.getInstance();
    private int worldSizeX;
    private int worldSizeY;
    private final Breeding breeding = Breeding.getInstance();
    private int turn;
    private FoodMain foodMain;

    @Override
    public EAction chooseAction(IAntInfo thisAnt, ILocationInfo thisLocation, List<ILocationInfo> visibleLocations, List<EAction> possibleActions) {
        visibleLocations.add(thisLocation);
        collectiveMemory.addVisibleLocations(visibleLocations);
        foodMain = new FoodMain(thisAnt, thisLocation, visibleLocations);

        EAction action = null;
        if (possibleActions.contains(EAction.EatFood) && thisAnt.getHitPoints() < 10) {
            action = EAction.EatFood;
        } else if (possibleActions.contains(EAction.LayEgg)) {
            action = EAction.LayEgg;
        } else if (possibleActions.contains(EAction.Attack) && visibleLocations.get(0).getAnt().getTeamInfo().getTeamID() != thisAnt.getTeamInfo().getTeamID()) {
            action = EAction.Attack;
        } else if (possibleActions.contains(EAction.PickUpFood) && thisAnt.getFoodLoad() < 15) {
//            action = foodMain.getAction(); //array out of bounds!
            action = EAction.PickUpFood;

        } else {
            action = possibleActions.get(rnd.nextInt(possibleActions.size()));
            if (action == EAction.Attack) {
                action = EAction.Pass;
            }
        }
        StringBuilder actions = new StringBuilder();
        for (EAction a : possibleActions) {
            actions.append(a.toString());
            actions.append(", ");
        }
        System.out.println(actions.toString());
        System.out.println("ID: " + thisAnt.antID() + " chooseAction: " + action);
        return action;
    }

    @Override
    public void onStartTurn(IAntInfo thisAnt, int turn) {
        this.turn = turn;
        System.out.println("ID: " + thisAnt.antID() + " onStartTurn(" + turn + ")");
    }

    @Override
    public void onAttacked(IAntInfo thisAnt, int dir, IAntInfo attacker, int damage) {
        System.out.println("ID: " + thisAnt.antID() + " onAttacked: " + damage + " damage");
    }

    @Override
    public void onDeath(IAntInfo thisAnt) {
        collectiveMemory.removeAnt(thisAnt);
        System.out.println("ID: " + thisAnt.antID() + " onDeath");
    }

    @Override
    public void onLayEgg(IAntInfo thisAnt, List<EAntType> types, IEgg egg) {
        int index = breeding.getBreedingAction(collectiveMemory.getAnts(), turn);
        EAntType type = types.get(index);
        System.out.println("ID: " + thisAnt.antID() + " onLayEgg: " + type);
        egg.set(type, this);
    }

    @Override
    public void onHatch(IAntInfo thisAnt, ILocationInfo thisLocation, int worldSizeX, int worldSizeY) {
        collectiveMemory.addAnt(thisAnt);
        if (thisAnt.getAntType().getTypeName().equals("Queen")) {
            collectiveMemory.setQueenSpawn(thisLocation);
        }
        System.out.println("ID: " + thisAnt.antID() + " onHatch");
        this.worldSizeX = worldSizeX;
        this.worldSizeY = worldSizeY;
    }

}
