package a3.ant;

import a3.memory.CollectiveMemory;
import a3.memory.model.EnemySighting;
import static a3.utility.Action.getRandomAction;
import static a3.utility.Debug.println;
import aiantwars.EAction;
import aiantwars.EAntType;
import aiantwars.IAntInfo;
import aiantwars.ILocationInfo;
import java.util.List;

/**
 * WIP
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
        println("Scout: Current AP: " + thisAnt.getActionPoints() + "| Available actions: " + possibleActions.toString());
        EAction action = null;

        // replenish food storage
        if (possibleActions.contains(EAction.PickUpFood) && thisAnt.getFoodLoad() < 5) {
            action = EAction.PickUpFood;
        } else {
            List<EnemySighting> enemySightings = cm.getEnemySightings();
            for (EnemySighting es : enemySightings) {
                IAntInfo enemy = es.getEnemy();
                IAntInfo friendly = es.getFriendly();

                // queen is attacked, send warrior to enemy's location
                if (friendly.getAntType().equals(EAntType.QUEEN)) {
                    ILocationInfo enemyLocation = enemy.getLocation();
                }
            }
        }
        if (action == null || action.equals(EAction.Pass)) {
            return getRandomAction(possibleActions);
        }
        return null;
    }

}
