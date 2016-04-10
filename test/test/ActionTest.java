package test;

import aiantwars.EAction;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import utility.Action;
import utility.Debug;
import static utility.Debug.println;

/**
 *
 * @author Tobias Jacobsen
 */
public class ActionTest {

    public ActionTest() {
        Action action = new Action();
        Debug.isDebug = true;
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void getRandomActionTest() {

        // sum: 100+20+60+60+0+5 = 245
        Action.setMoveForwardWeight(100); // 100/245 ~ ,41 -> 4100 times
        Action.setMoveBackwardWeight(20); // 20/245 ~ 0,08 -> 800 times
        Action.setTurnLeftWeight(60); // 60/245 ~ 0,24 -> 2400 times
        Action.setTurnRightWeight(60); // 60/245 ~ 0,24 -> 2400 times
        Action.setAttackWeight(0); // 0 -> 0 times
        Action.setPassWeight(5); // 5/245 ~ 0,02 -> 200 times

        List<EAction> possibleActions = new ArrayList();
        possibleActions.add(EAction.MoveForward);
        possibleActions.add(EAction.MoveBackward);
        possibleActions.add(EAction.TurnLeft);
        possibleActions.add(EAction.TurnRight);
        possibleActions.add(EAction.Attack);
        possibleActions.add(EAction.Pass);

        int moveForward = 0;
        int moveBackward = 0;
        int turnLeft = 0;
        int turnRight = 0;
        int attack = 0;
        int pass = 0;

        for (int i = 0; i < 10000; i++) {
            EAction randomAction = Action.getRandomAction(possibleActions);

            switch (randomAction) {
                case MoveForward:
                    moveForward++;
                    break;
                case MoveBackward:
                    moveBackward++;
                    break;
                case TurnLeft:
                    turnLeft++;
                    break;
                case TurnRight:
                    turnRight++;
                    break;
                case Attack:
                    attack++;
                    break;
                case Pass:
                    pass++;
                    break;
                default:
                    break;
            }
        }
        
        println("moveForward: " + moveForward + " times.");
        println("moveBackward: " + moveBackward + " times.");
        println("turnLeft: " + turnLeft + " times.");
        println("turnRight: " + turnRight + " times.");
        println("attack: " + attack + " times.");
        println("pass: " + pass + " times.");
        
        assertEquals(4100, moveForward, 100);
        assertEquals(800, moveBackward, 100);
        assertEquals(2400, turnLeft, 100);
        assertEquals(2400, turnRight, 100);
        assertEquals(200, pass, 100);

    }
}
