package utility;

import aiantwars.EAction;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Tobias Jacobsen
 */
public class Action {

    public static EAction getRandomAction(List<EAction> possibleActions) {
        Random rnd = new Random();
        EAction action = possibleActions.get(rnd.nextInt(possibleActions.size()));
        if (action == EAction.Attack || action == EAction.DropFood || action == EAction.DropSoil || action == EAction.LayEgg) {
            action = EAction.Pass;
        }
        return action;
    }
}
