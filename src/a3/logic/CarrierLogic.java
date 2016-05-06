package a3.logic;

import aiantwars.EAction;
import aiantwars.EAntType;
import aiantwars.IAntInfo;
import aiantwars.ILocationInfo;
import aiantwars.impl.Location;
import a3.algorithm.ShortestPath;
import a3.behaviour.ScavengeFood;
import java.util.List;
import java.util.Map;
import a3.memory.CollectiveMemory;
import a3.memory.model.Position;
import a3.memory.model.Tile;
import a3.memory.model.TileType;
import a3.utility.Calc;
import static a3.utility.Debug.println;
import static a3.utility.Action.getRandomAction;

/**
 *
 * @author Tobias Jacobsen
 */
public class CarrierLogic {

    private final CollectiveMemory cm;
    private final IAntInfo thisAnt;
    private final ILocationInfo thisLocation;
    private final List<EAction> possibleActions;
    private final int maxFoodLoad;
    private final int minFoodLoad;

    public CarrierLogic(IAntInfo thisAnt, ILocationInfo thisLocation, List<EAction> possibleActions, CollectiveMemory cm) {
        this.cm = cm;
        this.thisAnt = thisAnt;
        this.thisLocation = thisLocation;
        this.possibleActions = possibleActions;
        maxFoodLoad = calcMaxFoodLoad();
        minFoodLoad = 3;
    }

    public EAction getAction() {

        println("Carrier: Current AP: " + thisAnt.getActionPoints() + "| Available actions: " + possibleActions.toString());
        println("Carrier: foodLoad: " + thisAnt.getFoodLoad() + ", maxFoodLoad: " + maxFoodLoad);

        if (isDeposit() && possibleActions.contains(EAction.DropFood) && thisAnt.getFoodLoad() > minFoodLoad) {
            // a. when ant have food and current position is a deposit, then drop food
            return EAction.DropFood;
        } else if (isDeposit() && !possibleActions.contains(EAction.DropFood) && thisAnt.getFoodLoad() > minFoodLoad) {
            // b. when ant have food and current position is a deposit, but ant cannot drop food, then pass turn
            return EAction.Pass;
        } else if (!isDeposit() && possibleActions.contains(EAction.PickUpFood) && thisAnt.getFoodLoad() < maxFoodLoad) {
            // c. when ant is below max food load threshold, then pickup food
            return EAction.PickUpFood;
        } else if (thisAnt.getFoodLoad() >= maxFoodLoad) {
            // d. when ant max food load has been reached, then return to deposit location with lowest food count
            println("d. max food load has been reached. Returning to deposit location with lowest food count");
            ILocationInfo depositLocation = findDepositLocation();
            if (depositLocation != null) {
                ShortestPath path = new ShortestPath(thisAnt, thisLocation, findDepositLocation(), cm);
                int movementDirection = Calc.getMovementDirection(thisLocation, path.getShortestPath().get(0));
                EAction movementAction = Calc.getMovementAction(thisAnt.getDirection(), movementDirection, false);
                if (possibleActions.contains(movementAction)) {
                    return movementAction;
                }
            }
        } else if (thisLocation.getFoodCount() == 0) {
            // e. when current position has 0 food, then scavenge food
            println("e. current position: " + thisLocation.getX() + "," + thisLocation.getY() + " has 0 food. Scavenging food");
            ScavengeFood scavengeFood = new ScavengeFood(thisAnt, cm);
            EAction eAction = scavengeFood.getEAction();
            if (!eAction.equals(EAction.Pass)) {
                return eAction;
            }
        }
        // f. if no action is returned from above, then pick a random action
        println("Carrier: Picked random");
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

        int noOfCarriers = 0;
        for (IAntInfo ant : cm.getAnts()) {
            if (ant.getAntType().equals(EAntType.CARRIER)) {
                noOfCarriers++;
            }
        }

        if (ants.size() <= 3 || noOfCarriers <= 2) {
            return maxLoad / 2;
        } else {
            return maxLoad;
        }
    }
}
