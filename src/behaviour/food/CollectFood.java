package behaviour.food;

import aiantwars.EAction;
import aiantwars.IAntInfo;
import aiantwars.ILocationInfo;
import behaviour.LocationExtends;
import comparator.FoodCostComparator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import utility.Calc;

/**
 * Todo: 1. Use Tile.class instead of LocationExtends.class (redundant)
 *
 * 2. Use collectiveMemory instead of visibleLocations.
 *
 * @author Tobias
 */
public class CollectFood {

    private final IAntInfo thisAnt;
    private final ILocationInfo thisLocation;
    private final List<ILocationInfo> visibleLocations;

    public CollectFood(IAntInfo thisAnt, ILocationInfo thisLocation, List<ILocationInfo> visibleLocations) {
        this.thisAnt = thisAnt;
        this.thisLocation = thisLocation;
        this.visibleLocations = visibleLocations;
    }

    public EAction getEAction() {
        ILocationInfo currentLocation = thisLocation;

        List<LocationExtends> possibleLocations = new ArrayList();
        for (ILocationInfo location : visibleLocations) {

            if (IsLocationFree(location) && (location.getX() == currentLocation.getX() && location.getY() == currentLocation.getY() + 1)) {
                // get north
                LocationExtends locationNorth = new LocationExtends(location.getX(), location.getY(), thisAnt, 0, location.getFoodCount());
                possibleLocations.add(locationNorth);
            }
            if (IsLocationFree(location) && (location.getX() == currentLocation.getX() && location.getY() == currentLocation.getY() - 1)) {
                // get south
                LocationExtends locationSouth = new LocationExtends(location.getX(), location.getY(), thisAnt, 2, location.getFoodCount());
                possibleLocations.add(locationSouth);
            }
            if (IsLocationFree(location) && (location.getX() == currentLocation.getX() - 1 && location.getY() == currentLocation.getY())) {
                // get west
                LocationExtends locationWest = new LocationExtends(location.getX(), location.getY(), thisAnt, 3, location.getFoodCount());
                possibleLocations.add(locationWest);
            }
            if (IsLocationFree(location) && (location.getX() == currentLocation.getX() + 1 && location.getY() == currentLocation.getY())) {
                // get east
                LocationExtends locationEast = new LocationExtends(location.getX(), location.getY(), thisAnt, 1, location.getFoodCount());
                possibleLocations.add(locationEast);
            }
        }
        Collections.sort(possibleLocations, new FoodCostComparator());

        LocationExtends bestLocation = new LocationExtends(possibleLocations.get(0).getX(), possibleLocations.get(0).getY(), thisAnt, possibleLocations.get(0).getDirection(), possibleLocations.get(0).getFoodCount()); // location with lowest foodCost

        int chosenDirection = bestLocation.getDirection();
        return Calc.getMovementAction(thisAnt.getDirection(), chosenDirection);
    }

    private boolean IsLocationFree(ILocationInfo location) {
        return location.getAnt() == null && !location.isFilled() && !location.isRock();
    }
}
