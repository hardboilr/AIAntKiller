package test;

import aiantwars.EAction;
import aiantwars.EAntType;
import algorithm.model.Node;
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

    @Test
    public void GetMovementCostTest() {
        int turnLeft = Calc.getMovementCost(EAction.TurnLeft, EAntType.QUEEN, true);
        int turnRight = Calc.getMovementCost(EAction.TurnRight, EAntType.QUEEN, true);
        int moveBackward = Calc.getMovementCost(EAction.MoveBackward, EAntType.QUEEN, true);
        int moveForward = Calc.getMovementCost(EAction.MoveForward, EAntType.QUEEN, true);
        assertEquals(5, turnLeft);
        assertEquals(5, turnRight);
        assertEquals(4, moveBackward);
        assertEquals(3, moveForward);
    }

    @Test
    public void getMovementDirectionTest() {
        Node parent = new Node(2, 3, 0);
        Node child = new Node(1, 3, -1);
        int direction = Calc.getMovementDirection(parent, child);
        assertEquals(3, direction);
        
        parent = new Node(2, 3, 0);
        child = new Node(2, 4, -1);
        direction = Calc.getMovementDirection(parent, child);
        assertEquals(0, direction);
        
        parent = new Node(2, 3, 0);
        child = new Node(3, 3, -1);
        direction = Calc.getMovementDirection(parent, child);
        assertEquals(1, direction);
        
        parent = new Node(2, 3, 0);
        child = new Node(2, 2, -1);
        direction = Calc.getMovementDirection(parent, child);
        assertEquals(2, direction);
    }
}
