package utility;

import aiantwars.EAction;
import aiantwars.EAntType;
import algorithm.model.Node;

/**
 * Todo: getMovementCost might be redundant!
 *
 * @author Tobias
 */
public class Calc {

    //0 = NORTH, 1 = EAST, 2 = SOUTH, 3 = WEST
    public static EAction getMovementAction(int currentDirection, int direction) {
        if (currentDirection == direction) {
            // going forward
            return EAction.MoveForward;
        } else if (Math.abs(currentDirection - direction) == 2) {
            // going backward
            return EAction.MoveBackward;
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

    public static int getMovementDirection(Node parent, Node child) {
        if (child.getX() > parent.getX()) {
            return 1;
        } else if (child.getX() < parent.getX()) {
            return 3;
        } else if (child.getY() > parent.getY()) {
            return 0;
        } else {
            return 2;
        }
    }

}
