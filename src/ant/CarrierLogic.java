package ant;

import aiantwars.EAction;
import aiantwars.EAntType;
import aiantwars.IAntInfo;
import aiantwars.ILocationInfo;
import aiantwars.impl.Location;
import algorithm.ShortestPath;
import behaviour.food.ScavengeFood;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import memory.CollectiveMemory;
import memory.Position;
import memory.Tile;
import memory.TileType;
import static utility.Action.getRandomAction;
import utility.Calc;
import static utility.Debug.println;

/**
 *
 * @author Tobias Jacobsen
 */
public class CarrierLogic {

    private final CollectiveMemory cm = CollectiveMemory.getInstance();
    private final IAntInfo thisAnt;
    private final ILocationInfo thisLocation;
    private final List<EAction> possibleActions;
    private final int maxFoodLoad;
    private final int minFoodLoad;

    public CarrierLogic(IAntInfo thisAnt, ILocationInfo thisLocation, List<EAction> possibleActions) {
        this.thisAnt = thisAnt;
        this.thisLocation = thisLocation;
        this.possibleActions = possibleActions;
        maxFoodLoad = calcMaxFoodLoad();
        minFoodLoad = 3;

    }

    public EAction getAction() {
        println("foodLoad: " + thisAnt.getFoodLoad() + ", maxFoodLoad: " + maxFoodLoad);

        if (isDeposit() && possibleActions.contains(EAction.DropFood) && thisAnt.getFoodLoad() > minFoodLoad) {
            // a. when ant have food and current position is a deposit, then drop food
            println("Picked a");
            return EAction.DropFood;
        } else if (isDeposit() && !possibleActions.contains(EAction.DropFood) && thisAnt.getFoodLoad() > minFoodLoad) {
            // b. when ant have food and current position is a deposit, but ant cannot drop food, then pass turn
            println("Picked b");
            return EAction.Pass;
        } else if (possibleActions.contains(EAction.PickUpFood) && thisAnt.getFoodLoad() < maxFoodLoad) {
            // c. when ant is within max food load threshold, then pickup food
            println("Picked c");
            return EAction.PickUpFood;
        } else if (thisAnt.getFoodLoad() >= maxFoodLoad) {
            // d. when ant max food load has been reached, then return to deposit location with lowest food count
            println("Picked d");
            ILocationInfo depositLocation = findDepositLocation();
            if (depositLocation != null) {
                ShortestPath path = new ShortestPath(thisAnt, thisLocation, findDepositLocation());
                int movementDirection = Calc.getMovementDirection(thisLocation, path.getShortestPath().get(0));
                EAction movementAction = Calc.getMovementAction(thisAnt.getDirection(), movementDirection);
                if (possibleActions.contains(movementAction)) {
                    return movementAction;
                }
            }
        } else if (thisLocation.getFoodCount() == 0) {
            // e. when current position has 0 food, then scavenge food
            println("picked e");
            ScavengeFood scavengeFood = new ScavengeFood(thisAnt);
            
        }
        println("Picked random");
        return getRandomAction(possibleActions);
    }

    /**
     * Looks through all deposit locations and returns the location with lowest
     * food count
     *
     * @return
     */
    private ILocationInfo findDepositLocation() {
        Tile deposit = new Tile(0, 0);
        deposit.setFoodCount(Integer.MAX_VALUE);
        Map<Position, Tile> memory = cm.getTiles();
        boolean foundDeposit = false;

        for (Map.Entry<Position, Tile> entry : memory.entrySet()) {
            if (entry.getValue().getType().equals(TileType.DEPOSIT)) {
                if (entry.getValue().getFoodCount() < deposit.getFoodCount()) {
                    foundDeposit = true;
                    deposit = new Tile(entry.getValue().getX(), entry.getValue().getY());
                    deposit.setFoodCount(entry.getValue().getFoodCount());
                }
            }
        }

        if (!foundDeposit) {
            return null;
        } else {
            ILocationInfo loc = new Location(deposit.getX(), deposit.getY());
            return loc;
        }
    }

    /**
     * Looks at thisLocation and returns true if thisLocation is of type:
     * Deposit
     *
     * @return
     */
    private boolean isDeposit() {
        Map<Position, Tile> memory = cm.getTiles();
        Position pos = new Position(thisLocation.getX(), thisLocation.getY());
        if (memory.containsKey(pos)) {
            Tile tile = memory.get(pos);
            if (tile.getType().equals(TileType.DEPOSIT)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Calculates maxFoodLoad based on ant's max foodLoad and how many ants are
     * hatched. Lower maxFoodLoad with few ants.
     *
     * @return
     */
    private int calcMaxFoodLoad() {
        List<IAntInfo> ants = cm.getAnts();
        int maxLoad = thisAnt.getAntType().getMaxFoodLoad();

        if (ants.size() < 3) {
            return maxLoad / 3;
        } else {
            int noOfCarriers = 0;
            for (IAntInfo ant : cm.getAnts()) {
                if (ant.getAntType().equals(EAntType.CARRIER)) {
                    noOfCarriers++;
                }
            }
            if (noOfCarriers <= 2) {
                return maxLoad / 2;
            } else {
                return maxLoad;
            }
        }
    }
}
