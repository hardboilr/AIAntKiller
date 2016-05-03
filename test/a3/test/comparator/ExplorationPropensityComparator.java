package a3.test.comparator;

import a3.memory.Tile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Tobias Jacobsen
 */
public class ExplorationPropensityComparator {

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
}
