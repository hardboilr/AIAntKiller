package algoritm;

import aiantwars.ILocationInfo;
import java.util.List;

public class ShortestPath {

    private final List<ILocationInfo> visibleLocations;
    private final ILocationInfo start;
    private final ILocationInfo goal;

    public ShortestPath(List<ILocationInfo> visibleLocations, ILocationInfo start, ILocationInfo goal) {
        this.visibleLocations = visibleLocations;
        this.start = start;
        this.goal = goal;
    }
    
    public List<ILocationInfo> getShortestPath() {
        
    }
    
    

}
