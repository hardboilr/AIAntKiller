package a3.ant;

import a3.behaviour.Fight;
import a3.memory.CollectiveMemory;
import static a3.utility.Action.getRandomAction;
import static a3.utility.Debug.println;
import aiantwars.EAction;
import aiantwars.IAntInfo;
import aiantwars.ILocationInfo;
import java.util.List;

/**
 *
 * @author Jonas Rafn
 */
public class WarriorLogic {

    private final IAntInfo thisAnt;
    private final ILocationInfo thisLocation;
    private final List<EAction> possibleActions;
    private final List<ILocationInfo> visibleLocations;
    private final CollectiveMemory cm;

    public WarriorLogic(IAntInfo thisAnt, ILocationInfo thisLocation, List<EAction> possibleActions, List<ILocationInfo> visibleLocations, CollectiveMemory cm) {
        this.thisAnt = thisAnt;
        this.thisLocation = thisLocation;
        this.possibleActions = possibleActions;
        this.visibleLocations = visibleLocations;
        this.cm = cm;
    }

    public EAction getAction() {
        println("Warrior: Current AP: " + thisAnt.getActionPoints() + "| Available actions: " + possibleActions.toString());

        EAction action;

        // Keep foodload healthy
        if (possibleActions.contains(EAction.PickUpFood) && thisAnt.getFoodLoad() < 2) {
            action = EAction.PickUpFood;
            // Attack if ant in front is not on same team
        } else if (possibleActions.contains(EAction.Attack) && visibleLocations.get(0).getAnt().getTeamInfo().getTeamID() != thisAnt.getTeamInfo().getTeamID()) {
            action = EAction.Attack;
            // Get fight action
        } else {
            Fight fight = new Fight(thisAnt, thisLocation, cm, possibleActions);
            action = fight.getAction();
        }

        return checkAction(action);
    }

    private EAction checkAction(EAction action) {
        if (action == EAction.EatFood) {
            action = getRandomAction(possibleActions);
            checkAction(action);
        }
        return action;
    }

}
