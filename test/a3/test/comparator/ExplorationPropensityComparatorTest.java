package a3.test.comparator;

import a3.memory.model.Tile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tobias Jacobsen
 */
public class ExplorationPropensityComparatorTest {

    @Test
    public void test1() {
        List<Tile> tiles = new ArrayList();

        Tile tile1 = new Tile(3, 0);
        Tile tile2 = new Tile(6, 0);
        Tile tile3 = new Tile(4, 0);
        Tile tile4 = new Tile(1, 0);
        Tile tile5 = new Tile(5, 0);
        Tile tile6 = new Tile(2, 0);

        tile4.setExplorationPropensity(2.25);
        tile6.setExplorationPropensity(2.30);
        tile1.setExplorationPropensity(5);
        tile1.setDistanceToScout(4);
        tile3.setExplorationPropensity(5);
        tile3.setDistanceToScout(5);
        tile5.setExplorationPropensity(11);
        tile2.setExplorationPropensity(11.5);

        tiles.add(tile1);
        tiles.add(tile2);
        tiles.add(tile3);
        tiles.add(tile4);
        tiles.add(tile5);
        tiles.add(tile6);

        Collections.sort(tiles, Tile.ExplorationPropensityComparator);

        assertEquals(tile4, tiles.get(0));
        assertEquals(tile6, tiles.get(1));
        assertEquals(tile1, tiles.get(2));
        assertEquals(tile3, tiles.get(3));
        assertEquals(tile5, tiles.get(4));
        assertEquals(tile2, tiles.get(5));
    }

    @Test
    public void test2() {
        List<Tile> tiles = new ArrayList();
        
        Tile tile30 = new Tile(3,0);
        Tile tile21 = new Tile(2,1);
        Tile tile40 = new Tile(4,0);
        Tile tile20 = new Tile(2,0);
        Tile tile11 = new Tile(1,1);
        Tile tile00 = new Tile(0,0);
        Tile tile44 = new Tile(4,4);
        
        tile30.setExplorationPropensity(38.0);
        tile30.setDistanceToScout(10);

        tile21.setExplorationPropensity(180.0);

        tile40.setExplorationPropensity(10.0);
        tile40.setDistanceToScout(500);

        tile20.setExplorationPropensity(24.0);
        tile20.setDistanceToScout(1);
        
        tile11.setExplorationPropensity(195.0);
        
        tile00.setExplorationPropensity(42.5);
        tile00.setDistanceToScout(10);
        
        tile44.setExplorationPropensity(42.5);
        tile44.setDistanceToScout(5);
        
        tiles.add(tile30);
        tiles.add(tile21);
        tiles.add(tile40);
        tiles.add(tile20);
        tiles.add(tile11);
        tiles.add(tile00);
        tiles.add(tile44);
        
        Collections.sort(tiles, Tile.ExplorationPropensityComparator);
        
        assertEquals(tile40, tiles.get(0));
        assertEquals(tile20, tiles.get(1));
        assertEquals(tile30, tiles.get(2));
        assertEquals(tile44, tiles.get(3));
        assertEquals(tile00, tiles.get(4));
        assertEquals(tile21, tiles.get(5));
        assertEquals(tile11, tiles.get(6));
        
        
    }
}
