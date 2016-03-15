package ai;

import aiantwars.EAction;
import aiantwars.EAntType;
import aiantwars.IAntAI;
import aiantwars.IAntInfo;
import aiantwars.IEgg;
import aiantwars.ILocationInfo;
import java.util.List;
import java.util.Map;
import java.util.Random;
import memory.CollectiveMemory;
import memory.Position;
import memory.Tile;

public class Main implements IAntAI {

    private final Random rnd = new Random();
    private final CollectiveMemory collectiveMemory = CollectiveMemory.getInstance();
    private int worldSizeX;
    private int worldSizeY;

    @Override
    public EAction chooseAction(IAntInfo thisAnt, ILocationInfo thisLocation, List<ILocationInfo> visibleLocations, List<EAction> possibleActions) {
        collectiveMemory.addVisibleLocations(visibleLocations);

        for (Map.Entry pair : collectiveMemory.getMemory().entrySet()) {
            Position pos = (Position) pair.getKey();
            Tile tile = (Tile) pair.getValue();

            System.out.println("Map position (" + pos.getxPos() + ", " + pos.getyPos() + ")" + ", FoodCount: " + tile.getFoodCount() + ", Ant: Not Supported"
                    + ", IsFilled: " + tile.isFilled() + ", IsRock: " + tile.isRock());
        }

        System.out.println("visibleLocations: ");
        for (ILocationInfo loc : visibleLocations) {
            System.out.println("Coord: " + loc.getX() + "," + loc.getY());
            System.out.println("Ant?" + loc.getAnt());
            System.out.println("isFilled?" + loc.isFilled());
            System.out.println("isRock?" + loc.isRock());
        }

        EAction action = null;
        if (possibleActions.contains(EAction.EatFood) && thisAnt.getHitPoints() < 10) {
            action = EAction.EatFood;
        } else if (possibleActions.contains(EAction.LayEgg)) {
            action = EAction.LayEgg;
        } else if (possibleActions.contains(EAction.Attack) && visibleLocations.get(0).getAnt().getTeamInfo().getTeamID() != thisAnt.getTeamInfo().getTeamID()) {
            action = EAction.Attack;
        } else if (possibleActions.contains(EAction.PickUpFood) && thisAnt.getFoodLoad() < 15) {
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
        System.out.println("ID: " + thisAnt.antID() + " onStartTurn(" + turn + ")");
    }

    @Override
    public void onAttacked(IAntInfo thisAnt, int dir, IAntInfo attacker, int damage) {
        System.out.println("ID: " + thisAnt.antID() + " onAttacked: " + damage + " damage");
    }

    @Override
    public void onDeath(IAntInfo thisAnt) {
        System.out.println("ID: " + thisAnt.antID() + " onDeath");
    }

    @Override
    public void onLayEgg(IAntInfo thisAnt, List<EAntType> types, IEgg egg) {
        EAntType type = types.get(rnd.nextInt(types.size()));
        System.out.println("ID: " + thisAnt.antID() + " onLayEgg: " + type);
        egg.set(type, this);
    }

    @Override
    public void onHatch(IAntInfo thisAnt, ILocationInfo thisLocation, int worldSizeX, int worldSizeY) {
        System.out.println("ID: " + thisAnt.antID() + " onHatch");
        this.worldSizeX = worldSizeX;
        this.worldSizeY = worldSizeY;
    }

}
