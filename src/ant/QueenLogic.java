package ant;

import aiantwars.EAction;
import aiantwars.IAntInfo;
import aiantwars.ILocationInfo;
import aiantwars.impl.Location;
import algorithm.ShortestPath;
import java.util.List;
import java.util.Map;
import memory.CollectiveMemory;
import memory.Position;
import memory.Tile;
import memory.TileType;
import static utility.Action.getRandomAction;
import utility.Calc;
import utility.Debug;
import static utility.Debug.println;

public class QueenLogic {

    private final CollectiveMemory cm = CollectiveMemory.getInstance();
    private IAntInfo thisAnt;
    private ILocationInfo thisLocation;
    private List<EAction> possibleActions;
    Map<Position, Tile> memory = cm.getTiles();

    private static QueenLogic instance = null;

    private QueenLogic() {
    }

    /**
     * Returns the instance of this class
     *
     * @return instance
     */
    public static QueenLogic getInstance() {
        if (instance == null) {
            instance = new QueenLogic();
        }
        return instance;
    }

    /**
     * Calculates the action for queen and returns it
     *
     * @param thisAnt
     * @param thisLocation
     * @param possibleActions
     * @param visibleLocations
     * @param turn
     * @return EAction
     */
    public EAction getAction(IAntInfo thisAnt, ILocationInfo thisLocation, List<EAction> possibleActions, List<ILocationInfo> visibleLocations, int turn) {
        Debug.isDebug = true;
        
        this.thisAnt = thisAnt;
        this.thisLocation = thisLocation;
        this.possibleActions = possibleActions;
        EAction action = getRandomAction(possibleActions);
        memory = cm.getTiles();

        /**
         * Queen is creating breeding grounds and deposit locations, near the
         * queenSpawn used for the rest of the game
         *
         */
        Position currentPos = new Position(thisLocation.getX(), thisLocation.getY());
        if (thisLocation.getX() == cm.getQueenSpawn().getX() && thisLocation.getY() == cm.getQueenSpawn().getY()) {
            if (thisLocation.getFoodCount() <= 1) {
                if (memory.containsKey(currentPos)) {
                    Tile tile = memory.get(currentPos);
                    if (tile.getType() == TileType.DEFAULT) {
                        tile.setType(TileType.BREEDING);
                        println("Set location " + currentPos.toString() + " to a breeding location!");
                    }
                }
            }
        } else {
            ShortestPath sp = new ShortestPath(thisAnt, thisLocation, new Location(cm.getQueenSpawn().getX(), cm.getQueenSpawn().getY()));
            List<ILocationInfo> path = sp.getShortestPath();
            //Breeding location
            if (path.size() == 1) {
                if (thisLocation.getFoodCount() <= 1) {
                    if (memory.containsKey(currentPos)) {
                        Tile tile = memory.get(currentPos);
                        if (tile.getType() == TileType.DEFAULT) {
                            tile.setType(TileType.BREEDING);
                            println("Set location " + currentPos.toString() + " to a breeding location!");
                        }
                    }
                }
            } //Deposit location
            else if (path.size() == 2) {
                if (memory.containsKey(currentPos)) {
                    Tile tile = memory.get(currentPos);
                    if (tile.getType() == TileType.DEFAULT) {
                        tile.setType(TileType.DEPOSIT);
                        println("Set location " + currentPos.toString() + " to a deposit location!");
                    }
                }
            }
        }

        /**
         * If possible actions contains LayEgg and position is right according
         * to breeding grounds, then lay egg
         *
         * Else if possible actions contains PickUpFood then pickupfoood
         */
        if (possibleActions.contains(EAction.LayEgg)) {
            Position pos = new Position(visibleLocations.get(0).getX(), visibleLocations.get(0).getY());
            if (memory.containsKey(pos)) {
                Tile tile = memory.get(pos);
                if (tile.getType().equals(TileType.BREEDING)) {
                    action = EAction.LayEgg;
                }
            }
        } else if (possibleActions.contains(EAction.PickUpFood)) {
            action = EAction.PickUpFood;
        }

        /**
         * If thisAnt foodload is larger than 5 or equal to 5, look for breeding
         * grounds
         *
         * Else if foodload is lower than 5, look for deposit locations
         */
        if (thisAnt.getFoodLoad() >= 5) {
            println("queen: has enough food to lay egg, looking for breeding location");
            ILocationInfo breedingLocation = findBreedingLocation();
            if (breedingLocation != null) {
                ShortestPath path = new ShortestPath(thisAnt, thisLocation, breedingLocation);
                int movementDirection = Calc.getMovementDirection(thisLocation, path.getShortestPath().get(0));
                EAction movementAction = Calc.getMovementAction(thisAnt.getDirection(), movementDirection);
                if (possibleActions.contains(movementAction)) {
                    return movementAction;
                }
            }
        } else if (thisAnt.getFoodLoad() < 5) {
            println("queen: is low on food, looking for deposit location");
            ILocationInfo depositLocation = findDepositLocation();
            if (depositLocation != null) {
                ShortestPath path = new ShortestPath(thisAnt, thisLocation, depositLocation);
                int movementDirection = Calc.getMovementDirection(thisLocation, path.getShortestPath().get(0));
                EAction movementAction = Calc.getMovementAction(thisAnt.getDirection(), movementDirection);
                if (possibleActions.contains(movementAction)) {
                    return movementAction;
                }
            }
        }

        return checkAction(action);
    }

    /**
     * Looks through all deposit locations and returns the location with highest
     * food count
     *
     * @return ILocation
     */
    private ILocationInfo findDepositLocation() {
        Tile deposit = new Tile(0, 0);
        deposit.setFoodCount(Integer.MAX_VALUE);
        boolean foundDeposit = false;

        for (Map.Entry<Position, Tile> entry : memory.entrySet()) {
            if (entry.getValue().getType().equals(TileType.DEPOSIT)) {
                if (entry.getValue().getFoodCount() > deposit.getFoodCount()) {
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
     * Looks through all breeding locations and returns the closest empty
     * location
     *
     * @return ILocation
     */
    private ILocationInfo findBreedingLocation() {
        Tile breeding = new Tile(0, 0);
        boolean foundBreeding = false;
        int distance = Integer.MAX_VALUE;
        ShortestPath path;
        List<ILocationInfo> pathList;
        for (Map.Entry<Position, Tile> entry : memory.entrySet()) {
            if (entry.getValue().getType().equals(TileType.BREEDING)) {
                if (entry.getValue().getAnt() == null) {
                    path = new ShortestPath(thisAnt, thisLocation, new Location(entry.getValue().getX(), entry.getValue().getY()));
                    pathList = path.getShortestPath();
                    if (pathList.size() < distance) {
                        foundBreeding = true;
                        breeding = new Tile(entry.getValue().getX(), entry.getValue().getY());
                    }

                }
            }
        }

        if (!foundBreeding) {
            return null;
        } else {
            ILocationInfo loc = new Location(breeding.getX(), breeding.getY());
            return loc;
        }
    }

    public EAction checkAction(EAction action) {
        if (action == EAction.EatFood) {
            action = getRandomAction(possibleActions);
            checkAction(action);
        }
        return action;
    }
}
