package a3.utility;

import aiantwars.EAction;
import aiantwars.EAntType;
import aiantwars.ILocationInfo;
import a3.algorithm.model.Node;
import a3.memory.model.Tile;
import aiantwars.impl.Location;
import static java.lang.Math.abs;

/**
 * Todo: getMovementCost might be redundant!
 *
 * @author Tobias Jacobsen
 */
public class Calc {

    //0 = NORTH, 1 = EAST, 2 = SOUTH, 3 = WEST
    public static EAction getMovementAction(int currentDirection, int direction, boolean canMoveBackward) {
        if (currentDirection == direction) {
            // going forward
            return EAction.MoveForward;
        } else if (Math.abs(currentDirection - direction) == 2) {
            // going backward
            if (canMoveBackward) {
                return EAction.MoveBackward;
            } else {
                return EAction.TurnLeft;
            }
        } else if (currentDirection + 1 == direction || currentDirection - 3 == direction) {
            // turning right
            return EAction.TurnRight;
        } else if (currentDirection - 1 == direction || currentDirection + 3 == direction) {
            // turning left
            return EAction.TurnLeft;
        }
        return EAction.Pass;
    }

    public static int getMovementCost(EAction action, EAntType antType, boolean canMoveBackward) {
        switch (action) {
            case MoveForward:
                return antType.getActionCost(EAction.MoveForward);
            case MoveBackward:
                if (canMoveBackward) {
                    return antType.getActionCost(EAction.MoveBackward);
                } else {
                    return (antType.getActionCost(EAction.TurnRight) * 2) + antType.getActionCost(EAction.MoveForward);
                }
            case TurnLeft:
                return antType.getActionCost(EAction.TurnLeft) + antType.getActionCost(EAction.MoveForward);
            case TurnRight:
                return antType.getActionCost(EAction.TurnRight) + antType.getActionCost(EAction.MoveForward);
            default:
                return Integer.MAX_VALUE;
        }
    }

    /**
     * Calculates movementDirection from position A to position B. Accepts Nodes
     * and ILocationInfo
     *
     * @param a Object a (Node or ILocationInfo)
     * @param b Object b (Node or ILocationInfo)
     * @return the direction (0: NORTH, 1: EAST, 2: SOUTH, 3: WEST)
     */
    public static int getMovementDirection(Object a, Object b) {
        int parentX = -1;
        int parentY = -1;
        int childX = -1;
        int childY = -1;

        // if Node
        if (a instanceof Node && b instanceof Node) {
            Node parent = (Node) a;
            parentX = parent.getX();
            parentY = parent.getY();
            Node child = (Node) b;
            childX = child.getX();
            childY = child.getY();
        }

        // if ILocationInfo
        if (a instanceof ILocationInfo && b instanceof ILocationInfo) {
            ILocationInfo parent = (ILocationInfo) a;
            parentX = parent.getX();
            parentY = parent.getY();
            ILocationInfo child = (ILocationInfo) b;
            childX = child.getX();
            childY = child.getY();
        }

        if (childX > parentX) {
            return 1;
        } else if (childX < parentX) {
            return 3;
        } else if (childY > parentY) {
            return 0;
        } else {
            return 2;
        }
    }

    /**
     * Calculates distance from A to B when 1 movement equals 1 distance
     *
     * @param currentLocation
     * @param goalLocation
     * @return
     */
    public static int distanceFromAtoB(Object currentLocation, Object goalLocation) {
        int curX = 0, curY = 0, goalX = 0, goalY = 0;
        if (currentLocation instanceof ILocationInfo) {
            curX = ((ILocationInfo) currentLocation).getX();
            curY = ((ILocationInfo) currentLocation).getY();
        } else if (currentLocation instanceof Tile) {
            curX = ((Tile) currentLocation).getX();
            curY = ((Tile) currentLocation).getY();
        } else if (currentLocation instanceof Location) {
            curX = ((Location) currentLocation).getX();
            curY = ((Location) currentLocation).getY();
        }
        if (goalLocation instanceof ILocationInfo) {
            goalX = ((ILocationInfo) goalLocation).getX();
            goalY = ((ILocationInfo) goalLocation).getY();
        } else if (goalLocation instanceof Tile) {
            goalX = ((Tile) goalLocation).getX();
            goalY = ((Tile) goalLocation).getY();
        } else if (goalLocation instanceof Location) {
            goalX = ((Location) goalLocation).getX();
            goalY = ((Location) goalLocation).getY();
        }
        return abs(curX - goalX) + abs(curY - goalY);
    }
}
