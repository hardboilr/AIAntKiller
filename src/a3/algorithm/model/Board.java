package a3.algorithm.model;

import a3.memory.CollectiveMemory;
import a3.memory.model.Position;
import a3.memory.model.Tile;
import aiantwars.IAntInfo;
import aiantwars.ILocationInfo;
import aiantwars.impl.Location;

/**
 * Creates empty nodes based on world size and add them to two-dimensional
 * array. Is used for pathfinding algorithm.
 *
 * @author Tobias Jacobsen
 */
public class Board {

    private final Node[][] nodes;

    public Board(CollectiveMemory cm, IAntInfo thisAnt, Object goal) {
        int worldSizeX = cm.getWorldSizeX();
        int worldSizeY = cm.getWorldSizeY();
        nodes = new Node[worldSizeX][worldSizeY];

        int goalX = 0;
        int goalY = 0;
        if (goal instanceof ILocationInfo) {
            goalX = ((ILocationInfo) goal).getX();
            goalY = ((ILocationInfo) goal).getY();
        } else if (goal instanceof Tile) {
            goal = new Tile(((Tile) goal).getX(), ((Tile) goal).getY());
            goalX = ((Tile) goal).getX();
            goalY = ((Tile) goal).getY();
        }

        // 1a. create nodes and add to two-dimensional nodes array.
        for (int x = 0; x < worldSizeX; x++) {
            for (int y = 0; y < worldSizeY; y++) {
                nodes[x][y] = new Node(x, y, 0);

                // 1b. check for obstacles
                Tile tile = cm.getTiles().get(new Position(x, y));
                if (tile != null) {
                    try {

                        // if tile is filled OR rock or tile has ant AND ant is not thisAnt AND tile is not same as goal position 
                        if (tile.isFilled() || tile.isRock() || (tile.getAnt() != null && tile.getAnt().antID() != thisAnt.antID() && tile.getX() != goalX && tile.getY() != goalY)) {
                            nodes[x][y] = null;
                        }
                    } catch (NullPointerException ex) {
                    }
                }
            }
        }

        // 2. add adjacent nodes to each node in nodes array
        for (int x = 0; x < worldSizeX; x++) {
            for (int y = 0; y < worldSizeY; y++) {
                Node current = nodes[x][y];
                if (current != null) {
                    //north
                    int yOffset = y + 1;
                    int direction = 0;
                    if (checkNode(x, yOffset)) {
                        current.addAdjacentNode(createNode(x, yOffset, direction));
                    }

                    //south
                    yOffset = y - 1;
                    direction = 2;
                    if (checkNode(x, yOffset)) {
                        current.addAdjacentNode(createNode(x, yOffset, direction));
                    }

                    //east
                    int xOffset = x + 1;
                    direction = 1;
                    if (checkNode(xOffset, y)) {
                        current.addAdjacentNode(createNode(xOffset, y, direction));
                    }

                    //west
                    xOffset = x - 1;
                    direction = 3;
                    if (checkNode(xOffset, y)) {
                        current.addAdjacentNode(createNode(xOffset, y, direction));
                    }
                }
            }
        }
    }

    /**
     * Checks that node is within world boundaries
     *
     * @param x position x
     * @param y position y
     * @return node or null if outside boundaries
     */
    private boolean checkNode(int x, int y) {
        try {

            Node node = nodes[x][y];
            if (node != null) {
                return true;
            } else {
                return false;
            }
        } catch (ArrayIndexOutOfBoundsException ex) {
            return false;
        }
    }

    private Node createNode(int x, int y, int direction) {
        Node node = nodes[x][y];
        node.setDirection(direction);
        return node;
    }

    public Node[][] getBoardNodes() {
        return nodes;
    }
}
