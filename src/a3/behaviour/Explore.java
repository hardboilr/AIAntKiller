package a3.behaviour;

import a3.algorithm.ShortestPath;
import a3.memory.CollectiveMemory;
import a3.memory.model.Position;
import a3.memory.model.Tile;
import a3.utility.Calc;
import static a3.utility.Calc.distanceFromAtoB;
import static a3.utility.Calc.getMovementDirection;
import static a3.utility.Debug.print;
import static a3.utility.Debug.println;
import aiantwars.EAction;
import aiantwars.IAntInfo;
import aiantwars.ILocationInfo;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @author Tobias Jacobsen
 */
public class Explore {

    private final IAntInfo thisAnt;
    private final ILocationInfo thisLocation;
    private final CollectiveMemory cm;

    public Explore(IAntInfo thisAnt, ILocationInfo thisLocation, CollectiveMemory cm) {
        this.thisAnt = thisAnt;
        this.thisLocation = thisLocation;
        this.cm = cm;
    }

    /**
     * Go's through all tiles in collective memory. Calculates distance to this
     * location and calculates explorationPropensity on each tile.
     * ExplorationPropensity (double value) is a factor of frequency, distance
     * to queen spawn, distance to scout and the tile's potentially unexplored
     * neighbors. The tile with the lowest value is the most attractive tile for
     * the scout to go to.
     *
     * @return
     */
    public EAction getAction() {
        SortedSet<Tile> possibleLocations = new TreeSet(Tile.ExplorationPropensityComparator);
        double unexploredFactor = 0.1;

        // look through all tiles in collectiveMemory 
        for (Map.Entry<Position, Tile> entry : cm.getTiles().entrySet()) {
            Tile tile = entry.getValue();

            // if tile is not rock or filled and not thisLocation
            if (!tile.isFilled() && !tile.isRock() && (tile.getX() != thisLocation.getX() || tile.getY() != thisLocation.getY())) {
                // save distance toScout for later use in PropensityFactorComparator
                tile.setDistanceToScout(distanceFromAtoB(tile, thisLocation));

                // frequency * distance to queenspawn * distance to scout 
                double explorationPropensity = (tile.getFrequency() + 1) * (distanceFromAtoB(thisLocation, cm.getQueenSpawn()) + 1) * (tile.getDistanceToScout() + 1);

                // if tile has unexplored neighbour, multiply by 0.1
                if (cm.getTile(tile.getX() + "," + (tile.getY() + 1)) == null) { // north
                    explorationPropensity = explorationPropensity * unexploredFactor;
                } else if (cm.getTile(tile.getX() + "," + (tile.getY() - 1)) == null) { // south
                    explorationPropensity = explorationPropensity * unexploredFactor;
                } else if (cm.getTile((tile.getX() - 1) + "," + tile.getY()) == null) { // west
                    explorationPropensity = explorationPropensity * unexploredFactor;
                } else if (cm.getTile((tile.getX() + 1) + "," + tile.getY()) == null) { // east
                    explorationPropensity = explorationPropensity * unexploredFactor;
                }

                // save explorationPropensity-factor to tile
                tile.setExplorationPropensity(explorationPropensity);
                possibleLocations.add(tile);
            }
        }

        // find tile with lowest explorationPropensity-factor
        if (possibleLocations.size() > 0) {
            for (Tile t : possibleLocations) {
                println("Scout: possible locations: " + t);
            }

            ShortestPath sp = new ShortestPath(thisAnt, thisLocation, possibleLocations.first(), cm);
            List<ILocationInfo> shortestPath = sp.getShortestPath();

            println("shortestPath ->");
            for (ILocationInfo l : shortestPath) {
                print("(" + l.getX() + "," + l.getY() + "),");
            }

            int movementDirection = getMovementDirection(thisLocation, shortestPath.get(0));

            println("chosenLocation: " + possibleLocations.first());
            println("chosenDirection: " + movementDirection);

            return Calc.getMovementAction(thisAnt.getDirection(), movementDirection, false);

        } else {
            return EAction.Pass;
        }
    }
}
