package a3.test;

import a3.ai.JT_Destroyer;
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
import a3.behaviour.ScavengeFood;
import java.util.ArrayList;
import java.util.List;
import a3.memory.CollectiveMemory;
import org.junit.Test;
import static org.junit.Assert.*;
import a3.test.model.OnGameFinished;

/**
 * 
 * @author Tobias
 */
public class ScavengeFoodTest {

    private final CollectiveMemory cm = new CollectiveMemory();
    private final Board board;
    private final IAntInfo thisAnt;
    private final ILocationInfo thisLocation;
    private List<ILocationInfo> visibleLocations;
    private final LogicAnt queen;
    private final OnGameFinished onGameFinished;

    public ScavengeFoodTest() {
        onGameFinished = new OnGameFinished();
        board = new Board(9, 9);
        IGraphicsAntWarsGUI antwarsGUI = new DummyGraphicsAntWarsGUI(false);
        AntWarsGameCtrl factory = new AntWarsGameCtrl(antwarsGUI, board, onGameFinished);
        thisLocation = new Location(1, 1);
        Location location2 = new Location(4, 4);
        ITeamInfo teamInfo = new TeamInfo(1, "Test team");
        JT_Destroyer ai = new JT_Destroyer();
        thisAnt = new LogicAnt(factory, board, (Location) thisLocation, 0, 1, teamInfo, EAntType.CARRIER, ai);
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
        cm.addTiles(visibleLocations);
        
        
        ScavengeFood collectFood = new ScavengeFood(thisAnt, cm);
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
        cm.addTiles(visibleLocations);
        
        ScavengeFood collectFood = new ScavengeFood(thisAnt, cm);
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
        cm.addTiles(visibleLocations);
        
        ScavengeFood collectFood = new ScavengeFood(thisAnt, cm);
        EAction eAction = collectFood.getEAction();
        assertEquals(EAction.TurnLeft, eAction);
    }
}
