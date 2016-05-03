package a3.ant;

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

        EAction action;

        // replenish food storage
        if (possibleActions.contains(EAction.PickUpFood) && thisAnt.getFoodLoad() < 5) {
            action = EAction.PickUpFood;
        } else { // explore
            Explore explore = new Explore(thisAnt, thisLocation, cm);
            action = explore.getAction();
            if (!possibleActions.contains(action)) {
                action = EAction.Pass;
            }
        }
        // get random action
        if (action.equals(EAction.Pass)) {
            action = getRandomAction(possibleActions);
        }
        return action;
    }

}
