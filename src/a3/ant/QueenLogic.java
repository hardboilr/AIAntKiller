package a3.ant;

import aiantwars.EAction;
import aiantwars.IAntInfo;
import aiantwars.ILocationInfo;
import aiantwars.impl.Location;
import a3.algorithm.ShortestPath;
import java.util.List;
import java.util.Map;
import a3.memory.CollectiveMemory;
import a3.memory.Position;
import a3.memory.Tile;
import a3.memory.TileType;
import static a3.utility.Action.getRandomAction;
import a3.utility.Calc;
import a3.utility.Debug;
import static a3.utility.Debug.println;

public class QueenLogic {

    private final CollectiveMemory cm;
    private IAntInfo thisAnt;
    private ILocationInfo thisLocation;
    private List<EAction> possibleActions;
    Map<Position, Tile> memory;


    public QueenLogic(CollectiveMemory cm) {
        this.cm = cm;
        memory = cm.getTiles();
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
        Debug.isDebug = false;

        this.thisAnt = thisAnt;
        this.thisLocation = thisLocation;
        this.possibleActions = possibleActions;
        EAction action = getRandomAction(possibleActions);
        memory = cm.getTiles();

        boolean actionPicked = false;

        //Position right in fornt of thisAnt as Position object
        Position posInFront = new Position(visibleLocations.get(0).getX(), visibleLocations.get(0).getY());
        //thisLocation as a Position object
        Position currentPos = new Position(thisLocation.getX(), thisLocation.getY());

        //tileInFront used for locating breeding grounds
        Tile tileInFront = memory.get(currentPos);

        /**
         * If location in front of thisAnt in memory get location
         */
        if (memory.containsKey(posInFront)) {
            tileInFront = memory.get(posInFront);
        }

        /**
         * Queen is creating breeding grounds and deposit locations, near the
         * queenSpawn used for the rest of the game
         *
         */
        //If location is queenspawn
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
            ShortestPath sp = new ShortestPath(thisAnt, thisLocation, new Location(cm.getQueenSpawn().getX(), cm.getQueenSpawn().getY()), cm);
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
            else if (path.size() == 2 || path.size() == 3) {
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
        if (possibleActions.contains(EAction.LayEgg) && turn < 30) {
            action = EAction.LayEgg;
            actionPicked = true;
        } else if (possibleActions.contains(EAction.LayEgg)) {
            println("PossibleAction layEgg at current pos: " + posInFront.toString());
            if (tileInFront.getType().equals(TileType.BREEDING)) {
                action = EAction.LayEgg;
                actionPicked = true;
            }
        } else if (possibleActions.contains(EAction.PickUpFood)) {
            action = EAction.PickUpFood;
            actionPicked = true;
        }

        /**
         * Check if positionInFront of thisAnt is breeding ground and
         * actionpoints < 5 then pass
         *
         * Else look for breeding grounds or deposit locations
         */
        if (!actionPicked) {
            if (tileInFront.getType() == TileType.BREEDING && thisAnt.getActionPoints() < 5) {
                action = EAction.Pass;
            } else if (thisAnt.getFoodLoad() >= 5) {
                println("queen: has enough food to lay egg, looking for breeding location");
                ILocationInfo breedingLocation = findBreedingLocation();
                if (breedingLocation != null) {
                    ShortestPath path = new ShortestPath(thisAnt, thisLocation, breedingLocation, cm);
                    int movementDirection = Calc.getMovementDirection(thisLocation, path.getShortestPath().get(0));
                    EAction movementAction = Calc.getMovementAction(thisAnt.getDirection(), movementDirection);
                    if (possibleActions.contains(movementAction)) {
                        action = movementAction;
                    }
                }
            } else if (thisAnt.getFoodLoad() < 5) {
                println("queen: is low on food, looking for deposit location");
                ILocationInfo depositLocation = findDepositLocation();
                if (depositLocation != null) {
                    ShortestPath path = new ShortestPath(thisAnt, thisLocation, depositLocation, cm);
                    int movementDirection = Calc.getMovementDirection(thisLocation, path.getShortestPath().get(0));
                    EAction movementAction = Calc.getMovementAction(thisAnt.getDirection(), movementDirection);
                    if (possibleActions.contains(movementAction)) {
                        action = movementAction;
                    }
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
            println("Found location (" + loc.getX() + ", " + loc.getY() + ")");
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
                    path = new ShortestPath(thisAnt, thisLocation, new Location(entry.getValue().getX(), entry.getValue().getY()), cm);
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
            println("FindBreedingLocation: returned location (" + loc.getX() + ", " + loc.getY() + ")");
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
