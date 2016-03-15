package test;

import aiantwars.EAction;
import aiantwars.EAntType;
import org.junit.Test;
import static org.junit.Assert.*;
import utility.Calc;

/**
 * 
 * @author Tobias
 */
public class CalcTest {

    @Test
    public void GetMovementActionTest() {
        int currentDirection = 1;
        int direction = 2;
        EAction movementAction = Calc.getMovementAction(currentDirection, direction);
        assertEquals(EAction.TurnRight, movementAction);

        currentDirection = 4;
        direction = 2;
        movementAction = Calc.getMovementAction(currentDirection, direction);
        assertEquals(EAction.MoveBackward, movementAction);

        currentDirection = 3;
        direction = 2;
        movementAction = Calc.getMovementAction(currentDirection, direction);
        assertEquals(EAction.TurnLeft, movementAction);

    }

    @Test(expected = Exception.class)
    public void GetMovementCostExceptionTest() throws Exception {
        Calc.getMovementCost(EAction.Attack, EAntType.QUEEN, true);
    }

    @Test
    public void GetMovementCostTest() throws Exception {
        int turnLeft = Calc.getMovementCost(EAction.TurnLeft, EAntType.QUEEN, true);
        int turnRight = Calc.getMovementCost(EAction.TurnRight, EAntType.QUEEN, true);
        int moveBackward = Calc.getMovementCost(EAction.MoveBackward, EAntType.QUEEN, true);
        int moveForward = Calc.getMovementCost(EAction.MoveForward, EAntType.QUEEN, true);
        assertEquals(5, turnLeft);
        assertEquals(5, turnRight);
        assertEquals(4, moveBackward);
        assertEquals(3, moveForward);
    }
}
