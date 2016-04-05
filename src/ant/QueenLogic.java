package ant;

import aiantwars.EAction;
import aiantwars.IAntInfo;
import aiantwars.ILocationInfo;
import algorithm.ShortestPath;
import java.util.List;
import memory.CollectiveMemory;

public class QueenLogic {

    private final CollectiveMemory cm = CollectiveMemory.getInstance();
    private IAntInfo thisAnt;
    private ILocationInfo thisLocation;
    private List<EAction> possibleActions;

    private static QueenLogic instance = null;

    private QueenLogic() {
    }

    public static QueenLogic getInstance() {
        if (instance == null) {
            instance = new QueenLogic();
        }
        return instance;
    }

    public EAction getAction(IAntInfo thisAnt, ILocationInfo thisLocation, List<EAction> possibleActions, List<ILocationInfo> visibleLocations, int turn) {
        this.thisAnt = thisAnt;
        this.thisLocation = thisLocation;
        this.possibleActions = possibleActions;
        EAction action = EAction.Pass;

        if (turn <= 20) {
            ShortestPath sp = new ShortestPath(thisAnt, thisLocation, cm.getQueenSpawn());
            List<ILocationInfo> path = sp.getShortestPath();

            if (thisLocation.getFoodCount() <= 1 && path.size() <= 3) {
                cm.addBreedingGround(thisLocation);
            }
        }
        if (possibleActions.contains(EAction.LayEgg)) {

            if (thisAnt.getFoodLoad() >= 5) {
                if (cm.getBreedingGrounds().contains(visibleLocations.get(0))) {
                    action = EAction.LayEgg;
                }

            }
        } else if (possibleActions.contains(EAction.PickUpFood)) {
            action = EAction.PickUpFood;
        }

        return action;
    }

}
