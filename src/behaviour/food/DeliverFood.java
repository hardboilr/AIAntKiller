package behaviour.food;

import aiantwars.EAction;
import aiantwars.EAntType;
import aiantwars.IAntInfo;
import aiantwars.ILocationInfo;
import java.util.List;

/**
 * WIP!
 * @author Tobias
 */
public class DeliverFood {

    private final IAntInfo thisAnt;
    private final ILocationInfo thisLocation;
    private final List<ILocationInfo> visibleLocations;

    DeliverFood(IAntInfo thisAnt, ILocationInfo thisLocation, List<ILocationInfo> visibleLocations) {
        this.thisAnt = thisAnt;
        this.thisLocation = thisLocation;
        this.visibleLocations = visibleLocations;
    }

    public EAction getAction() {
        ILocationInfo queenLocation = getQueenLocation();
        
        return null;
        
    }
    
    private ILocationInfo getQueenLocation() {
        for(ILocationInfo loc : visibleLocations) {
            if(loc.getAnt().getAntType().equals(EAntType.QUEEN)) {
                return loc;
            }
        }
        return null;
    }

}
