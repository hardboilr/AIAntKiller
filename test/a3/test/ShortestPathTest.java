package a3.test;

import a3.ai.JT_Destroyer;
import aiantwars.EAntType;
import aiantwars.IAntInfo;
import aiantwars.ILocationInfo;
import aiantwars.IOnGameFinished;
import aiantwars.ITeamInfo;
import aiantwars.graphicsinterface.IGraphicsAntWarsGUI;
import aiantwars.impl.AntWarsGameCtrl;
import aiantwars.impl.Board;
import aiantwars.impl.DummyGraphicsAntWarsGUI;
import aiantwars.impl.Location;
import aiantwars.impl.LogicAnt;
import aiantwars.impl.TeamInfo;
import a3.algorithm.ShortestPath;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import a3.memory.CollectiveMemory;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import a3.test.model.OnGameFinished;
import static a3.utility.Debug.isDebug;

/**
 *
 * @author Tobias Jacobsen
 */
public class ShortestPathTest {

    private final IOnGameFinished onFinished;
    Map<String, ILocationInfo> visibleLocations;
    private Board board;
    private IGraphicsAntWarsGUI antwarsGUI;
    private AntWarsGameCtrl factory;
    private ITeamInfo teamInfo;
    private JT_Destroyer ai;
    CollectiveMemory cm = new CollectiveMemory();

    public ShortestPathTest() {
        onFinished = new OnGameFinished();
        isDebug = false;
        visibleLocations = new HashMap();
        cm.saveWorldSizeX(10);
        cm.saveWorldSizeY(10);
    }

    /**
     * Tests shortest path with an ordinary scenario
     */
    @Test
    public void testScenario1() {
        Map<String, ILocationInfo> locations = getLocationsMap();
        Location loc41 = (Location) locations.get("4,1");
        loc41.setRock(true);
        Location loc42 = (Location) locations.get("4,2");
        loc42.setRock(true);
        Location loc43 = (Location) locations.get("4,3");
        loc43.setRock(true);
        Location loc44 = (Location) locations.get("4,4");
        loc44.setRock(true);
        Location loc45 = (Location) locations.get("4,5");
        loc45.setRock(true);
        Location loc46 = (Location) locations.get("4,6");
        loc46.setRock(true);
        Location loc36 = (Location) locations.get("3,6");
        loc36.setRock(true);
        Location loc26 = (Location) locations.get("2,6");
        loc26.setRock(true);
        Location loc16 = (Location) locations.get("1,6");
        loc16.setRock(true);
        Location loc64 = (Location) locations.get("6,4");
        loc64.setRock(true);
        Location loc74 = (Location) locations.get("7,4");
        loc74.setRock(true);
        Location loc84 = (Location) locations.get("8,4");
        loc84.setRock(true);

        cm.addTiles(getLocationsList());
        board = new Board(cm.getWorldSizeX(), cm.getWorldSizeY());

        antwarsGUI = new DummyGraphicsAntWarsGUI(false);
        factory = new AntWarsGameCtrl(antwarsGUI, board, onFinished);
        ai = new JT_Destroyer();
        teamInfo = new TeamInfo(1, "Test team");
        IAntInfo ant = new LogicAnt(factory, board, loc84, 0/*direction*/, 1, teamInfo, EAntType.SCOUT, ai);

        ILocationInfo start = new Location(2, 3);
        ILocationInfo goal = new Location(2, 7);

        ShortestPath sp = new ShortestPath(ant, start, goal, cm);
        List<ILocationInfo> shortestPath = sp.getShortestPath();

        assertEquals("2,4", shortestPath.get(0).getX() + "," + shortestPath.get(0).getY());
        assertEquals("2,5", shortestPath.get(1).getX() + "," + shortestPath.get(1).getY());
        assertEquals("1,5", shortestPath.get(2).getX() + "," + shortestPath.get(2).getY());
        assertEquals("0,5", shortestPath.get(3).getX() + "," + shortestPath.get(3).getY());
        assertEquals("0,6", shortestPath.get(4).getX() + "," + shortestPath.get(4).getY());
        assertEquals("0,7", shortestPath.get(5).getX() + "," + shortestPath.get(5).getY());
        assertEquals("1,7", shortestPath.get(6).getX() + "," + shortestPath.get(6).getY());
        assertEquals("2,7", shortestPath.get(7).getX() + "," + shortestPath.get(7).getY());
    }

    /**
     * Tests shortest path with an ordinary scenario
     */
    @Test
    public void testScenario2() {
        Map<String, ILocationInfo> locations = getLocationsMap();
        Location loc11 = (Location) locations.get("1,1");
        loc11.setRock(true);
        Location loc21 = (Location) locations.get("2,1");
        loc21.setRock(true);
        Location loc41 = (Location) locations.get("4,1");
        loc41.setRock(true);
        Location loc42 = (Location) locations.get("4,2");
        loc42.setRock(true);
        Location loc33 = (Location) locations.get("3,3");
        loc33.setRock(true);
        Location loc43 = (Location) locations.get("4,3");
        loc43.setRock(true);
        Location loc54 = (Location) locations.get("5,4");
        loc54.setRock(true);
        Location loc64 = (Location) locations.get("6,4");
        loc64.setRock(true);
        Location loc74 = (Location) locations.get("7,4");
        loc74.setRock(true);
        Location loc84 = (Location) locations.get("8,4");
        loc84.setRock(true);
        Location loc15 = (Location) locations.get("1,5");
        loc15.setRock(true);
        Location loc25 = (Location) locations.get("2,5");
        loc25.setRock(true);
        Location loc35 = (Location) locations.get("3,5");
        loc35.setRock(true);
        Location loc55 = (Location) locations.get("5,5");
        loc55.setRock(true);
        Location loc26 = (Location) locations.get("2,6");
        loc26.setRock(true);
        Location loc56 = (Location) locations.get("5,6");
        loc56.setRock(true);
        Location loc27 = (Location) locations.get("2,7");
        loc27.setRock(true);
        Location loc57 = (Location) locations.get("5,7");
        loc57.setRock(true);
        Location loc28 = (Location) locations.get("2,8");
        loc28.setRock(true);
        Location loc29 = (Location) locations.get("2,9");
        loc29.setRock(true);

        cm.addTiles(getLocationsList());
        board = new Board(cm.getWorldSizeX(), cm.getWorldSizeY());

        antwarsGUI = new DummyGraphicsAntWarsGUI(false);
        factory = new AntWarsGameCtrl(antwarsGUI, board, onFinished);
        ai = new JT_Destroyer();
        teamInfo = new TeamInfo(1, "Test team");

        ILocationInfo start = new Location(6, 3);
        ILocationInfo goal = new Location(0, 9);

        IAntInfo ant = new LogicAnt(factory, board, (Location) start, 2/*direction*/, 1, teamInfo, EAntType.SCOUT, ai);

        ShortestPath sp = new ShortestPath(ant, start, goal, cm);
        List<ILocationInfo> shortestPath = sp.getShortestPath();

        assertEquals("5,3", shortestPath.get(0).getX() + "," + shortestPath.get(0).getY());
        assertEquals("5,2", shortestPath.get(1).getX() + "," + shortestPath.get(1).getY());
        assertEquals("5,1", shortestPath.get(2).getX() + "," + shortestPath.get(2).getY());
        assertEquals("5,0", shortestPath.get(3).getX() + "," + shortestPath.get(3).getY());
        assertEquals("4,0", shortestPath.get(4).getX() + "," + shortestPath.get(4).getY());
        assertEquals("3,0", shortestPath.get(5).getX() + "," + shortestPath.get(5).getY());
        assertEquals("2,0", shortestPath.get(6).getX() + "," + shortestPath.get(6).getY());
        assertEquals("1,0", shortestPath.get(7).getX() + "," + shortestPath.get(7).getY());
        assertEquals("0,0", shortestPath.get(8).getX() + "," + shortestPath.get(8).getY());
        assertEquals("0,1", shortestPath.get(8).getX() + "," + shortestPath.get(9).getY());
        assertEquals("0,2", shortestPath.get(9).getX() + "," + shortestPath.get(10).getY());
        assertEquals("0,3", shortestPath.get(10).getX() + "," + shortestPath.get(11).getY());
        assertEquals("0,4", shortestPath.get(11).getX() + "," + shortestPath.get(12).getY());
        assertEquals("0,5", shortestPath.get(12).getX() + "," + shortestPath.get(13).getY());
        assertEquals("0,6", shortestPath.get(13).getX() + "," + shortestPath.get(14).getY());
        assertEquals("0,7", shortestPath.get(14).getX() + "," + shortestPath.get(15).getY());
        assertEquals("0,8", shortestPath.get(15).getX() + "," + shortestPath.get(16).getY());
        assertEquals("0,9", shortestPath.get(16).getX() + "," + shortestPath.get(17).getY());
    }

    /**
     * Tests shortest path with an ordinary scenario
     */
    @Test
    public void testScenario3() {
        Map<String, ILocationInfo> locations = getLocationsMap();
        Location loc40 = (Location) locations.get("4,0");
        loc40.setRock(true);
        Location loc01 = (Location) locations.get("0,1");
        loc01.setRock(true);
        Location loc11 = (Location) locations.get("1,1");
        loc11.setRock(true);
        Location loc21 = (Location) locations.get("2,1");
        loc21.setRock(true);
        Location loc41 = (Location) locations.get("4,1");
        loc41.setRock(true);
        Location loc42 = (Location) locations.get("4,2");
        loc42.setRock(true);
        Location loc33 = (Location) locations.get("3,3");
        loc33.setRock(true);
        Location loc43 = (Location) locations.get("4,3");
        loc43.setRock(true);
        Location loc54 = (Location) locations.get("5,4");
        loc54.setRock(true);
        Location loc64 = (Location) locations.get("6,4");
        loc64.setRock(true);
        Location loc74 = (Location) locations.get("7,4");
        loc74.setRock(true);
        Location loc15 = (Location) locations.get("1,5");
        loc15.setRock(true);
        Location loc25 = (Location) locations.get("2,5");
        loc25.setRock(true);
        Location loc35 = (Location) locations.get("3,5");
        loc35.setRock(true);
        Location loc55 = (Location) locations.get("5,5");
        loc55.setRock(true);
        Location loc26 = (Location) locations.get("2,6");
        loc26.setRock(true);
        Location loc56 = (Location) locations.get("5,6");
        loc56.setRock(true);
        Location loc27 = (Location) locations.get("2,7");
        loc27.setRock(true);
        Location loc57 = (Location) locations.get("5,7");
        loc57.setRock(true);
        Location loc28 = (Location) locations.get("2,8");
        loc28.setRock(true);
        Location loc29 = (Location) locations.get("2,9");
        loc29.setRock(true);

        cm.addTiles(getLocationsList());
        board = new Board(cm.getWorldSizeX(), cm.getWorldSizeY());

        antwarsGUI = new DummyGraphicsAntWarsGUI(false);
        factory = new AntWarsGameCtrl(antwarsGUI, board, onFinished);
        ai = new JT_Destroyer();
        teamInfo = new TeamInfo(1, "Test team");

        ILocationInfo start = new Location(6, 3);
        ILocationInfo goal = new Location(0, 9);

        IAntInfo ant = new LogicAnt(factory, board, (Location) start, 3/*direction*/, 1, teamInfo, EAntType.SCOUT, ai);

        ShortestPath sp = new ShortestPath(ant, start, goal, cm);
        List<ILocationInfo> shortestPath = sp.getShortestPath();

        assertEquals("7,3", shortestPath.get(0).getX() + "," + shortestPath.get(0).getY());
        assertEquals("8,3", shortestPath.get(1).getX() + "," + shortestPath.get(1).getY());
        assertEquals("8,4", shortestPath.get(2).getX() + "," + shortestPath.get(2).getY());
        assertEquals("8,5", shortestPath.get(3).getX() + "," + shortestPath.get(3).getY());
        assertEquals("8,6", shortestPath.get(4).getX() + "," + shortestPath.get(4).getY());
        assertEquals("8,7", shortestPath.get(5).getX() + "," + shortestPath.get(5).getY());
        assertEquals("8,8", shortestPath.get(6).getX() + "," + shortestPath.get(6).getY());
        assertEquals("8,9", shortestPath.get(7).getX() + "," + shortestPath.get(7).getY());
        assertEquals("7,9", shortestPath.get(8).getX() + "," + shortestPath.get(8).getY());
        assertEquals("6,9", shortestPath.get(9).getX() + "," + shortestPath.get(9).getY());
        assertEquals("5,9", shortestPath.get(10).getX() + "," + shortestPath.get(10).getY());
        assertEquals("4,9", shortestPath.get(11).getX() + "," + shortestPath.get(11).getY());
        assertEquals("4,8", shortestPath.get(12).getX() + "," + shortestPath.get(12).getY());
        assertEquals("4,7", shortestPath.get(13).getX() + "," + shortestPath.get(13).getY());
        assertEquals("4,6", shortestPath.get(14).getX() + "," + shortestPath.get(14).getY());
        assertEquals("4,5", shortestPath.get(15).getX() + "," + shortestPath.get(15).getY());
        assertEquals("4,4", shortestPath.get(16).getX() + "," + shortestPath.get(16).getY());
        assertEquals("3,4", shortestPath.get(17).getX() + "," + shortestPath.get(17).getY());
        assertEquals("2,4", shortestPath.get(18).getX() + "," + shortestPath.get(18).getY());
        assertEquals("1,4", shortestPath.get(19).getX() + "," + shortestPath.get(19).getY());
        assertEquals("0,4", shortestPath.get(20).getX() + "," + shortestPath.get(20).getY());
        assertEquals("0,5", shortestPath.get(21).getX() + "," + shortestPath.get(21).getY());
        assertEquals("0,6", shortestPath.get(22).getX() + "," + shortestPath.get(22).getY());
        assertEquals("0,7", shortestPath.get(23).getX() + "," + shortestPath.get(23).getY());
        assertEquals("0,8", shortestPath.get(24).getX() + "," + shortestPath.get(24).getY());
        assertEquals("0,9", shortestPath.get(25).getX() + "," + shortestPath.get(25).getY());
    }

    /**
     * Tests shortest path with an ordinary scenario
     */
    @Test
    public void testScenario4() {
        Map<String, ILocationInfo> locations = getLocationsMap();
        Location loc40 = (Location) locations.get("4,0");
        loc40.setRock(true);
        Location loc21 = (Location) locations.get("2,1");
        loc21.setRock(true);
        Location loc41 = (Location) locations.get("4,1");
        loc41.setRock(true);
        Location loc02 = (Location) locations.get("0,2");
        loc02.setRock(true);
        Location loc12 = (Location) locations.get("1,2");
        loc12.setRock(true);
        Location loc22 = (Location) locations.get("2,2");
        loc22.setRock(true);
        Location loc42 = (Location) locations.get("4,2");
        loc42.setRock(true);
        Location loc62 = (Location) locations.get("6,2");
        loc62.setRock(true);
        Location loc72 = (Location) locations.get("7,2");
        loc72.setRock(true);
        Location loc92 = (Location) locations.get("9,2");
        loc92.setRock(true);
        Location loc43 = (Location) locations.get("4,3");
        loc43.setRock(true);
        Location loc63 = (Location) locations.get("6,3");
        loc63.setRock(true);
        Location loc14 = (Location) locations.get("1,4");
        loc14.setRock(true);
        Location loc24 = (Location) locations.get("2,4");
        loc24.setRock(true);
        Location loc34 = (Location) locations.get("3,4");
        loc34.setRock(true);
        Location loc44 = (Location) locations.get("4,4");
        loc44.setRock(true);
        Location loc64 = (Location) locations.get("6,4");
        loc64.setRock(true);
        Location loc84 = (Location) locations.get("8,4");
        loc84.setRock(true);
        Location loc65 = (Location) locations.get("6,5");
        loc65.setRock(true);
        Location loc06 = (Location) locations.get("0,6");
        loc06.setRock(true);
        Location loc16 = (Location) locations.get("1,6");
        loc16.setRock(true);
        Location loc26 = (Location) locations.get("2,6");
        loc26.setRock(true);
        Location loc36 = (Location) locations.get("3,6");
        loc36.setRock(true);
        Location loc46 = (Location) locations.get("4,6");
        loc46.setRock(true);
        Location loc56 = (Location) locations.get("5,6");
        loc56.setRock(true);
        Location loc66 = (Location) locations.get("6,6");
        loc66.setRock(true);
        Location loc76 = (Location) locations.get("7,6");
        loc76.setRock(true);
        Location loc86 = (Location) locations.get("8,6");
        loc86.setRock(true);
        Location loc27 = (Location) locations.get("2,7");
        loc27.setRock(true);
        Location loc47 = (Location) locations.get("4,7");
        loc47.setRock(true);
        Location loc28 = (Location) locations.get("2,8");
        loc28.setRock(true);
        Location loc48 = (Location) locations.get("4,8");
        loc48.setRock(true);
        Location loc68 = (Location) locations.get("6,8");
        loc68.setRock(true);
        Location loc69 = (Location) locations.get("6,9");
        loc69.setRock(true);

        cm.addTiles(getLocationsList());
        board = new Board(cm.getWorldSizeX(), cm.getWorldSizeY());

        antwarsGUI = new DummyGraphicsAntWarsGUI(false);
        factory = new AntWarsGameCtrl(antwarsGUI, board, onFinished);
        ai = new JT_Destroyer();
        teamInfo = new TeamInfo(1, "Test team");

        ILocationInfo start = new Location(1, 1);
        ILocationInfo goal = new Location(0, 7);

        IAntInfo ant = new LogicAnt(factory, board, (Location) start, 3/*direction*/, 1, teamInfo, EAntType.SCOUT, ai);

        ShortestPath sp = new ShortestPath(ant, start, goal, cm);
        List<ILocationInfo> shortestPath = sp.getShortestPath();

        assertEquals("1,0", shortestPath.get(0).getX() + "," + shortestPath.get(0).getY());
        assertEquals("2,0", shortestPath.get(1).getX() + "," + shortestPath.get(1).getY());
        assertEquals("3,0", shortestPath.get(2).getX() + "," + shortestPath.get(2).getY());
        assertEquals("3,1", shortestPath.get(3).getX() + "," + shortestPath.get(3).getY());
        assertEquals("3,2", shortestPath.get(4).getX() + "," + shortestPath.get(4).getY());
        assertEquals("3,3", shortestPath.get(5).getX() + "," + shortestPath.get(5).getY());
        assertEquals("2,3", shortestPath.get(6).getX() + "," + shortestPath.get(6).getY());
        assertEquals("1,3", shortestPath.get(7).getX() + "," + shortestPath.get(7).getY());
        assertEquals("0,3", shortestPath.get(8).getX() + "," + shortestPath.get(8).getY());
        assertEquals("0,4", shortestPath.get(9).getX() + "," + shortestPath.get(9).getY());
        assertEquals("0,5", shortestPath.get(10).getX() + "," + shortestPath.get(10).getY());
        assertEquals("1,5", shortestPath.get(11).getX() + "," + shortestPath.get(11).getY());
        assertEquals("2,5", shortestPath.get(12).getX() + "," + shortestPath.get(12).getY());
        assertEquals("3,5", shortestPath.get(13).getX() + "," + shortestPath.get(13).getY());
        assertEquals("4,5", shortestPath.get(14).getX() + "," + shortestPath.get(14).getY());
        assertEquals("5,5", shortestPath.get(15).getX() + "," + shortestPath.get(15).getY());
        assertEquals("5,4", shortestPath.get(16).getX() + "," + shortestPath.get(16).getY());
        assertEquals("5,3", shortestPath.get(17).getX() + "," + shortestPath.get(17).getY());
        assertEquals("5,2", shortestPath.get(18).getX() + "," + shortestPath.get(18).getY());
        assertEquals("5,1", shortestPath.get(19).getX() + "," + shortestPath.get(19).getY());
        assertEquals("6,1", shortestPath.get(20).getX() + "," + shortestPath.get(20).getY());
        assertEquals("7,1", shortestPath.get(21).getX() + "," + shortestPath.get(21).getY());
        assertEquals("8,1", shortestPath.get(22).getX() + "," + shortestPath.get(22).getY());
        assertEquals("8,2", shortestPath.get(23).getX() + "," + shortestPath.get(23).getY());
        assertEquals("8,3", shortestPath.get(24).getX() + "," + shortestPath.get(24).getY());
        assertEquals("9,3", shortestPath.get(25).getX() + "," + shortestPath.get(25).getY());
        assertEquals("9,4", shortestPath.get(26).getX() + "," + shortestPath.get(26).getY());
        assertEquals("9,5", shortestPath.get(27).getX() + "," + shortestPath.get(27).getY());
        assertEquals("9,6", shortestPath.get(28).getX() + "," + shortestPath.get(28).getY());
        assertEquals("9,7", shortestPath.get(29).getX() + "," + shortestPath.get(29).getY());
        assertEquals("8,7", shortestPath.get(30).getX() + "," + shortestPath.get(30).getY());
        assertEquals("7,7", shortestPath.get(31).getX() + "," + shortestPath.get(31).getY());
        assertEquals("6,7", shortestPath.get(32).getX() + "," + shortestPath.get(32).getY());
        assertEquals("5,7", shortestPath.get(33).getX() + "," + shortestPath.get(33).getY());
        assertEquals("5,8", shortestPath.get(34).getX() + "," + shortestPath.get(34).getY());
        assertEquals("5,9", shortestPath.get(35).getX() + "," + shortestPath.get(35).getY());
        assertEquals("4,9", shortestPath.get(36).getX() + "," + shortestPath.get(36).getY());
        assertEquals("3,9", shortestPath.get(37).getX() + "," + shortestPath.get(37).getY());
        assertEquals("2,9", shortestPath.get(38).getX() + "," + shortestPath.get(38).getY());
        assertEquals("1,9", shortestPath.get(39).getX() + "," + shortestPath.get(39).getY());
        assertEquals("0,9", shortestPath.get(40).getX() + "," + shortestPath.get(40).getY());
        assertEquals("0,8", shortestPath.get(41).getX() + "," + shortestPath.get(41).getY());
        assertEquals("0,7", shortestPath.get(42).getX() + "," + shortestPath.get(42).getY());
    }

    /**
     * Tests shortest path with an ordinary scenario
     */
    @Test
    public void testScenario5() {
        Map<String, ILocationInfo> locations = getLocationsMap();

        Location loc33 = (Location) locations.get("3,3");
        loc33.setRock(true);
        Location loc34 = (Location) locations.get("3,4");
        loc34.setRock(true);
        Location loc35 = (Location) locations.get("3,5");
        loc35.setRock(true);
        Location loc36 = (Location) locations.get("3,6");
        loc36.setRock(true);
        Location loc37 = (Location) locations.get("3,7");
        loc37.setRock(true);

        cm.addTiles(getLocationsList());
        board = new Board(cm.getWorldSizeX(), cm.getWorldSizeY());

        antwarsGUI = new DummyGraphicsAntWarsGUI(false);
        factory = new AntWarsGameCtrl(antwarsGUI, board, onFinished);
        ai = new JT_Destroyer();
        teamInfo = new TeamInfo(1, "Test team");

        ILocationInfo start = new Location(1, 5);
        ILocationInfo goal = new Location(6, 5);

        IAntInfo ant = new LogicAnt(factory, board, (Location) start, 3/*direction*/, 1, teamInfo, EAntType.SCOUT, ai);

        ShortestPath sp = new ShortestPath(ant, start, goal, cm);
        List<ILocationInfo> shortestPath = sp.getShortestPath();

        assertEquals("2,5", shortestPath.get(0).getX() + "," + shortestPath.get(0).getY());
        assertEquals("2,4", shortestPath.get(1).getX() + "," + shortestPath.get(1).getY());
        assertEquals("2,3", shortestPath.get(2).getX() + "," + shortestPath.get(2).getY());
        assertEquals("2,2", shortestPath.get(3).getX() + "," + shortestPath.get(3).getY());
        assertEquals("3,2", shortestPath.get(4).getX() + "," + shortestPath.get(4).getY());
        assertEquals("4,2", shortestPath.get(5).getX() + "," + shortestPath.get(5).getY());
        assertEquals("5,2", shortestPath.get(6).getX() + "," + shortestPath.get(6).getY());
        assertEquals("6,2", shortestPath.get(7).getX() + "," + shortestPath.get(7).getY());
        assertEquals("6,3", shortestPath.get(8).getX() + "," + shortestPath.get(8).getY());
        assertEquals("6,4", shortestPath.get(9).getX() + "," + shortestPath.get(9).getY());
        assertEquals("6,5", shortestPath.get(10).getX() + "," + shortestPath.get(10).getY());
    }

     /**
     * Tests shortest path with an ordinary scenario and when there is an ant on goal (which is allowed)
     */
    @Test
    public void testScenario6() {
        Map<String, ILocationInfo> locations = getLocationsMap();

        Location loc33 = (Location) locations.get("3,3");
        LogicAnt queen = new LogicAnt(factory, board, loc33, 0, 0, teamInfo, EAntType.QUEEN, ai);
        loc33.setAnt(queen);

        cm.addTiles(getLocationsList());
        board = new Board(cm.getWorldSizeX(), cm.getWorldSizeY());
        antwarsGUI = new DummyGraphicsAntWarsGUI(false);
        factory = new AntWarsGameCtrl(antwarsGUI, board, onFinished);
        ai = new JT_Destroyer();
        teamInfo = new TeamInfo(1, "Test team");

        ILocationInfo start = new Location(1, 5);
        ILocationInfo goal = new Location(3, 3);

        IAntInfo ant = new LogicAnt(factory, board, (Location) start, 3/*direction*/, 1, teamInfo, EAntType.SCOUT, ai);

        ShortestPath sp = new ShortestPath(ant, start, goal, cm);
        List<ILocationInfo> shortestPath = sp.getShortestPath();

        assertEquals("2,5", shortestPath.get(0).getX() + "," + shortestPath.get(0).getY());
        assertEquals("3,5", shortestPath.get(1).getX() + "," + shortestPath.get(1).getY());
        assertEquals("3,4", shortestPath.get(2).getX() + "," + shortestPath.get(2).getY());
        assertEquals("3,3", shortestPath.get(3).getX() + "," + shortestPath.get(3).getY());
    }

    /**
     * Tests shortest path when no path could be found
     */
    @Test(expected = NullPointerException.class)
    public void testScenario7() {
        Map<String, ILocationInfo> locations = getLocationsMap();

        Location loc23 = (Location) locations.get("2,3");
        loc23.setRock(true);
        Location loc33 = (Location) locations.get("3,3");
        loc33.setRock(true);
        Location loc43 = (Location) locations.get("4,3");
        loc43.setRock(true);
        Location loc53 = (Location) locations.get("5,3");
        loc53.setRock(true);
        Location loc63 = (Location) locations.get("6,3");
        loc63.setRock(true);
        Location loc24 = (Location) locations.get("2,4");
        loc24.setRock(true);
        Location loc25 = (Location) locations.get("2,5");
        loc25.setRock(true);
        Location loc26 = (Location) locations.get("2,6");
        loc26.setRock(true);
        Location loc27 = (Location) locations.get("2,7");
        loc27.setRock(true);
        Location loc37 = (Location) locations.get("3,7");
        loc37.setRock(true);
        Location loc47 = (Location) locations.get("4,7");
        loc47.setRock(true);
        Location loc57 = (Location) locations.get("5,7");
        loc57.setRock(true);
        Location loc67 = (Location) locations.get("6,7");
        loc67.setRock(true);
        Location loc66 = (Location) locations.get("6,6");
        loc66.setRock(true);
        Location loc65 = (Location) locations.get("6,5");
        loc65.setRock(true);
        Location loc64 = (Location) locations.get("6,4");
        loc64.setRock(true);

        cm.addTiles(getLocationsList());
        board = new Board(cm.getWorldSizeX(), cm.getWorldSizeY());

        antwarsGUI = new DummyGraphicsAntWarsGUI(false);
        factory = new AntWarsGameCtrl(antwarsGUI, board, onFinished);
        ai = new JT_Destroyer();
        teamInfo = new TeamInfo(1, "Test team");

        ILocationInfo start = new Location(4, 5);
        ILocationInfo goal = new Location(8, 8);

        IAntInfo ant = new LogicAnt(factory, board, (Location) start, 0/*direction*/, 1, teamInfo, EAntType.SCOUT, ai);

        ShortestPath sp = new ShortestPath(ant, start, goal, cm);
        List<ILocationInfo> shortestPath = sp.getShortestPath();

        assertEquals("exception", shortestPath.get(0).getX() + "," + shortestPath.get(0).getY());
    }

    private Map getLocationsMap() {
        visibleLocations = new HashMap();

        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
                ILocationInfo loc = new Location(x, y);
                visibleLocations.put(x + "," + y, loc);
            }
        }
        return visibleLocations;
    }

    private List getLocationsList() {
        List<ILocationInfo> list = new ArrayList(visibleLocations.values());
        return list;
    }
}
