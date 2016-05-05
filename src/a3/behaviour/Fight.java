package a3.behaviour;

import a3.algorithm.ShortestPath;
import a3.memory.CollectiveMemory;
import a3.memory.model.Position;
import a3.memory.model.Tile;
import a3.utility.Calc;
import static a3.utility.Debug.println;
import aiantwars.EAction;
import aiantwars.IAntInfo;
import aiantwars.ILocationInfo;
import aiantwars.impl.Location;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Jonas Rafn
 */
public class Fight {

    private final IAntInfo thisAnt;
    private final ILocationInfo thisLocation;
    private final CollectiveMemory cm;
    private final Map<Position, Tile> memory;
    private final List<EAction> possibleActions;

    public Fight(IAntInfo thisAntInfo, ILocationInfo thisLocation, CollectiveMemory cm, List<EAction> possibleActions) {
        this.thisAnt = thisAntInfo;
        this.thisLocation = thisLocation;
        this.possibleActions = possibleActions;
        this.cm = cm;
        this.memory = cm.getTiles();
    }

    public EAction getAction() {
        EAction action = EAction.Pass;
        int scoutCount = 0;
        int carrierCount = 0;
        int warriorCount = 0;

        List<IAntInfo> ants = cm.getAnts();
        for (IAntInfo ant : ants) {
            switch (ant.getAntType().getTypeName()) {
                case "Scout":
                    scoutCount++;
                    break;
                case "Carrier":
                    carrierCount++;
                    break;
                case "Warrier":
                    warriorCount++;
                    break;
                default:
                    break;
            }
        }

        if (warriorCount >= 2) {
            //Look for enemy queen or enemy ants
            println("queen: is low on food, looking for deposit location");
            ILocationInfo enemyLocation = findEnemyLocation();
            if (enemyLocation != null) {
                ShortestPath path = new ShortestPath(thisAnt, thisLocation, enemyLocation, cm);
                int movementDirection = Calc.getMovementDirection(thisLocation, path.getShortestPath().get(0));
                EAction movementAction = Calc.getMovementAction(thisAnt.getDirection(), movementDirection, false);
                if (possibleActions.contains(movementAction)) {
                    action = movementAction;
                }
            }
        }

        return action;
    }

    /**
     * Looks through collectivememory looking for enemy ants, returns the one
     * with shortest distance
     *
     * @return ILocation
     */
    private ILocationInfo findEnemyLocation() {
        Tile enemyLocation = new Tile(0, 0);
        boolean foundEnemy = false;
        int distance = Integer.MAX_VALUE;
        ShortestPath path;
        List<ILocationInfo> pathList;
        for (Map.Entry<Position, Tile> entry : memory.entrySet()) {
            if (entry.getValue().getAnt().getTeamInfo().getTeamID() != thisAnt.getTeamInfo().getTeamID()) {
                path = new ShortestPath(thisAnt, thisLocation, new Location(entry.getValue().getX(), entry.getValue().getY()), cm);
                pathList = path.getShortestPath();
                if (pathList.size() < distance) {
                    foundEnemy = true;
                    enemyLocation = new Tile(entry.getValue().getX(), entry.getValue().getY());
                }
            }
        }

        if (!foundEnemy) {
            return null;
        } else {
            ILocationInfo loc = new Location(enemyLocation.getX(), enemyLocation.getY());
            println("FindEnemyLocation: returned location (" + loc.getX() + ", " + loc.getY() + ")");
            return loc;
        }
    }

}
