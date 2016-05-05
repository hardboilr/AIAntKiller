package a3.behaviour;

import static a3.utility.Debug.println;
import aiantwars.IAntInfo;
import java.util.List;

public class Breed {

    public int getBreedingAction(List<IAntInfo> ants, int turn) {
        //First ant is allways ant type "Carrier" before turn 50
        if (ants.size() == 1 && turn <= 50) {
            return 0;
            //Second ant is allways ant type "Scout" before turn 50
        } else if (ants.size() == 2 && turn <= 50) {
            return 1;
        }
        return getAntType(ants, turn);
    }

    private static int getAntType(List<IAntInfo> ants, int turn) {
        int antReturn = 0;
        int scoutCount = 0;
        int carrierCount = 0;
        int warriorCount = 0;

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
                    warriorCount++;
                    break;
                default:
                    break;
            }
        }
        println("Ant Count, Scouts: " + scoutCount + ", Carriers: " + carrierCount + ", Warriers: " + warriorCount);

        //Before up until turn 50 always 2 of each ant type
        if (turn <= 50) {
            if (carrierCount < 2) {
                antReturn = 0;
            } else if (scoutCount < 2) {
                antReturn = 1;
            } else if (warriorCount < 2) {
                antReturn = 2;
            }
        }

        //After turn 50 always 4 warriers and 2 of the other ant types
        if (turn > 50) {
            if (warriorCount < 4) {
                antReturn = 0;
            } else if (carrierCount < 2) {
                antReturn = 1;
            } else if (scoutCount < 2) {
                antReturn = 2;
            }
        }

        //If above conditions are met, ant type is selected based on the total number of ants
        if (turn > 100) {
            if (scoutCount + warriorCount + carrierCount % 2 == 0) {
                antReturn = 0;
            } else if (scoutCount + warriorCount + carrierCount % 3 == 1) {
                antReturn = 1;
            } else if (scoutCount + warriorCount + carrierCount % 4 == 1) {
                antReturn = 2;
            } else {
                antReturn = 0;
            }
        }

        return antReturn;
    }
}
