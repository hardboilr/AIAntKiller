package behaviour;

import aiantwars.IAntInfo;
import java.util.List;

public class Breeding {

    public int getBreedingAction(List<IAntInfo> ants, int turn) {
        //First ant is allways ant type "Carrier"
        if (ants.size() == 1 && turn <= 50) {
            return 0;
        } else if (ants.size() == 2 && turn <= 50) {
            return 1;
        }
        return getAntType(ants, turn);
    }

    private static int getAntType(List<IAntInfo> ants, int turn) {
        int scoutCount = 0;
        int carrierCount = 0;
        int warrierCount = 0;

        //Counts number of ant types
        for (IAntInfo ant : ants) {
            switch (ant.getAntType().getTypeName()) {
                case "Scout":
                    scoutCount++;
                    break;
                case "Carrier":
                    carrierCount++;
                    break;
                case "Warrier":
                    warrierCount++;
                    break;
                default:
                    break;
            }
        }
        System.out.println("Ant Count, Scouts: " + scoutCount + ", Carriers: " + carrierCount + ", Warriers: " + warrierCount);

        //Before up until turn 50 always 2 of each ant type
        if (turn <= 50) {
            if (carrierCount < 2) {
                return 0;
            } else if (scoutCount < 2) {
                return 1;
            } else if (warrierCount < 2) {
                return 2;
            }
        }

        //After turn 50 always 4 warriers and 2 of the other ant types
        if (turn > 50) {
            if (warrierCount < 4) {
                return 0;
            } else if (carrierCount < 2) {
                return 1;
            } else if (scoutCount < 2) {
                return 2;
            }
        }

        //If above conditions are met, ant type is selected based on the total number of ants
        if (scoutCount + warrierCount + carrierCount % 2 == 0) {
            return 0;
        } else if (scoutCount + warrierCount + carrierCount % 3 == 1) {
            return 1;
        } else if (scoutCount + warrierCount + carrierCount % 4 == 1) {
            return 2;
        } else {
            return 0;
        }
    }
}
