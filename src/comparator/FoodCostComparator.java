package comparator;

import behaviour.LocationExtends;
import java.util.Comparator;

/**
 * Sort in ascending order by foodCost (lowest first). If two items has equal
 * foodCost, sort by foodCount in descending order (highest first).
 *
 * @author Tobias Jacobsen
 */
public class FoodCostComparator implements Comparator<LocationExtends> {

    @Override
    public int compare(LocationExtends o1, LocationExtends o2) {
        if (o1.getFoodCost() < o2.getFoodCost()) {
            return -1;
        } else if (o1.getFoodCount() > o2.getFoodCount()) {
            return -1;
        }
        return 0;
    }
}
