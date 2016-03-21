package memory;

import aiantwars.IAntInfo;

/**
 *
 * @author Jonas Rafn
 */
public class Tile {

    private int foodCount;
    private IAntInfo ant;
    private boolean isFilled;
    private boolean isRock;

    public Tile(int foodCount, IAntInfo ant, boolean isFilled, boolean isRock) {
        this.foodCount = foodCount;
        this.ant = ant;
        this.isFilled = isFilled;
        this.isRock = isRock;
    }

    public int getFoodCount() {
        return foodCount;
    }

    public void setFoodCount(int foodCount) {
        this.foodCount = foodCount;
    }

    public IAntInfo getAnt() {
        return ant;
    }

    public void setAnt(IAntInfo ant) {
        this.ant = ant;
    }

    public boolean isFilled() {
        return isFilled;
    }

    public void setIsFilled(boolean isFilled) {
        this.isFilled = isFilled;
    }

    public boolean isRock() {
        return isRock;
    }

    public void setIsRock(boolean isRock) {
        this.isRock = isRock;
    }

    @Override
    public String toString() {
        return "Tile:" + ", isRock:" + isRock;
    }

}
