package behaviour.food;

import aiantwars.EAction;
import aiantwars.IAntInfo;
import aiantwars.ILocationInfo;
import behaviour.LocationExtends;
import comparator.FoodCostComparator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import memory.Tile;
import utility.Calc;

/**
 * 
 * 2. Use collectiveMemory instead of visibleLocations.
 *
 * @author Tobias
 */
public class ScavengeFood {

    private final IAntInfo thisAnt;
    private final ILocationInfo thisLocation;
    private final List<ILocationInfo> visibleLocations;

    public ScavengeFood(IAntInfo thisAnt, ILocationInfo thisLocation, List<ILocationInfo> visibleLocations) {
        this.thisAnt = thisAnt;
        this.thisLocation = thisLocation;
        this.visibleLocations = visibleLocations;
    }

    public EAction getEAction() {
        ILocationInfo currentLocation = thisLocation;

        List<Tile> possibleLocations = new ArrayList();
        for (ILocationInfo location : visibleLocations) {

            if (IsLocationFree(location) && (location.getX() == currentLocation.getX() && location.getY() == currentLocation.getY() + 1)) {
                // get north
                Tile locNorth = new Tile(location.getX(), location.getY(), thisAnt, 0, location.getFoodCount());
                possibleLocations.add(locNorth);
            }
            if (IsLocationFree(location) && (location.getX() == currentLocation.getX() && location.getY() == currentLocation.getY() - 1)) {
                // get south
                Tile locSouth = new Tile(location.getX(), location.getY(), thisAnt, 2, location.getFoodCount());
                possibleLocations.add(locSouth);
            }
            if (IsLocationFree(location) && (location.getX() == currentLocation.getX() - 1 && location.getY() == currentLocation.getY())) {
                // get west
                Tile locWest = new Tile(location.getX(), location.getY(), thisAnt, 3, location.getFoodCount());
                possibleLocations.add(locWest);

            }
            if (IsLocationFree(location) && (location.getX() == currentLocation.getX() + 1 && location.getY() == currentLocation.getY())) {
                // get east
                Tile locEast = new Tile(location.getX(), location.getY(), thisAnt, 1, location.getFoodCount());
                possibleLocations.add(locEast);
            }
        }
        Collections.sort(possibleLocations, new FoodCostComparator());

        Tile bestTile = new Tile(possibleLocations.get(0).getX(), possibleLocations.get(0).getY(), thisAnt, possibleLocations.get(0).getDirection(), possibleLocations.get(0).getFoodCount()); // location with lowest foodCost
        
        int chosenDirection = bestTile.getDirection();
        return Calc.getMovementAction(thisAnt.getDirection(), chosenDirection);
    }

    private boolean IsLocationFree(ILocationInfo location) {
        return location.getAnt() == null && !location.isFilled() && !location.isRock();
    }
}
