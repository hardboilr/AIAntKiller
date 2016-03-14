package utility;

import aiantwars.EAction;
import aiantwars.EAntType;

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

    public static int getMovementCost(EAction action, EAntType antType) throws Exception {
        switch (action) {
            case MoveForward:
                return antType.getActionCost(EAction.MoveForward);
            case MoveBackward:
                return antType.getActionCost(EAction.MoveBackward);
            case TurnLeft:
                return antType.getActionCost(EAction.TurnLeft) + antType.getActionCost(EAction.MoveForward);
            case TurnRight:
                return antType.getActionCost(EAction.TurnRight) + antType.getActionCost(EAction.MoveForward);
            default:
                throw new Exception("Not a valid movement action");
        }
    }
}
