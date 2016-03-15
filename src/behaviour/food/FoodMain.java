package behaviour.food;

import aiantwars.EAction;
import aiantwars.IAntInfo;
import aiantwars.ILocationInfo;
import java.util.List;

/**
 * WIP!
 * @author Tobias
 */
public class FoodMain {

    private final IAntInfo thisAnt;
    private final ILocationInfo thisLocation;
    private final List<ILocationInfo> visibleLocations;
    private final CollectFood collectFood;
    private final DeliverFood deliverFood;

    public FoodMain(IAntInfo thisAnt, ILocationInfo thisLocation, List<ILocationInfo> visibleLocations) {
        this.thisAnt = thisAnt;
        this.thisLocation = thisLocation;
        this.visibleLocations = visibleLocations;
        this.collectFood = new CollectFood(this.thisAnt, this.thisLocation, this.visibleLocations);
        this.deliverFood = new DeliverFood(this.thisAnt, this.thisLocation, this.visibleLocations);
    }

    public EAction getAction() {
        if (thisAnt.getAntType().getMaxFoodLoad() == thisAnt.getFoodLoad()) {
            // ant has maxed its food storage -> return food to queen
            return deliverFood.getAction();
        } else if (thisLocation.getFoodCount() > 0) {
            // food in current location -> pick it up
            return EAction.PickUpFood;
        } else {
            // find collect in nearby tile
            return collectFood.getEAction();
        }

    }

}
