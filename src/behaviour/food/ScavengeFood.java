package behaviour.food;

import aiantwars.EAction;
import aiantwars.IAntInfo;
import aiantwars.ILocationInfo;
import comparator.FoodCostComparator;
import java.util.SortedSet;
import java.util.TreeSet;
import memory.CollectiveMemory;
import memory.Position;
import memory.Tile;
import utility.Calc;
import static utility.Debug.println;

/**
 *
 * @author Tobias Jacobsen
 */
public class ScavengeFood {

    private final CollectiveMemory cm = CollectiveMemory.getInstance();
    private final IAntInfo thisAnt;

    public ScavengeFood(IAntInfo thisAnt) {
        this.thisAnt = thisAnt;
    }

    public EAction getEAction() {
        ILocationInfo currentLocation = thisAnt.getLocation();
        SortedSet<Tile> possibleLocations = new TreeSet(new FoodCostComparator());

        int xOffset = currentLocation.getX();
        int yOffset = currentLocation.getY() + 1;

        // north
        if (checkPosition(xOffset, yOffset)) {
            Tile tile = cm.getTile(xOffset + "," + yOffset);
            tile.setDirection(0);
            possibleLocations.add(tile);
        }

        // south
        yOffset = currentLocation.getY() - 1;
        if (checkPosition(xOffset, yOffset)) {
            Tile tile = cm.getTile(xOffset + "," + yOffset);
            tile.setDirection(2);
            possibleLocations.add(tile);
        }

        // west
        xOffset = currentLocation.getX() - 1;
        yOffset = currentLocation.getY();
        if (checkPosition(xOffset, yOffset)) {
            Tile tile = cm.getTile(xOffset + "," + yOffset);
            tile.setDirection(3);
            possibleLocations.add(tile);
        }

        // east
        xOffset = currentLocation.getX() + 1;
        if (checkPosition(xOffset, yOffset)) {
            Tile tile = cm.getTile(xOffset + "," + yOffset);
            tile.setDirection(1);
            possibleLocations.add(tile);
        }

        println("System: possibleLocations size: " + possibleLocations.size());

        if (possibleLocations.size() > 0) {
            for (Tile t : possibleLocations) {
                println("System: " + t.toString());
            }
            println("System: ---");

            println("C: firstLocation: " + possibleLocations.first().toString());

            int chosenDirection = possibleLocations.first().getDirection();
            return Calc.getMovementAction(thisAnt.getDirection(), chosenDirection);

        } else {
            return EAction.Pass;

        }

    }

    private boolean checkPosition(int x, int y) {
        Position position = new Position(x, y);
        if (cm.getTiles().containsKey(position)) {
            Tile tile = cm.getTile(position);
            return tile.getAnt() == null && !tile.isFilled() && !tile.isRock();
        }
        return false;
    }
}
