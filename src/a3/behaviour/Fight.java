package a3.behaviour;

import a3.algorithm.ShortestPath;
import a3.memory.CollectiveMemory;
import a3.memory.model.Position;
import a3.memory.model.Tile;
import a3.utility.Calc;
import static a3.utility.Debug.println;
import aiantwars.EAction;
import aiantwars.EAntType;
import aiantwars.IAntInfo;
import aiantwars.ILocationInfo;
import aiantwars.impl.Location;
import java.util.List;
import java.util.Map;
import static a3.utility.Action.getRandomAction;

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
        ILocationInfo enemyLocation = findEnemyLocation();
        if (enemyLocation != null) {
            ShortestPath path = new ShortestPath(thisAnt, thisLocation, enemyLocation, cm);

            List<ILocationInfo> shortestPath = path.getShortestPath();
            if (shortestPath != null) {
                int movementDirection = Calc.getMovementDirection(thisLocation, shortestPath.get(0));
                EAction movementAction = Calc.getMovementAction(thisAnt.getDirection(), movementDirection, false);
                if (possibleActions.contains(movementAction)) {
                    return movementAction;
                }
            }
        }

        return getRandomAction(possibleActions);
    }

    /**
     * Looks through collective memory looking for enemy ants, returns the one
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
                if (pathList != null) {
                    if (pathList.size() < distance) {
                        foundEnemy = true;
                        enemyLocation = new Tile(entry.getValue().getX(), entry.getValue().getY());
                    }
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
