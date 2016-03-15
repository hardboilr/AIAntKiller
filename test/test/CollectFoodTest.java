package test;

import ai.Main;
import aiantwars.EAction;
import aiantwars.EAntType;
import aiantwars.IAntInfo;
import aiantwars.ILocationInfo;
import aiantwars.ITeamInfo;
import aiantwars.graphicsinterface.IGraphicsAntWarsGUI;
import aiantwars.impl.AntWarsGameCtrl;
import aiantwars.impl.Board;
import aiantwars.impl.DummyGraphicsAntWarsGUI;
import aiantwars.impl.Location;
import aiantwars.impl.LogicAnt;
import aiantwars.impl.TeamInfo;
import behaviour.food.CollectFood;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * 
 * @author Tobias
 */
public class CollectFoodTest {

    private final Board board;
    private final IAntInfo thisAnt;
    private final ILocationInfo thisLocation;
    private List<ILocationInfo> visibleLocations;
    private final LogicAnt queen;

    public CollectFoodTest() {
        board = new Board(9, 9);
        IGraphicsAntWarsGUI antwarsGUI = new DummyGraphicsAntWarsGUI();
        AntWarsGameCtrl factory = new AntWarsGameCtrl(antwarsGUI, board);
        thisLocation = new Location(1, 1);
        Location location2 = new Location(4, 4);
        ITeamInfo teamInfo = new TeamInfo(1, "Test team");
        Main ai = new Main();
        thisAnt = new LogicAnt(factory, board, (Location) thisLocation, 1, 1, teamInfo, EAntType.CARRIER, ai);
        queen = new LogicAnt(factory, board, location2, 4, 2, teamInfo, EAntType.QUEEN, ai);

    }

    @Test
    public void testCollectFood1() {
        visibleLocations = new ArrayList() {
            {
                Location loc01 = new Location(0, 1);
                loc01.setFoodCount(3);
                add(loc01);
                Location loc10 = new Location(1, 0);
                loc10.setFoodCount(0);
                add(loc10);
                Location loc11 = new Location(1, 1);
                loc11.setFoodCount(1);
                add(loc11);
                Location loc12 = new Location(1, 2);
                loc12.setRock(true);
                add(loc12);
                Location loc21 = new Location(2, 1);
                loc21.setFoodCount(1);
                add(loc21);
            }
        };
        CollectFood collectFood = new CollectFood(thisAnt, thisLocation, visibleLocations);
        EAction eAction = collectFood.getEAction();
        assertEquals(EAction.TurnLeft, eAction);
    }

    @Test
    public void testCollectFood2() {
        visibleLocations = new ArrayList() {
            {
                Location loc01 = new Location(0, 1); //west
                loc01.setFoodCount(4);
                add(loc01);
                Location loc10 = new Location(1, 0); //south
                loc10.setFoodCount(4);
                add(loc10);
                Location loc11 = new Location(1, 1); //start
                loc11.setFoodCount(1);
                add(loc11);
                Location loc12 = new Location(1, 2); //north
                loc12.setFoodCount(2);
                add(loc12);
                Location loc21 = new Location(2, 1); //east
                loc21.setFoodCount(0);
                add(loc21);
            }
        };
        CollectFood collectFood = new CollectFood(thisAnt, thisLocation, visibleLocations);
        EAction eAction = collectFood.getEAction();
        assertEquals(EAction.MoveBackward, eAction);
    }

    @Test
    public void testCollectFood3() {
        visibleLocations = new ArrayList() {
            {
                Location loc01 = new Location(0, 1); //west
                loc01.setFoodCount(4);
                add(loc01);
                Location loc10 = new Location(1, 0); //south
                loc10.setAnt(queen);
                add(loc10);
                Location loc11 = new Location(1, 1); //start
                loc11.setFoodCount(1);
                add(loc11);
                Location loc12 = new Location(1, 2); //north
                loc12.setFoodCount(2);
                add(loc12);
                Location loc21 = new Location(2, 1); //east
                loc21.setFoodCount(0);
                add(loc21);
            }
        };
        CollectFood collectFood = new CollectFood(thisAnt, thisLocation, visibleLocations);
        EAction eAction = collectFood.getEAction();
        assertEquals(EAction.TurnLeft, eAction);
    }
}
