package a3.memory;

import aiantwars.IAntInfo;
import aiantwars.ILocationInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static a3.utility.Debug.println;

public class CollectiveMemory {

    private Map<Position, Tile> tiles = new HashMap();
    private final List<IAntInfo> ants = new ArrayList();
    private ILocationInfo queenSpawn;
    private int worldSizeX;
    private int worldSizeY;

    public CollectiveMemory() {
    }

    

    public void addTiles(List<ILocationInfo> visibleLocations) {
        for (ILocationInfo visibleLocation : visibleLocations) {
            addTile(visibleLocation);
        }
    }

    public void addTile(ILocationInfo location) {
        Position pos = new Position(location.getX(), location.getY());

        if (tiles.containsKey(pos)) {
            Tile get = tiles.get(pos);
            get.setFoodCount(location.getFoodCount());
            get.setAnt(location.getAnt());
            get.setIsFilled(location.isFilled());
            get.setIsRock(location.isFilled());
            get.setFoodCount(location.getFoodCount());
        } else {
            Tile tile = new Tile(location.getX(), location.getY(), location.getFoodCount(), location.getAnt(), location.isFilled(), location.isRock());
            tiles.put(pos, tile);
        }
    }

    public Map<Position, Tile> getTiles() {
        return tiles;
    }
    
    public void clearTiles() {
        tiles = new HashMap();
    }

    public Tile getTile(Position pos) {
        return tiles.get(pos);
    }

    public Tile getTile(String pos) {
        String[] positions = pos.split(",");
        println("System: getTile(" + pos + ")");
        return tiles.get(new Position(Integer.parseInt(positions[0]), Integer.parseInt(positions[1])));
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