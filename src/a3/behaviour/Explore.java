package a3.behaviour;

import a3.algorithm.ShortestPath;
import a3.memory.CollectiveMemory;
import a3.memory.model.Position;
import a3.memory.model.Tile;
import a3.utility.Calc;
import static a3.utility.Calc.distanceFromAtoB;
import static a3.utility.Calc.getMovementDirection;
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

    public EAction getAction() {
        SortedSet<Tile> possibleLocations = new TreeSet(Tile.ExplorationPropensityComparator);
        double unexploredFactor = 0.1;

        // look through all tiles in collectiveMemory 
        for (Map.Entry<Position, Tile> entry : cm.getTiles().entrySet()) {
            Tile tile = entry.getValue();

           // System.out.println("Considering: " + tile);

            if (!tile.isFilled() && !tile.isRock() && (tile.getX() != thisLocation.getX() || tile.getY() != thisLocation.getY())) {
                    // save distance toScout for later use in PropensityFactorComparator
                    tile.setDistanceToScout(distanceFromAtoB(tile, thisLocation));

                    // frequency * distance to queenspawn * distance to scout 
                    double explorationPropensity = tile.getFrequency() * distanceFromAtoB(thisLocation, cm.getQueenSpawn()) * tile.getDistanceToScout();

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
                    System.out.println("Possible location: " + tile);
                    possibleLocations.add(tile);
            }
        }

        // find tile with lowest explorationPropensity-factor
        if (possibleLocations.size() > 0) {
            ShortestPath sp = new ShortestPath(thisAnt, thisLocation, possibleLocations.first(), cm);
            List<ILocationInfo> shortestPath = sp.getShortestPath();

            int movementDirection = getMovementDirection(thisLocation, shortestPath.get(0));
            println("chosenLocation: " + possibleLocations.first());

            int chosenDirection = possibleLocations.first().getDirection();
            println("chosenDirection: " + movementDirection);
            return Calc.getMovementAction(thisAnt.getDirection(), chosenDirection, false);

        } else {
            return EAction.Pass;
        }
    }
}
