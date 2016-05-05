package a3.ai;

import aiantwars.EAction;
import aiantwars.EAntType;
import aiantwars.IAntAI;
import aiantwars.IAntInfo;
import aiantwars.IEgg;
import aiantwars.ILocationInfo;
import a3.ant.CarrierLogic;
import a3.ant.QueenLogic;
import a3.ant.ScoutLogic;
import a3.ant.WarriorLogic;
import a3.behaviour.Breed;
import java.util.List;
import a3.memory.CollectiveMemory;
import a3.utility.Debug;
import static a3.utility.Debug.println;

public class JT_Destroyer implements IAntAI {

    private final CollectiveMemory cm = new CollectiveMemory();
    private final Breed breeding = new Breed();
    private int turn;

    @Override
    public EAction chooseAction(IAntInfo thisAnt, ILocationInfo thisLocation, List<ILocationInfo> visibleLocations, List<EAction> possibleActions) {
        Debug.isDebug = false;

        // add tiles to collective memory
        cm.addTiles(visibleLocations);
        cm.addTile(thisLocation);

        EAction action = null;

        // ALL
        if (possibleActions.contains(EAction.EatFood) && thisAnt.getHitPoints() < 10) {
            action = EAction.EatFood;

            // CARRIER
        } else if (thisAnt.getAntType().equals(EAntType.CARRIER)) {
            CarrierLogic carrierLogic = new CarrierLogic(thisAnt, thisLocation, possibleActions, cm);
            action = carrierLogic.getAction();
            println("Carrier: chose action: " + action.toString());

            // QUEEN
        } else if (thisAnt.getAntType().equals(EAntType.QUEEN)) {
            QueenLogic queenLogic = new QueenLogic(cm, thisAnt, thisLocation, possibleActions, visibleLocations, turn);
            action = queenLogic.getAction();
            println("Queen: chose action: " + action.toString());

            // SCOUT
        } else if (thisAnt.getAntType().equals(EAntType.SCOUT)) {
            ScoutLogic scoutLogic = new ScoutLogic(thisAnt, thisLocation, possibleActions, cm);
            action = scoutLogic.getAction();
//            action = getRandomAction(possibleActions);
            println("Scout: chose action: " + action.toString());
            //WARRIOR
        } else if (thisAnt.getAntType().equals(EAntType.WARRIOR)) {
//            if (possibleActions.contains(EAction.Attack) && visibleLocations.get(0).getAnt().getTeamInfo().getTeamID() != thisAnt.getTeamInfo().getTeamID()) {
//                action = EAction.Attack;
//            } else if (possibleActions.contains(EAction.PickUpFood) && thisAnt.getFoodLoad() < 2) {
//                action = EAction.PickUpFood;
//            } else {
//                action = getRandomAction(possibleActions);
//            }
            WarriorLogic warriorLogic = new WarriorLogic(thisAnt, thisLocation, possibleActions, visibleLocations, cm);
            action = warriorLogic.getAction();
            println("Warrior: chose action: " + action.toString());
        }
        return action;
    }

    @Override
    public void onStartTurn(IAntInfo thisAnt, int turn) {
        this.turn = turn;
        println("System: ID: " + thisAnt.antID() + " onStartTurn(" + turn + ")");
    }

    @Override
    public void onAttacked(IAntInfo thisAnt, int dir, IAntInfo attacker, int damage) {
        println("System: ID: " + thisAnt.antID() + " onAttacked: " + damage + " damage");
    }

    @Override
    public void onDeath(IAntInfo thisAnt) {
        cm.removeAnt(thisAnt);
        println("System: ID: " + thisAnt.antID() + " onDeath");
    }

    @Override
    public void onLayEgg(IAntInfo thisAnt, List<EAntType> types, IEgg egg) {
        int index = breeding.getBreedingAction(cm.getAnts(), turn);
        EAntType type = types.get(index);
        println("System: ID: " + thisAnt.antID() + " onLayEgg: " + type);

        egg.set(type, this);
    }

    @Override
    public void onHatch(IAntInfo thisAnt, ILocationInfo thisLocation, int worldSizeX, int worldSizeY) {
        cm.addAnt(thisAnt);
        if (thisAnt.getAntType().getTypeName().equals("Queen")) {
            cm.setQueenSpawn(thisLocation);
        }
        println("System: ID: " + thisAnt.antID() + " onHatch");

        cm.saveWorldSizeX(worldSizeX);
        cm.saveWorldSizeY(worldSizeY);
    }

    @Override
    public void onStartMatch(int worldSizeX, int worldSizeY) {
        cm.clearTiles();
    }

    @Override
    public void onStartRound(int round) {
    }

    @Override
    public void onEndRound(int yourMajor, int yourMinor, int enemyMajor, int enemyMinor) {
    }

    @Override
    public void onEndMatch(int yourScore, int yourWins, int enemyScore, int enemyWins) {
    }

}
