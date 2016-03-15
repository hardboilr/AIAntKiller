package test;

import behaviour.LocationExtends;
import comparator.FoodCostComparator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * 
 * @author Tobias
 */
public class ComparatorTest {

    @Test
    public void testFoodCostComparator1() {
        System.out.println("--- testFoodCostComparator1 ---");

        List<LocationExtends> path = new ArrayList();

        LocationExtends locNorth = new LocationExtends(0, 1, null, 1, 1);
        locNorth.setMovementCost(3);
        path.add(locNorth);
        System.out.println(locNorth);

        LocationExtends locSouth = new LocationExtends(0, 2, null, 3, 0);
        locSouth.setMovementCost(4);
        path.add(locSouth);
        System.out.println(locSouth);

        LocationExtends locEast = new LocationExtends(1, 0, null, 2, 4);
        locEast.setMovementCost(5);
        path.add(locEast);
        System.out.println(locEast);

        LocationExtends locWest = new LocationExtends(1, 1, null, 4, 6);
        locWest.setMovementCost(5);
        path.add(locWest);
        System.out.println(locWest);

        System.out.println("--- Sorted ---");
        Collections.sort(path, new FoodCostComparator());
        for (LocationExtends location : path) {
            System.out.println(location);
        }
        assertEquals(path.get(0), locWest);
        assertEquals(path.get(1), locEast);
        assertEquals(path.get(2), locNorth);
        assertEquals(path.get(3), locSouth);
        System.out.println("\n");
    }

    @Test
    public void testFoodCostComparator2() {
        System.out.println("--- testFoodCostComparator2 ---");
        List<LocationExtends> path = new ArrayList();

        LocationExtends locNorth = new LocationExtends(0, 1, null, 1, 4);
        locNorth.setMovementCost(3);
        path.add(locNorth);
        System.out.println(locNorth);

        LocationExtends locSouth = new LocationExtends(0, 2, null, 3, 0);
        locSouth.setMovementCost(4);
        path.add(locSouth);
        System.out.println(locSouth);

        LocationExtends locEast = new LocationExtends(1, 0, null, 2, 4);
        locEast.setMovementCost(5);
        path.add(locEast);
        System.out.println(locEast);

        LocationExtends locWest = new LocationExtends(1, 1, null, 4, 6);
        locWest.setMovementCost(5);
        path.add(locWest);
        System.out.println(locWest);

        System.out.println("--- Sorted ---");
        Collections.sort(path, new FoodCostComparator());
        for (LocationExtends location : path) {
            System.out.println(location);
        }
        assertEquals(path.get(0), locWest);
        assertEquals(path.get(1), locNorth);
        assertEquals(path.get(2), locEast);
        assertEquals(path.get(3), locSouth);
        System.out.println("\n");
    }

}
