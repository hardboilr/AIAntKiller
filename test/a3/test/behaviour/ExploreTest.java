package a3.test.behaviour;

import a3.ai.JT_Destroyer;
import a3.behaviour.Explore;
import a3.memory.CollectiveMemory;
import a3.memory.model.Tile;
import a3.test.model.OnGameFinished;
import static a3.utility.Debug.println;
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
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * @author Tobias Jacobsen
 */
public class ExploreTest {

    private final CollectiveMemory cm = new CollectiveMemory();
    private final OnGameFinished onGameFinished;
    private final Board board;
    private final ILocationInfo thisLocation;

    private final IAntInfo thisAnt;

    public ExploreTest() {
        thisLocation = new Location(1, 7);
        onGameFinished = new OnGameFinished();
        cm.saveWorldSizeX(18);
        cm.saveWorldSizeY(10);
        board = new Board(18, 10);
        ITeamInfo teamInfo = new TeamInfo(1, "Test team");
        JT_Destroyer ai = new JT_Destroyer();
        IGraphicsAntWarsGUI antwarsGUI = new DummyGraphicsAntWarsGUI(false);
        AntWarsGameCtrl factory = new AntWarsGameCtrl(antwarsGUI, board, onGameFinished);
        thisAnt = new LogicAnt(factory, board, (Location) thisLocation, 1, 1, teamInfo, EAntType.SCOUT, ai);
    }

    @Test
    public void exploreTest1() {
        println("--- exploreTest1 ---");

        List<ILocationInfo> locs = new ArrayList();

        Location tile02 = new Location(0, 2);
        Location tile03 = new Location(0, 3);
        Location tile04 = new Location(0, 4);
        Location tile05 = new Location(0, 5);
        Location tile06 = new Location(0, 6);
        Location tile07 = new Location(0, 7);
        locs.add(tile02);
        locs.add(tile03);
        locs.add(tile04);
        locs.add(tile05);
        locs.add(tile06);
        locs.add(tile07);

        Location tile12 = new Location(1, 2);
        Location tile13 = new Location(1, 3);
        tile13.setRock(true);
        Location tile14 = new Location(1, 4);
        Location tile15 = new Location(1, 5);
        Location tile16 = new Location(1, 6);
        Location tile17 = new Location(1, 7);
        Location tile18 = new Location(1, 8);
        Location tile19 = new Location(1, 9);
        Location tile110 = new Location(1, 10);
        tile110.setFilled(true);
        locs.add(tile12);
        locs.add(tile13);
        locs.add(tile14);
        locs.add(tile15);
        locs.add(tile16);
        locs.add(tile17);
        locs.add(tile18);
        locs.add(tile19);
        locs.add(tile110);

        Location tile22 = new Location(2, 2);
        tile22.setFilled(true);
        Location tile23 = new Location(2, 3);
        tile23.setRock(true);
        Location tile24 = new Location(2, 4);
        Location tile25 = new Location(2, 5);
        Location tile26 = new Location(2, 6);
        Location tile27 = new Location(2, 7);
        Location tile28 = new Location(2, 8);
        Location tile29 = new Location(2, 9);
        tile29.setFilled(true);
        locs.add(tile22);
        locs.add(tile23);
        locs.add(tile24);
        locs.add(tile25);
        locs.add(tile26);
        locs.add(tile27);
        locs.add(tile28);
        locs.add(tile29);

        Location tile32 = new Location(3, 2);
        Location tile33 = new Location(3, 3);
        Location tile34 = new Location(3, 4);
        Location tile35 = new Location(3, 5);
        tile35.setRock(true);
        Location tile36 = new Location(3, 6);
        tile36.setRock(true);
        Location tile37 = new Location(3, 7);
        tile37.setRock(true);
        locs.add(tile32);
        locs.add(tile33);
        locs.add(tile34);
        locs.add(tile35);
        locs.add(tile36);
        locs.add(tile37);

        cm.addTiles(locs);

        // set frequencies
        cm.getTile(0 + "," + 2).setFrequency(1);
        cm.getTile(0 + "," + 3).setFrequency(1);
        cm.getTile(0 + "," + 4).setFrequency(2);
        cm.getTile(0 + "," + 5).setFrequency(4); // <--- queen spawn
        cm.getTile(0 + "," + 6).setFrequency(2);
        cm.getTile(0 + "," + 7).setFrequency(1);

        cm.getTile(1 + "," + 2).setFrequency(1);
        cm.getTile(1 + "," + 3).setFrequency(1);
        cm.getTile(1 + "," + 4).setFrequency(2);
        cm.getTile(1 + "," + 5).setFrequency(3);
        cm.getTile(1 + "," + 6).setFrequency(2);
        cm.getTile(1 + "," + 7).setFrequency(3);
        cm.getTile(1 + "," + 8).setFrequency(1);
        cm.getTile(1 + "," + 9).setFrequency(1);
        cm.getTile(1 + "," + 10).setFrequency(1);

        cm.getTile(2 + "," + 2).setFrequency(1);
        cm.getTile(2 + "," + 3).setFrequency(2);
        cm.getTile(2 + "," + 4).setFrequency(2);
        cm.getTile(2 + "," + 5).setFrequency(3);
        cm.getTile(2 + "," + 6).setFrequency(1);
        cm.getTile(2 + "," + 7).setFrequency(1);
        cm.getTile(2 + "," + 8).setFrequency(1);
        cm.getTile(2 + "," + 9).setFrequency(1);

        cm.getTile(3 + "," + 2).setFrequency(1);
        cm.getTile(3 + "," + 3).setFrequency(1);
        cm.getTile(3 + "," + 4).setFrequency(2);
        cm.getTile(3 + "," + 5).setFrequency(1);
        cm.getTile(3 + "," + 6).setFrequency(1);
        cm.getTile(3 + "," + 7).setFrequency(1);

        // set queen spawn location
        cm.setQueenSpawn(tile05);

        Explore explore = new Explore(thisAnt, thisLocation, cm);
        
        EAction action = explore.getAction();
        System.out.println(action);
        
    }
}
