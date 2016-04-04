package comparator;

import java.util.Comparator;
import memory.Tile;

/**
 * Sort in ascending order by foodCost (lowest first). If two items has equal
 * foodCost, sort by foodCount in descending order (highest first).
 *
 * @author Tobias Jacobsen
 */
public class FoodCostComparator implements Comparator<Tile> {

    @Override
    public int compare(Tile o1, Tile o2) {
        if (o1.getFoodCost() < o2.getFoodCost()) {
            return -1;
        } else if (o1.getFoodCount() > o2.getFoodCount()) {
            return -1;
        }
        return 0;
    }
}
