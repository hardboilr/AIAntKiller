package a3.test.antlogic;

import a3.ai.JT_Destroyer;
import a3.logic.ScoutLogic;
import a3.memory.CollectiveMemory;
import a3.test.model.OnGameFinished;
import a3.utility.Debug;
import aiantwars.EAction;
import aiantwars.EAntType;
import aiantwars.IAntInfo;
import aiantwars.ILocationInfo;
import aiantwars.graphicsinterface.IGraphicsAntWarsGUI;
import aiantwars.impl.AntWarsGameCtrl;
import aiantwars.impl.Board;
import aiantwars.impl.DummyGraphicsAntWarsGUI;
import aiantwars.impl.Location;
import aiantwars.impl.LogicAnt;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Tobias Jacobsen
 */
public class ScoutLogicTest {

    private CollectiveMemory cm = new CollectiveMemory();
    private final Board board;
    private IAntInfo thisAnt;
    private final Location thisLocation;
    private List<ILocationInfo> visibleLocations;
    List<EAction> possibleActions;
    private final OnGameFinished onGameFinished;
    private final AntWarsGameCtrl factory;
    private final JT_Destroyer ai;

    public ScoutLogicTest() {
        Debug.isDebug = true;

        cm.saveWorldSizeX(10);
        cm.saveWorldSizeY(10);
        onGameFinished = new OnGameFinished();
        board = new Board(cm.getWorldSizeX(), cm.getWorldSizeY());
        IGraphicsAntWarsGUI antwarsGUI = new DummyGraphicsAntWarsGUI(false);
        factory = new AntWarsGameCtrl(antwarsGUI, board, onGameFinished);
        thisLocation = new Location(4, 5);
        ai = new JT_Destroyer();
        thisAnt = new LogicAnt(EAntType.SCOUT, ai, board, factory, thisLocation, 0, false, 0, 50, 0, 5, false);
    }

    @Test
    public void getActionTest() {
        thisLocation.setFoodCount(2);

        visibleLocations = new ArrayList() {
            {
                Location loc14 = new Location(1, 4);
                add(loc14);
                Location loc24 = new Location(2, 4);
                add(loc24);
                Location loc25 = new Location(2, 5);
                add(loc25);
                Location loc26 = new Location(2, 6);
                add(loc26);
                Location loc33 = new Location(3, 3);
                add(loc33);
                Location loc43 = new Location(4, 3);
                add(loc43);
                
                Location loc34 = new Location(3, 4);
                loc34.setFoodCount(4);
                add(loc34);
                Location loc35 = new Location(3, 5);
                loc35.setFoodCount(3);
                add(loc35);
                Location loc36 = new Location(3, 6);
                loc36.setFoodCount(0);
                add(loc36);
                Location loc37 = new Location(3, 7);
                loc37.setFoodCount(0);
                add(loc37);
                Location loc44 = new Location(4, 4);
                loc44.setFoodCount(3);
                add(loc44);
                Location loc46 = new Location(4, 6);
                loc46.setFoodCount(2);
                add(loc46);
                Location loc47 = new Location(4, 7);
                loc47.setFoodCount(0);
                add(loc47);
                Location loc54 = new Location(5, 4);
                loc54.setFoodCount(1);
                add(loc54);
                Location loc55 = new Location(5, 5);
                loc55.setFoodCount(4);
                add(loc55);
                Location loc56 = new Location(5, 6);
                loc56.setFoodCount(3);
                add(loc56);
                Location loc57 = new Location(5, 7);
                loc57.setFoodCount(0);
                add(loc57);
                Location loc64 = new Location(6, 4);
                loc64.setFoodCount(0);
                add(loc64);
                Location loc65 = new Location(6, 5);
                loc65.setFoodCount(0);
                add(loc65);
                Location loc66 = new Location(6, 6);
                loc66.setFoodCount(0);
                add(loc66);
                Location loc67 = new Location(6, 7);
                loc67.setFoodCount(0);
                add(loc67);
            }
        };
        visibleLocations.add(thisLocation);
        cm.addTiles(visibleLocations);
        
        cm.getTile("3,5").setFrequency(100);
        cm.getTile("3,6").setFrequency(100);
        cm.getTile("4,6").setFrequency(100);
        cm.getTile("3,4").setIsRock(true);
        cm.getTile("4,4").setFrequency(2);
        cm.getTile("5,6").setFrequency(0);
        cm.getTile("5,5").setFrequency(10);
        cm.getTile("5,4").setFrequency(3);

        possibleActions = new ArrayList();
        possibleActions.add(EAction.Pass);
        possibleActions.add(EAction.DropFood);
        possibleActions.add(EAction.PickUpFood);
        possibleActions.add(EAction.EatFood);
        possibleActions.add(EAction.MoveBackward);
        possibleActions.add(EAction.MoveForward);
        possibleActions.add(EAction.TurnRight);
        possibleActions.add(EAction.TurnLeft);

        // a. when location has food and ant's food load is below 5, then pickup food
        thisAnt = new LogicAnt(EAntType.SCOUT, ai, board, factory, thisLocation, 0/**direction*/, false/**carriesSoil*/, 0/**age*/, 50/**hitpoints*/, 4/**foodLoad*/, 40/**hitpoints*/, false/**isDead*/);
        ScoutLogic logic = new ScoutLogic(thisAnt, thisLocation, possibleActions, cm);
        EAction action = logic.getAction();
        assertEquals(EAction.PickUpFood, action); 
        
        // b. else explore 
        thisAnt = new LogicAnt(EAntType.SCOUT, ai, board, factory, thisLocation, 1/**direction*/, false/**carriesSoil*/, 0/**age*/, 50/**hitpoints*/, 6/**foodLoad*/, 40/**hitpoints*/, false/**isDead*/);
        logic = new ScoutLogic(thisAnt, thisLocation, possibleActions, cm);
        action = logic.getAction();
        assertTrue(action.equals(EAction.TurnLeft) || action.equals(EAction.TurnRight));
        
        // b. else explore 
        cm.clearTiles();
        cm.addTiles(visibleLocations);
        
        cm.getTile("1,4").setFrequency(1);
        cm.getTile("2,6").setFrequency(10);
        cm.getTile("3,6").setIsFilled(true);
        cm.getTile("4,6").setIsFilled(true);
        cm.getTile("5,6").setIsFilled(true);
        cm.getTile("2,5").setFrequency(100);
        cm.getTile("3,5").setFrequency(100);
        cm.getTile("3,7").setFrequency(100);
        cm.getTile("2,4").setFrequency(100);
        cm.getTile("3,4").setFrequency(100);
        cm.getTile("4,4").setFrequency(100);
        cm.getTile("4,7").setFrequency(100);
        cm.getTile("6,4").setFrequency(100);
        cm.getTile("6,5").setFrequency(100);
        cm.getTile("6,6").setFrequency(100);
        cm.getTile("3,3").setFrequency(15);
        cm.getTile("4,3").setFrequency(13);
        cm.getTile("5,5").setIsRock(true);
        cm.getTile("5,4").setIsRock(true);
        cm.getTile("5,7").setIsRock(true);
        
        thisAnt = new LogicAnt(EAntType.SCOUT, ai, board, factory, thisLocation, 1/**direction*/, false/**carriesSoil*/, 0/**age*/, 50/**hitpoints*/, 6/**foodLoad*/, 40/**hitpoints*/, false/**isDead*/);
        logic = new ScoutLogic(thisAnt, thisLocation, possibleActions, cm);
        action = logic.getAction();
        assertEquals(EAction.TurnRight, action);
        
    }
}
