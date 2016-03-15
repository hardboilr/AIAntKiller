package memory;

import aiantwars.IAntInfo;
import aiantwars.ILocationInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Jonas Rafn
 */
public class CollectiveMemory {

    private static CollectiveMemory instance = null;
    private final Map<Position, Tile> memory = new HashMap();
    private final List<IAntInfo> ants = new ArrayList();

    private CollectiveMemory() {
    }

    public static CollectiveMemory getInstance() {
        if (instance == null) {
            instance = new CollectiveMemory();
        }
        return instance;
    }

    public void addVisibleLocations(List<ILocationInfo> visibleLocations) {
        for (ILocationInfo visibleLocation : visibleLocations) {
            Position pos = new Position(visibleLocation.getX(), visibleLocation.getY());
            int foodCount = visibleLocation.getFoodCount();
            IAntInfo ant = visibleLocation.getAnt();
            boolean isFilled = visibleLocation.isFilled();
            boolean isRock = visibleLocation.isRock();

            if (memory.containsKey(pos)) {
                memory.get(pos);
                Tile tile = new Tile(foodCount, ant, isFilled, isRock);
                memory.put(pos, tile);
            } else {
                Tile tile = new Tile(foodCount, ant, isFilled, isRock);
                memory.put(pos, tile);
            }
        }
    }

    public Map<Position, Tile> getMemory() {
        return memory;
    }

    public void addAnt(IAntInfo ant) {
        ants.add(ant);
    }

    public void removeAnt(IAntInfo ant) {
        ants.remove(ant);
    }

    public List<IAntInfo> getAnts() {
        return ants;
    }
}
