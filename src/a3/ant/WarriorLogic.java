package a3.ant;

import a3.behaviour.Fight;
import a3.memory.CollectiveMemory;
import a3.memory.model.Tile;
import static a3.utility.Action.getRandomAction;
import static a3.utility.Calc.getMovementAction;
import static a3.utility.Calc.getMovementDirection;
import static a3.utility.Debug.println;
import aiantwars.EAction;
import aiantwars.IAntInfo;
import aiantwars.ILocationInfo;
import java.util.List;

/**
 * WIP
 *
 * @author Tobias Jacobsen
 */
public class WarriorLogic {

    private final IAntInfo thisAnt;
    private final ILocationInfo thisLocation;
    private final List<EAction> possibleActions;
    private final List<ILocationInfo> visibleLocations;
    private final CollectiveMemory cm;

    public WarriorLogic(IAntInfo thisAnt, ILocationInfo thisLocation, List<EAction> possibleActions, List<ILocationInfo> visibleLocations, CollectiveMemory cm) {
        this.thisAnt = thisAnt;
        this.thisLocation = thisLocation;
        this.possibleActions = possibleActions;
        this.visibleLocations = visibleLocations;
        this.cm = cm;
    }

    public EAction getAction() {
        println("Scout: Current AP: " + thisAnt.getActionPoints() + "| Available actions: " + possibleActions.toString());
        EAction action = null;

        // replenish food storage
        if (possibleActions.contains(EAction.PickUpFood) && thisAnt.getFoodLoad() < 3) {
            return EAction.PickUpFood;
        }

        // Attack enemy if possible    
        if (possibleActions.contains(EAction.Attack) && visibleLocations.get(0).getAnt().getTeamInfo().getTeamID() != thisAnt.getTeamInfo().getTeamID()) {
            return EAction.Attack;
        }

        // Check if enemy ant is in either north, south, east or west position from thisAnt and turn towards this that position
        Tile enemyTile = checkSurroundingTilesForEnemy();
        if (enemyTile != null) {
            return getMovementAction(thisAnt.getDirection(), getMovementDirection(thisAnt, enemyTile), false);
        }

        // If number of warrior ants correct look for enemy ants
        int warriorCount = 0;

        List<IAntInfo> ants = cm.getAnts();
        for (IAntInfo ant : ants) {
            switch (ant.getAntType().getTypeName()) {
                case "Warrier":
                    warriorCount++;
                    break;
                default:
                    break;
            }
        }

        if (warriorCount >= 2) {
            Fight fight = new Fight(thisAnt, thisLocation, cm, possibleActions);
            return fight.getAction();
        }

        return checkAction(getRandomAction(possibleActions));
    }

    private Tile checkSurroundingTilesForEnemy() {
        Tile tileNorth = cm.getTile(thisLocation.getX() + "," + (thisLocation.getY() + 1)); // north
        Tile tileSouth = cm.getTile(thisLocation.getX() + "," + (thisLocation.getY() - 1)); // south
        Tile tileWest = cm.getTile((thisLocation.getX() - 1) + "," + thisLocation.getY());  // west
        Tile tileEast = cm.getTile((thisLocation.getX() + 1) + "," + thisLocation.getY()); // east

        if (tileNorth != null && tileNorth.getAnt() != null) {
            if (tileNorth.getAnt().getTeamInfo().getTeamID() != thisAnt.getTeamInfo().getTeamID()) {
                return tileNorth;
            }
        } else if (tileSouth != null && tileSouth.getAnt() != null) {
            if (tileSouth.getAnt().getTeamInfo().getTeamID() != thisAnt.getTeamInfo().getTeamID()) {
                return tileSouth;
            }
        } else if (tileWest != null && tileWest.getAnt() != null) {
            if (tileWest.getAnt().getTeamInfo().getTeamID() != thisAnt.getTeamInfo().getTeamID()) {
                return tileWest;
            }
        } else if (tileEast != null && tileEast.getAnt() != null) {
            if (tileEast.getAnt().getTeamInfo().getTeamID() != thisAnt.getTeamInfo().getTeamID()) {
                return tileEast;
            }
        }
        return null;
    }

    private EAction checkAction(EAction action) {
        if (action == EAction.EatFood) {
            action = getRandomAction(possibleActions);
            checkAction(action);
        }
        return action;
    }

}
