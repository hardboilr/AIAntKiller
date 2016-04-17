package memory;

import aiantwars.IAntInfo;
import aiantwars.ILocationInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectiveMemory {

    private static CollectiveMemory instance = null;
    private final Map<Position, Tile> tiles = new HashMap();
    private final List<IAntInfo> ants = new ArrayList();
    private ILocationInfo queenSpawn;
    private int worldSizeX;
    private int worldSizeY;

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

            if (tiles.containsKey(pos)) {
                tiles.get(pos);
                Tile tile = new Tile(foodCount, ant, isFilled, isRock);
                tiles.put(pos, tile);
            } else {
                Tile tile = new Tile(foodCount, ant, isFilled, isRock);
                tiles.put(pos, tile);
            }
        }
    }

    public void addLocation(ILocationInfo location) {
        Tile tile = new Tile(location);
        tiles.put(new Position(tile.getX(), tile.getY()), tile);
    }

    public Map<Position, Tile> getTiles() {
        return tiles;
    }

    public List<Tile> getTilesAsList() {
        return new ArrayList(tiles.values());
    }

    public Tile getTile(Position pos) {
        return tiles.get(pos);
    }

    public Tile getTile(String pos) {
        String[] positions = pos.split(",");
        return tiles.get(new Position(Integer.parseInt(positions[0]), Integer.parseInt(positions[1])));
    }

    public void clearMemory() {
        instance = new CollectiveMemory();
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

    public void setQueenSpawn(ILocationInfo queenSpawn) {
        this.queenSpawn = this.queenSpawn == null ? queenSpawn : this.queenSpawn;
    }

    public ILocationInfo getQueenSpawn() {
        return queenSpawn;
    }

    public void saveWorldSizeX(int worldSizeX) {
        this.worldSizeX = worldSizeX;
    }

    public void saveWorldSizeY(int worldSizeY) {
        this.worldSizeY = worldSizeY;
    }

    public int getWorldSizeX() {
        return worldSizeX;
    }

    public int getWorldSizeY() {
        return worldSizeY;
    }

}
