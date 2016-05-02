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
import a3.ant.CarrierLogic;
import java.util.ArrayList;
import java.util.List;
import a3.memory.CollectiveMemory;
import a3.memory.Position;
import a3.memory.TileType;
import org.junit.Test;
import static org.junit.Assert.*;
import a3.test.model.OnGameFinished;
import a3.utility.Debug;

/**
 *
 * @author Tobias Jacobsen
 */
public class CarrierLogicTest {

    private final CollectiveMemory cm = new CollectiveMemory();
    private final Board board;
    private IAntInfo thisAnt;
    private final Location thisLocation;
    private List<ILocationInfo> visibleLocations;
    List<EAction> possibleActions;
    private final OnGameFinished onGameFinished;
    private AntWarsGameCtrl factory;
    private JT_Destroyer ai;

    public CarrierLogicTest() {
        Debug.isDebug = false;
        
        cm.saveWorldSizeX(10);
        cm.saveWorldSizeY(10);
        onGameFinished = new OnGameFinished();
        board = new Board(cm.getWorldSizeX(), cm.getWorldSizeY());
        IGraphicsAntWarsGUI antwarsGUI = new DummyGraphicsAntWarsGUI(false);
        factory = new AntWarsGameCtrl(antwarsGUI, board, onGameFinished);
        thisLocation = new Location(4, 5);
        ai = new JT_Destroyer();
        thisAnt = new LogicAnt(EAntType.CARRIER, ai, board, factory, thisLocation, 0, false, 0, 50, 0, 5, false);
    }

    @Test
    public void getActionTest() {
        thisLocation.setFoodCount(2);

        visibleLocations = new ArrayList() {
            {
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

        possibleActions = new ArrayList();
        possibleActions.add(EAction.Pass);
        possibleActions.add(EAction.DropFood);
        possibleActions.add(EAction.PickUpFood);
        possibleActions.add(EAction.EatFood);
        possibleActions.add(EAction.MoveBackward);
        possibleActions.add(EAction.MoveForward);
        possibleActions.add(EAction.TurnRight);
        possibleActions.add(EAction.TurnLeft);

        // a. when ant have food and current position is a deposit, then drop food
        cm.getTile(new Position(thisLocation.getX(), thisLocation.getY())).setType(TileType.DEPOSIT);
        thisAnt = new LogicAnt(EAntType.CARRIER, ai, board, factory, thisLocation, 0/**direction*/, false/**carriesSoil*/, 0/**age*/, 50/**hitpoints*/, 30/**foodLoad*/, 40/**hitpoints*/, false/**isDead*/);
        CarrierLogic logic = new CarrierLogic(thisAnt, thisLocation, possibleActions, cm);
        EAction action = logic.getAction();
        assertEquals(EAction.DropFood, action); 
        
        // b. when ant have food and current position is a deposit, but ant cannot drop food, then pass turn
        possibleActions = new ArrayList();
        possibleActions.add(EAction.Pass);
        possibleActions.add(EAction.PickUpFood);
        possibleActions.add(EAction.EatFood);
        possibleActions.add(EAction.MoveBackward);
        possibleActions.add(EAction.MoveForward);
        possibleActions.add(EAction.TurnRight);
        possibleActions.add(EAction.TurnLeft);
        
        cm.getTile(new Position(thisLocation.getX(), thisLocation.getY())).setType(TileType.DEPOSIT);
        thisAnt = new LogicAnt(EAntType.CARRIER, ai, board, factory, thisLocation, 0/**direction*/, false/**carriesSoil*/, 0/**age*/, 50/**hitpoints*/, 30/**foodLoad*/, 40/**hitpoints*/, false/**isDead*/);
        logic = new CarrierLogic(thisAnt, thisLocation, possibleActions, cm);
        action = logic.getAction();
        assertEquals(EAction.Pass, action); 

        // c. when ant is within max food load threshold, then pickup food
        possibleActions = new ArrayList();
        possibleActions.add(EAction.Pass);
        possibleActions.add(EAction.DropFood);
        possibleActions.add(EAction.PickUpFood);
        possibleActions.add(EAction.EatFood);
        possibleActions.add(EAction.MoveBackward);
        possibleActions.add(EAction.MoveForward);
        possibleActions.add(EAction.TurnRight);
        possibleActions.add(EAction.TurnLeft);
        
        cm.getTile(new Position(thisLocation.getX(), thisLocation.getY())).setType(TileType.DEFAULT);
        thisAnt = new LogicAnt(EAntType.CARRIER, ai, board, factory, thisLocation, 1/**direction*/, false/**carriesSoil*/, 0/**age*/, 50/**hitpoints*/, 0/**foodLoad*/, 40/**hitpoints*/, false/**isDead*/);
        logic = new CarrierLogic(thisAnt, thisLocation, possibleActions, cm);
        action = logic.getAction();
        assertEquals(EAction.PickUpFood, action);
        
        // d. when ant max food load has been reached, then return to deposit location with lowest food count
        possibleActions = new ArrayList();
        possibleActions.add(EAction.Pass);
        possibleActions.add(EAction.DropFood);
        possibleActions.add(EAction.PickUpFood);
        possibleActions.add(EAction.EatFood);
        possibleActions.add(EAction.MoveBackward);
        possibleActions.add(EAction.MoveForward);
        possibleActions.add(EAction.TurnRight);
        possibleActions.add(EAction.TurnLeft);
        
        cm.addTile(new Location(6,7));
        cm.getTile("6,7").setType(TileType.DEPOSIT);
        
        thisAnt = new LogicAnt(EAntType.CARRIER, ai, board, factory, thisLocation, 1/**direction*/, false/**carriesSoil*/, 0/**age*/, 50/**hitpoints*/, 20/**foodLoad*/, 40/**hitpoints*/, false/**isDead*/);
        logic = new CarrierLogic(thisAnt, thisLocation, possibleActions, cm);
        action = logic.getAction();
        assertEquals(EAction.MoveForward, action);
    }
}
