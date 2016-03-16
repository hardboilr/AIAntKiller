package algorithm.model;

import memory.CollectiveMemory;
import memory.Position;
import memory.Tile;

/**
 * Creates empty nodes based on world size and add them to two-dimensional
 * array. Is used for pathfinding algorithm.
 *
 * @author Tobias
 */
public class Board {

    private final CollectiveMemory cm = CollectiveMemory.getInstance();
    private final int worldSizeX;
    private final int worldSizeY;
    private final Node[][] nodes;

    public Board(int worldSizeX, int worldSizeY) {
        this.worldSizeX = worldSizeX;
        this.worldSizeY = worldSizeY;
        nodes = new Node[worldSizeX][worldSizeY];

        // create nodes and add to two-dimensional nodes array.
        for (int x = 0; x < worldSizeX; x++) {
            for (int y = 0; y < worldSizeY; y++) {
                nodes[x][y] = new Node(x, y, null, -1);

                // check for obstacles
                Tile tile = cm.getMemory().get(new Position(x, y));
                if (tile != null) {
                    if (tile.isFilled() || tile.isRock()) {
                        nodes[x][y] = null;
                    }
                }
            }
        }

        // add adjacent nodes to each node in nodes array
        for (int x = 0; x < worldSizeX; x++) {
            for (int y = 0; y < worldSizeY; y++) {
                Node current = nodes[x][y];
                if (current != null) {
                    //north
                    current.setNodeNorth(checkNode(x, y + 1));
                    //south
                    current.setNodeSouth(checkNode(x, y - 1));
                    //east
                    current.setNodeEast(checkNode(x + 1, y));
                    //west
                    current.setNodeWest(checkNode(x - 1, y));
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
    private Node checkNode(int x, int y) {
        if (x > 0 && x <= worldSizeX && y > 0 && y <= worldSizeY) {
            return nodes[x][y];
        } else {
            return null;
        }
    }

    public Node[][] getBoardNodes() {
        return nodes;
    }
}
