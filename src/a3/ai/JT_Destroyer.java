package a3.ai;

import aiantwars.EAction;
import aiantwars.EAntType;
import aiantwars.IAntAI;
import aiantwars.IAntInfo;
import aiantwars.IEgg;
import aiantwars.ILocationInfo;
import a3.logic.CarrierLogic;
import a3.logic.QueenLogic;
import a3.logic.ScoutLogic;
import a3.logic.WarriorLogic;
import a3.behaviour.Breed;
import java.util.List;
import a3.memory.CollectiveMemory;
import static a3.utility.Action.getRandomAction;
import a3.utility.Debug;
import static a3.utility.Debug.println;

public class JT_Destroyer implements IAntAI {

    private final CollectiveMemory cm = new CollectiveMemory();
    private final Breed breeding = new Breed();
    private int turn;

    @Override
    public EAction chooseAction(IAntInfo thisAnt, ILocationInfo thisLocation, List<ILocationInfo> visibleLocations, List<EAction> possibleActions) {
        Debug.isDebug = false;

        // update collective memory
        cm.addTiles(visibleLocations);
        cm.addTile(thisLocation);

        EAction action = null;

        // ALL
        if (possibleActions.contains(EAction.EatFood) && thisAnt.getHitPoints() < 10) {
            return EAction.EatFood;

            // CARRIER
        } else if (thisAnt.getAntType().equals(EAntType.CARRIER)) {
            CarrierLogic carrierLogic = new CarrierLogic(thisAnt, thisLocation, possibleActions, cm);
            return carrierLogic.getAction();

            // QUEEN
        } else if (thisAnt.getAntType().equals(EAntType.QUEEN)) {
            QueenLogic queenLogic = new QueenLogic(cm, thisAnt, thisLocation, possibleActions, visibleLocations, turn);
            return queenLogic.getAction();

            // SCOUT
        } else if (thisAnt.getAntType().equals(EAntType.SCOUT)) {
            ScoutLogic scoutLogic = new ScoutLogic(thisAnt, thisLocation, possibleActions, cm);
            return scoutLogic.getAction();
            //WARRIOR
        } else if (thisAnt.getAntType().equals(EAntType.WARRIOR)) {
            WarriorLogic warriorLogic = new WarriorLogic(thisAnt, thisLocation, possibleActions, visibleLocations, cm);
            return warriorLogic.getAction();
        }
        return getRandomAction(possibleActions);
    }

    @Override
    public void onStartTurn(IAntInfo thisAnt, int turn) {
        println("System: ID: " + thisAnt.antID() + " onStartTurn(" + turn + ")");
        this.turn = turn;
        cm.setCurrentTurn(turn);
    }

    @Override
    public void onAttacked(IAntInfo thisAnt, int dir, IAntInfo attacker, int damage) {
        println("System: ID: " + thisAnt.antID() + " onAttacked: " + damage + " damage");
    }

    @Override
    public void onDeath(IAntInfo thisAnt) {
        println("System: ID: " + thisAnt.antID() + " onDeath");
        cm.removeAnt(thisAnt);
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
        cm.setTeamID(thisAnt.getTeamInfo().getTeamID());
    }

    @Override
    public void onStartMatch(int worldSizeX, int worldSizeY) {
    }

    @Override
    public void onStartRound(int round) {
        cm.clearMemory();
    }

    @Override
    public void onEndRound(int yourMajor, int yourMinor, int enemyMajor, int enemyMinor) {
    }

    @Override
    public void onEndMatch(int yourScore, int yourWins, int enemyScore, int enemyWins) {
    }
}
