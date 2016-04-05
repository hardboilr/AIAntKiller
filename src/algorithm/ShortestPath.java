package algorithm;

import aiantwars.EAction;
import aiantwars.IAntInfo;
import aiantwars.ILocationInfo;
import aiantwars.impl.Location;
import algorithm.model.Board;
import algorithm.model.Node;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import utility.Calc;
import static utility.Debug.println;
import static java.lang.Math.abs;
import java.util.Comparator;
import static utility.Debug.print;

/**
 * WIP!
 *
 * 1. Failed strategy below! Instead implement wip Board.class
 *
 * @author Tobias
 */
public class ShortestPath {

    private final IAntInfo ant;
    private final Node startNode;
    private final Node goalNode;
    private final Board board;
    private final Node[][] nodes;

    public ShortestPath(IAntInfo ant, ILocationInfo start, ILocationInfo goal) {
        this.ant = ant;
        board = new Board();
        nodes = board.getBoardNodes();
        startNode = nodes[start.getX()][start.getY()];
        startNode.setDirection(ant.getDirection());
        startNode.setgVal(0);
        goalNode = nodes[goal.getX()][goal.getY()];
    }

    /**
     * Calculates shortest path to goal using A*.
     *
     * @return List containing path to goal, excluding current location and
     * including goal location. Null if path could not be found (ex. due to
     * obstructions)
     */
    public List<ILocationInfo> getShortestPath() {
        Set<Node> closedList = new HashSet();
        PriorityQueue<Node> openList = new PriorityQueue(Comparator.naturalOrder());
        Node currentNode = startNode;

        while (true) {

            for (Node adjacentNode : currentNode.getAdjacentNodes()) {

                if (adjacentNode.getParent() == null) {
                    adjacentNode.setParent(currentNode);
                }

                if (!closedList.contains(adjacentNode)) {
                    adjacentNode.setDirection(Calc.getMovementDirection(currentNode, adjacentNode));

                    // for debugging
                    println("Considering: " + adjacentNode + ". Direction: " + adjacentNode.getDirection() + ". Parent: " + adjacentNode.getParent().toString());
                    print(adjacentNode + " has " + adjacentNode.getAdjacentNodes().size() + " adjacent nodes: ");

                    for (Node n : adjacentNode.getAdjacentNodes()) {
                        print("(" + n + ")");
                    }
//                    println("");
                    //---------

                    // calculate new gValue
                    double newG = getGCost(adjacentNode);

                    if (adjacentNode.getgVal() > newG) {
                        println("lower gVal found on: " + adjacentNode);
                        println("setting: " + adjacentNode.getgVal() + " to: " + newG);
                        adjacentNode.setgVal(newG); // update gValue
                    }

                    if (openList.contains(adjacentNode)) {
                        // refresh adjacentNode position in openList
                        adjacentNode.sethVal(getHCost(adjacentNode)); // !!

                        openList.remove(adjacentNode);
                        openList.add(adjacentNode);
                    }

                    if (!openList.contains(adjacentNode)) {
                        // calculate hCost and add node to openList
                        adjacentNode.sethVal(getHCost(adjacentNode));

                        println("hCost for: " + adjacentNode + " with direction: " + adjacentNode.getDirection() + " is: " + adjacentNode.gethVal());

                        openList.add(adjacentNode);
                    }
                    print("\n and gCost: " + adjacentNode.getgVal() + ", " + "hCost: " + adjacentNode.gethVal() + ", fCost: " + adjacentNode.getfVal() + "\n" + "\n"); //debug
                }
            }

            if (openList.isEmpty()) {
                return null;
            }

            closedList.add(currentNode);
            println("##to closedList-> " + currentNode);

            currentNode = openList.poll();

            //update currentNode direction
            currentNode.setDirection(Calc.getMovementDirection(currentNode.getParent(), currentNode));

            println("###Picked with lowest cost--->" + currentNode);

            // found goal. Run through parent nodes and save to list 
            if (currentNode == goalNode) {
                println("found goal!-> " + currentNode.toString());
                List<ILocationInfo> shortestPath = new ArrayList();
                while (true) {
                    println("Adding: " + currentNode.getX() + "," + currentNode.getY());
                    ILocationInfo loc = new Location(currentNode.getX(), currentNode.getY());
                    shortestPath.add(loc);
                    currentNode = currentNode.getParent();
                    if (currentNode == startNode) {
                        break;
                    }
                }
                Collections.reverse(shortestPath);
                println("Path is: ");
                for (ILocationInfo loc : shortestPath) {
                    println(loc.getX() + "," + loc.getY());
                }
                return shortestPath;
            }
        }
    }

    /**
     * Movement cost from current node to goal-node
     *
     * @param currentNode
     * @param goalNode
     * @return movement cost
     */
    private int getHCost(Node currentNode) {
        int movementCost = 0;
        EAction movementAction;
        int direction;

        int curX = currentNode.getX();
        int curY = currentNode.getY();
        int goalX = goalNode.getX();
        int goalY = goalNode.getY();

        int movementX = abs(curX - goalX);
        int movementY = abs(curY - goalY);

        // calculate straight movement costs
        int straightMovementDistance = movementX + movementY;
        print("straightDistance: " + straightMovementDistance + ", ");
        int straightMovementCost = Calc.getMovementCost(EAction.MoveForward, ant.getAntType(), false);
        movementCost += straightMovementDistance * straightMovementCost;
        print("straightMovementCost: " + movementCost + ", ");

        // calculate optional turn costs for x direction
        if (movementX > 0) {
            if (curX > goalX) {
                direction = 3;
            } else {
                direction = 1;
            }
            movementAction = Calc.getMovementAction(currentNode.getDirection(), direction);
            print("currentDirection: " + currentNode.getDirection() + ", ");
            print("movementAction: " + movementAction.toString() + ", ");
            movementCost += Calc.getMovementCost(movementAction, ant.getAntType(), false) - ant.getAntType().getActionCost(EAction.MoveForward);
            print("with optional turn1: " + movementCost + ", ");
        }

        // calculate optional turn costs for y direction
        if (movementY > 0) {
            if (curY > goalY) {
                direction = 2;
            } else {
                direction = 0;
            }
            movementAction = Calc.getMovementAction(ant.getDirection(), direction);
            movementCost += Calc.getMovementCost(movementAction, ant.getAntType(), false) - ant.getAntType().getActionCost(EAction.MoveForward);
            print("with optional turn1: " + movementCost);
        }
        return movementCost;
    }

    /**
     * Movement cost from current node to start-node
     *
     * @param currentNode
     * @param startNode
     * @return
     */
    private int getGCost(Node currentNode) {
        int gCost = 0;
        Node thisNode = currentNode;

        gCost += Calc.getMovementCost(Calc.getMovementAction(thisNode.getParent().getDirection(), thisNode.getDirection()), ant.getAntType(), false);
        println("inital cost: " + gCost);

        // also add to gCost, from parent
        // when getParent == null, this should be start node
        if (thisNode.getParent() == startNode) {
            println("->Parent is: " + thisNode.getParent() + "(goalnode)");
        } else {
            Node parent = thisNode.getParent();
            println("Parent is: " + parent);
            gCost += parent.getgVal();
            println("Adding cost: " + parent.getgVal());
        }
        return gCost;
    }
}
