package a3.logic;

import a3.behaviour.Explore;
import a3.memory.CollectiveMemory;
import static a3.utility.Action.getRandomAction;
import static a3.utility.Debug.println;
import aiantwars.EAction;
import aiantwars.IAntInfo;
import aiantwars.ILocationInfo;
import java.util.List;

/**
 * @author Tobias Jacobsen
 */
public class ScoutLogic {

    private final IAntInfo thisAnt;
    private final ILocationInfo thisLocation;
    private final List<EAction> possibleActions;
    private final CollectiveMemory cm;

    public ScoutLogic(IAntInfo thisAnt, ILocationInfo thisLocation, List<EAction> possibleActions, CollectiveMemory cm) {
        this.thisAnt = thisAnt;
        this.thisLocation = thisLocation;
        this.possibleActions = possibleActions;
        this.cm = cm;
    }

    public EAction getAction() {
        println("Scout: Current AP: " + thisAnt.getActionPoints() + "| Available actions: " + possibleActions.toString());

        // a. when location has food and ant's food load is below 5, then pickup food
        if (possibleActions.contains(EAction.PickUpFood) && thisAnt.getFoodLoad() < 5) {
            return EAction.PickUpFood;
        } else { // b. else explore 
            Explore explore = new Explore(thisAnt, thisLocation, cm);
            EAction action = explore.getAction();
            if (possibleActions.contains(action) && !action.equals(EAction.Pass)) {
                return action;
            }
        }
        // c. If no action was returned, then get random action
        return getRandomAction(possibleActions);
    }
}
