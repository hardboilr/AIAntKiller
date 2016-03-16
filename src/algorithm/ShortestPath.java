package algorithm;

import aiantwars.EAction;
import aiantwars.IAntInfo;
import aiantwars.ILocationInfo;
import aiantwars.impl.Location;
import algorithm.model.Board;
import algorithm.model.Node;
import static java.lang.Math.abs;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;
import memory.CollectiveMemory;
import memory.Position;
import memory.Tile;
import utility.Calc;

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
    private final CollectiveMemory collectiveMemory = CollectiveMemory.getInstance();
    private final PriorityQueue<Node> openList;
    private final Set<Node> closedList;
    private final Board board;
    private final Node[][] nodes;

    public ShortestPath(IAntInfo ant, ILocationInfo start, ILocationInfo goal, int worldSizeX, int worldSizeY) {
        this.ant = ant;
        openList = new PriorityQueue();
        closedList = new HashSet();
        board = new Board(worldSizeX, worldSizeY);
        nodes = board.getBoardNodes();
        startNode = nodes[start.getX()][start.getY()];
        startNode.setDirection(ant.getDirection());
        goalNode = nodes[goal.getX()][goal.getY()];
    }

    public List<ILocationInfo> getShortestPath() throws Exception {

        Node currentNode = startNode;
        int direction;

        while (true) {

            //check if its in closedSet
            //optionally update g-value 
            //Add all adjacent nodes to openList
            if (canCreateNode(currentNode.getX(), currentNode.getY() + 1)) {
                //north
                direction = 0;
                Node northNode = new Node(currentNode.getX(), currentNode.getY() + 1, currentNode, direction);
                northNode.setgVal(getGCost(currentNode, direction));
                northNode.sethVal(getHCost(northNode, goalNode));
                openList.add(northNode);
            }
            if (canCreateNode(currentNode.getX(), currentNode.getY() - 1)) {
                //south
                direction = 2;
                Node southNode = new Node(currentNode.getX(), currentNode.getY() - 1, currentNode, direction);
                southNode.setgVal(getGCost(currentNode, direction));
                southNode.sethVal(getHCost(southNode, goalNode));
                openList.add(southNode);
            }
            if (canCreateNode(currentNode.getX() + 1, currentNode.getY())) {
                //east
                direction = 1;
                Node eastNode = new Node(currentNode.getX() + 1, currentNode.getY(), currentNode, direction);
                eastNode.setgVal(getGCost(currentNode, direction));
                eastNode.sethVal(getHCost(eastNode, goalNode));
                openList.add(eastNode);
            }
            if (canCreateNode(currentNode.getX() - 1, currentNode.getY())) {
                //west
                direction = 3;
                Node westNode = new Node(currentNode.getX() - 1, currentNode.getY(), currentNode, direction);
                westNode.setgVal(getGCost(currentNode, direction));
                westNode.sethVal(getHCost(westNode, goalNode));
                openList.add(westNode);
            }

            closedList.add(currentNode);
            currentNode = openList.poll();

            if (currentNode.getX() == goalNode.getX() && currentNode.getY() == goalNode.getY()) {
                break;
            }
        }

        // found goal. Run through parent nodes and save to list 
        List<ILocationInfo> bestPath = new ArrayList();
        do {
            ILocationInfo loc = new Location(currentNode.getX(), currentNode.getY());
            bestPath.add(loc);
            currentNode = currentNode.getParent();
        } while (currentNode.getParent() != null);

        return bestPath;
    }

    private boolean canCreateNode(int x, int y) {
        Tile tile = collectiveMemory.getMemory().get(new Position(x, y));

        for (Node n : closedList) {
            if (n.getX() == x && n.getY() == y) {
                return false;
            }
        }

        if (tile == null) {
            return true;
        } else if (tile.isFilled() || tile.isRock() || tile.getAnt() != null) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Movement cost from current node to goal-node
     *
     * @param currentNode
     * @param goalNode
     * @return movement cost
     */
    private int getHCost(Node currentNode, Node goalNode) throws Exception {
        //curNode pos: (4,2)
        //goalNode pos: (2,2)
        int movementCost = 0;
        EAction movementAction;
        int direction;

        int curX = currentNode.getX(); // 4
        int curY = currentNode.getY(); // 2
        int goalX = goalNode.getX(); // 2
        int goalY = goalNode.getY(); // 2

        int movementX = abs(curX - goalX); // 2
        int movementY = abs(curY - goalY); // 0

        // calculate straight movement costs
        int straightMovementDistance = movementX + movementY; // 2+0=2
        int straightMovementCost = Calc.getMovementCost(EAction.MoveForward, ant.getAntType(), false); // 3
        movementCost += straightMovementDistance * straightMovementCost; // 2*3=6

        // calculate optional turn costs for x direction
        if (movementX > 0) {
            if (curX > goalX) {
                direction = 3; //west
            } else {
                direction = 1; //east
            }
            movementAction = Calc.getMovementAction(ant.getDirection(), direction);
            movementCost += Calc.getMovementCost(movementAction, ant.getAntType(), false);
        }

        // calculate optional turn costs for y direction
        if (movementY > 0) {
            if (curY > goalY) {
                direction = 2; //south
            } else {
                direction = 0; // north
            }
            movementAction = Calc.getMovementAction(ant.getDirection(), direction);
            movementCost += Calc.getMovementCost(movementAction, ant.getAntType(), false);
        }
        return movementCost; // 10
    }

    /**
     * Movement cost from current node to start-node
     *
     * @param currentNode
     * @param startNode
     * @return
     */
    private int getGCost(Node currentNode, int direction) throws Exception {
        int gCost = 0;
        Node thisNode = currentNode;
        gCost += Calc.getMovementCost(Calc.getMovementAction(thisNode.getDirection(), direction), ant.getAntType(), false);

        // also add to gCost, down the chain of parent nodes
        // when getParent == null, this should be start node
        while (true) {
            if (thisNode.getParent() == null) {
                break;
            } else {
                Node parent = thisNode.getParent();
                gCost += parent.getgVal();
                thisNode = parent;
            }
        }
        return gCost;
    }
}
