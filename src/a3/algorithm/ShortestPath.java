package a3.algorithm;

import aiantwars.EAction;
import aiantwars.IAntInfo;
import aiantwars.ILocationInfo;
import aiantwars.impl.Location;
import a3.algorithm.model.Board;
import a3.algorithm.model.Node;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import a3.utility.Calc;
import static java.lang.Math.abs;
import java.util.Comparator;
import a3.memory.CollectiveMemory;
import a3.memory.model.Tile;
import static a3.utility.Debug.println;

/**
 *
 * @author Tobias Jacobsen
 */
public class ShortestPath {

    private final IAntInfo ant;
    private final Node startNode;
    private Node goalNode;
    private final Board board;
    private final Node[][] nodes;

    public ShortestPath(IAntInfo ant, ILocationInfo start, Object goal, CollectiveMemory cm) {
        this.ant = ant;
        board = new Board(cm);
        nodes = board.getBoardNodes();
        startNode = nodes[start.getX()][start.getY()];
        startNode.setDirection(ant.getDirection());
        startNode.setgVal(0);
        if (goal instanceof ILocationInfo) {
            goalNode = nodes[((ILocationInfo) goal).getX()][((ILocationInfo) goal).getY()];
        } else if (goal instanceof Tile) {
            goalNode = nodes[((Tile) goal).getX()][((Tile) goal).getY()];
        }
    }

    /**
     * Calculates shortest path to goal using A*.
     *
     * @return List containing path to goal, excluding current location and
     * including goal location. 0 index contains goal. Null if path could not be found (ex. due to
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

                    // calculate new gValue
                    double newG = getGCost(adjacentNode);

                    if (adjacentNode.getgVal() > newG) {
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

                        openList.add(adjacentNode);
                    }
                }
            }

            if (openList.isEmpty()) {
                println("Openlist empty!");
                return null;
            }

            closedList.add(currentNode);

            currentNode = openList.poll();

            //update currentNode direction
            currentNode.setDirection(Calc.getMovementDirection(currentNode.getParent(), currentNode));

            // found goal. Run through parent nodes and save to list 
            if (currentNode == goalNode) {
                List<ILocationInfo> shortestPath = new ArrayList();
                while (true) {
                    ILocationInfo loc = new Location(currentNode.getX(), currentNode.getY());
                    shortestPath.add(loc);
                    currentNode = currentNode.getParent();
                    if (currentNode == startNode) {
                        break;
                    }
                }
                Collections.reverse(shortestPath);
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
        int straightMovementCost = Calc.getMovementCost(EAction.MoveForward, ant.getAntType(), false);
        movementCost += straightMovementDistance * straightMovementCost;

        // calculate optional turn costs for x direction
        if (movementX > 0) {
            if (curX > goalX) {
                direction = 3;
            } else {
                direction = 1;
            }
            movementAction = Calc.getMovementAction(currentNode.getDirection(), direction, false);
            movementCost += Calc.getMovementCost(movementAction, ant.getAntType(), false) - ant.getAntType().getActionCost(EAction.MoveForward);
        }

        // calculate optional turn costs for y direction
        if (movementY > 0) {
            if (curY > goalY) {
                direction = 2;
            } else {
                direction = 0;
            }
            movementAction = Calc.getMovementAction(ant.getDirection(), direction, false);
            movementCost += Calc.getMovementCost(movementAction, ant.getAntType(), false) - ant.getAntType().getActionCost(EAction.MoveForward);
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

        gCost += Calc.getMovementCost(Calc.getMovementAction(thisNode.getParent().getDirection(), thisNode.getDirection(), false), ant.getAntType(), false);

        // also add to gCost, from parent
        // when getParent == null, this should be start node
        if (thisNode.getParent() == startNode) {
        } else {
            Node parent = thisNode.getParent();
            gCost += parent.getgVal();
        }
        return gCost;
    }
}
