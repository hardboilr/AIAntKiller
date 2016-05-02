package a3.utility;

import aiantwars.EAction;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 *
 * @author Tobias Jacobsen
 */
public class Action {

    private static double turnLeftWeight;
    private static double turnRightWeight;
    private static double moveForwardWeight;
    private static double moveBackwardWeight;
    private static double attackWeight;
    private static double pickUpFoodWeight;
    private static double dropFoodWeight;
    private static double eatFoodWeight;
    private static double digOutWeight;
    private static double dropSoilWeight;
    private static double layEggWeight;
    private static double passWeight;
    private static boolean once = false;
    private static Map<String, Double> weightMap;

    /**
     * Returns random action based on weights
     *
     * @param possibleActions
     * @return random action
     */
    public static EAction getRandomAction(List<EAction> possibleActions) {
        if (!once) {
            init();
            once = true;
        }

        int noOfPossibleActions = possibleActions.size();

        if (noOfPossibleActions > 1) {

            EAction action = EAction.Pass;
            Random rnd = new Random();
            double roll = rnd.nextDouble();

            // sum all weights
            double weightSum = 0;
            for (EAction ac : possibleActions) {
                weightSum += weightMap.get(ac.toString());
            }

            // go through each possible action and see if rolled probability is within that probability
            double probStart = 0;
            for (EAction ac : possibleActions) {

                double probCurrent = weightMap.get(ac.toString()) / weightSum;

                if (probCurrent == 0) {
                    continue;
                }
                double probEnd = probStart + probCurrent;

                if (roll >= probStart && roll <= probEnd) {
                    action = ac;
                    break;
                }
                probStart = probEnd;
            }
            return action;
        }
        return possibleActions.get(0);
    }

    public static void init() {
        //default weights
        turnLeftWeight = 75;
        turnRightWeight = 75;
        moveForwardWeight = 100;
        moveBackwardWeight = 20;
        attackWeight = 0;
        pickUpFoodWeight = 10;
        dropFoodWeight = 0;
        eatFoodWeight = 5;
        digOutWeight = 0;
        dropSoilWeight = 0;
        layEggWeight = 0;
        passWeight = 5;

        weightMap = new HashMap();
        weightMap.put(EAction.TurnLeft.toString(), turnLeftWeight);
        weightMap.put(EAction.TurnRight.toString(), turnRightWeight);
        weightMap.put(EAction.MoveForward.toString(), moveForwardWeight);
        weightMap.put(EAction.MoveBackward.toString(), moveBackwardWeight);
        weightMap.put(EAction.Attack.toString(), attackWeight);
        weightMap.put(EAction.PickUpFood.toString(), pickUpFoodWeight);
        weightMap.put(EAction.DropFood.toString(), dropFoodWeight);
        weightMap.put(EAction.EatFood.toString(), eatFoodWeight);
        weightMap.put(EAction.DigOut.toString(), digOutWeight);
        weightMap.put(EAction.DropSoil.toString(), dropSoilWeight);
        weightMap.put(EAction.LayEgg.toString(), layEggWeight);
        weightMap.put(EAction.Pass.toString(), passWeight);
    }

    public static double getTurnLeftWeight() {
        return turnLeftWeight;
    }

    public static void setTurnLeftWeight(double turnLeftWeight) {
        Action.turnLeftWeight = turnLeftWeight;
    }

    public static double getTurnRightWeight() {
        return turnRightWeight;
    }

    public static void setTurnRightWeight(double turnRightWeight) {
        Action.turnRightWeight = turnRightWeight;
    }

    public static double getMoveForwardWeight() {
        return moveForwardWeight;
    }

    public static void setMoveForwardWeight(double moveForwardWeight) {
        Action.moveForwardWeight = moveForwardWeight;
    }

    public static double getMoveBackwardWeight() {
        return moveBackwardWeight;
    }

    public static void setMoveBackwardWeight(double moveBackwardWeight) {
        Action.moveBackwardWeight = moveBackwardWeight;
    }

    public static double getAttackWeight() {
        return attackWeight;
    }

    public static void setAttackWeight(double attackWeight) {
        Action.attackWeight = attackWeight;
    }

    public static double getPickUpFoodWeight() {
        return pickUpFoodWeight;
    }

    public static void setPickUpFoodWeight(double pickUpFoodWeight) {
        Action.pickUpFoodWeight = pickUpFoodWeight;
    }

    public static double getDropFoodWeight() {
        return dropFoodWeight;
    }

    public static void setDropFoodWeight(double dropFoodWeight) {
        Action.dropFoodWeight = dropFoodWeight;
    }

    public static double getEatFoodWeight() {
        return eatFoodWeight;
    }

    public static void setEatFoodWeight(double eatFoodWeight) {
        Action.eatFoodWeight = eatFoodWeight;
    }

    public static double getDigOutWeight() {
        return digOutWeight;
    }

    public static void setDigOutWeight(double digOutWeight) {
        Action.digOutWeight = digOutWeight;
    }

    public static double getDropSoilWeight() {
        return dropSoilWeight;
    }

    public static void setDropSoilWeight(double dropSoilWeight) {
        Action.dropSoilWeight = dropSoilWeight;
    }

    public static double getLayEggWeight() {
        return layEggWeight;
    }

    public static void setLayEggWeight(double layEggWeight) {
        Action.layEggWeight = layEggWeight;
    }

    public static double getPassWeight() {
        return passWeight;
    }

    public static void setPassWeight(double passWeight) {
        Action.passWeight = passWeight;
    }

}
