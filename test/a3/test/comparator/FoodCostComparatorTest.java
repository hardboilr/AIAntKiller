package a3.test.comparator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import a3.memory.model.Tile;
import a3.utility.Debug;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import static a3.utility.Debug.println;
import static org.junit.Assert.assertEquals;

/**
 * 
 * @author Tobias
 */
public class FoodCostComparatorTest {
    
    public FoodCostComparatorTest() {
        Debug.isDebug = true;
    }

    @Test
    public void testFoodCostComparator1() {
        println("--- testFoodCostComparator1 ---");

        List<Tile> path = new ArrayList();

        Tile locNorth = new Tile(0, 1, null, 1, 1);
        locNorth.setMovementCost(3);
        path.add(locNorth);
        println(locNorth);

        Tile locSouth = new Tile(0, 2, null, 3, 0);
        locSouth.setMovementCost(4);
        path.add(locSouth);
        println(locSouth);

        Tile locEast = new Tile(1, 0, null, 2, 4);
        locEast.setMovementCost(5);
        path.add(locEast);
        println(locEast);

        Tile locWest = new Tile(1, 1, null, 4, 5);
        locWest.setMovementCost(5);
        path.add(locWest);
        println(locWest);

        println("--- Sorted ---");
        Collections.sort(path, Tile.FoodCostComparator);
        for (Tile location : path) {
            println(location);
        }
        assertEquals(path.get(0), locWest);
        assertEquals(path.get(1), locEast);
        assertEquals(path.get(2), locNorth);
        assertEquals(path.get(3), locSouth);
        println("\n");
    }

    @Test
    public void testFoodCostComparator2() {
        println("--- testFoodCostComparator2 ---");
        List<Tile> path = new ArrayList();

        Tile locNorth = new Tile(0, 1, null, 1, 4);
        locNorth.setMovementCost(3);
        path.add(locNorth);
        println(locNorth);

        Tile locSouth = new Tile(0, 2, null, 3, 0);
        locSouth.setMovementCost(4);
        path.add(locSouth);
        println(locSouth);

        Tile locEast = new Tile(1, 0, null, 2, 4);
        locEast.setMovementCost(5);
        path.add(locEast);
        println(locEast);

        Tile locWest = new Tile(1, 1, null, 4, 6);
        locWest.setMovementCost(5);
        path.add(locWest);
        println(locWest);

        println("--- Sorted ---");
        Collections.sort(path, Tile.FoodCostComparator);
        for (Tile location : path) {
            println(location);
        }
        assertEquals(path.get(0), locWest);
        assertEquals(path.get(1), locNorth);
        assertEquals(path.get(2), locEast);
        assertEquals(path.get(3), locSouth);
        println("\n");
    }

}
