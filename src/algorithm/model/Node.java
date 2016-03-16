package algorithm.model;

/**
 * Node containing 
 * 
 * @author Tobias
 */
public class Node {

    private final int x;
    private final int y;
    private int direction;
    private double gVal; // movement cost from starting node to this node
    private double hVal; // estimated movement cost from this node to final destination
    private Node parent;
    private Node nodeNorth;
    private Node nodeSouth;
    private Node nodeWest;
    private Node nodeEast;

    public Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Node(int x, int y, Node parent, int direction) {
        this.x = x;
        this.y = y;
        this.direction = direction;
        this.parent = parent;
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

    public double getHVal() {
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

    public Node getNodeNorth() {
        return nodeNorth;
    }

    public void setNodeNorth(Node nodeNorth) {
        this.nodeNorth = nodeNorth;
    }

    public Node getNodeSouth() {
        return nodeSouth;
    }

    public void setNodeSouth(Node nodeSouth) {
        this.nodeSouth = nodeSouth;
    }

    public Node getNodeWest() {
        return nodeWest;
    }

    public void setNodeWest(Node nodeWest) {
        this.nodeWest = nodeWest;
    }

    public Node getNodeEast() {
        return nodeEast;
    }

    public void setNodeEast(Node nodeEast) {
        this.nodeEast = nodeEast;
    }
}
