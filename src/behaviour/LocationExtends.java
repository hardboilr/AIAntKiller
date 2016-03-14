package behaviour;

import aiantwars.EAntType;
import aiantwars.IAntInfo;
import aiantwars.impl.Location;
import java.util.logging.Level;
import java.util.logging.Logger;
import utility.Calc;

public class LocationExtends extends Location {

    private int movementCost;
    private int direction;

    public LocationExtends(int x, int y) {
        super(x, y);
    }

    public LocationExtends(int x, int y, IAntInfo ant, int direction, int foodCount) {
        super(x, y);
        super.setFoodCount(foodCount);
        calcMovementCost(ant, direction);
        this.direction = direction;
    }

    public int getFoodCost() {
        return movementCost - getFoodCount();
    }

    private void calcMovementCost(IAntInfo ant, int direction) {
        if (ant != null) {
            int currentDirection = ant.getDirection();
            EAntType antType = ant.getAntType();
            try {
                movementCost = Calc.getMovementCost(Calc.getMovementAction(currentDirection, direction), antType);
            } catch (Exception ex) {
                Logger.getLogger(LocationExtends.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void setMovementCost(int movementCost) {
        this.movementCost = movementCost;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    @Override
    public String toString() {
        return getX() + "," + getY() + " with foodCost: " + getFoodCost() + " and foodCount: " + getFoodCount();
    }
}
