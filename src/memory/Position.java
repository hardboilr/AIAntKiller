package memory;

/**
 *
 * @author Jonas Rafn
 */
public class Position {

    private final int xPos;
    private final int yPos;

    public Position(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public int getxPos() {
        return xPos;
    }

    public int getyPos() {
        return yPos;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + this.xPos;
        hash = 97 * hash + this.yPos;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Position other = (Position) obj;
        if (this.xPos != other.xPos) {
            return false;
        }
        if (this.yPos != other.yPos) {
            return false;
        }
        return true;
    }

}
