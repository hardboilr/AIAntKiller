package algorithm.model;

import java.util.ArrayList;
import java.util.List;
import static utility.Debug.println;

/**
 * @author Tobias
 */
public class Node implements Comparable<Node> {

    private final int x;
    private final int y;
    private int direction;
    private double gVal = Double.POSITIVE_INFINITY; // movement cost from starting node to this node
    private double hVal = Double.POSITIVE_INFINITY;
    ; // estimated movement cost from this node to final destination
    private Node parent;
    private final List<Node> adjacentNodes;

    public Node(int x, int y, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        adjacentNodes = new ArrayList();
    }

    public double getgVal() {
        return gVal;
    }

    public void setgVal(double gVal) {
        this.gVal = gVal;
    }

    public double gethVal() {
        return hVal;
    }

    public void sethVal(double hVal) {
        this.hVal = hVal;
    }

    public double getfVal() {
        return hVal + gVal;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Node getParent() {
        return parent;
    }

    public void setParent(Node parent) {
        this.parent = parent;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public List<Node> getAdjacentNodes() {
        return adjacentNodes;
    }

    public void addAdjacentNode(Node n) {
        adjacentNodes.add(n);
    }

    @Override
    public String toString() {
        return x + "," + y;
    }

    @Override
    public int compareTo(Node o) {
        if (this.getfVal() < o.getfVal()) {
            return -1;
        }
        if (this.getfVal() > o.getfVal()) {
            return 1;
        }
        if (this.gethVal() < o.gethVal()) {
            return -1;
        }
        if (this.gethVal() > o.gethVal()) {
            return 1;
        }
        return 0;
    }

}
