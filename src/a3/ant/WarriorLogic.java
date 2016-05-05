package a3.ant;

import a3.memory.CollectiveMemory;
import aiantwars.EAction;
import aiantwars.IAntInfo;
import aiantwars.ILocationInfo;
import java.util.List;

/**
 * @author Tobias Jacobsen
 */
public class WarriorLogic {
    
    private final IAntInfo thisAnt;
    private final ILocationInfo thisLocation;
    private final List<EAction> possibleActions;
    private final CollectiveMemory cm;
    
    public WarriorLogic(IAntInfo thisAnt, ILocationInfo thisLocation, List<EAction> possibleActions, CollectiveMemory cm) {
        this.thisAnt = thisAnt;
        this.thisLocation = thisLocation;
        this.possibleActions = possibleActions;
        this.cm = cm;
    }
    
    public EAction getAction() {
        
        return null;
    }
    
}
