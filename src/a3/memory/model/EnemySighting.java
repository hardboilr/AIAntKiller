package a3.memory.model;

import aiantwars.EAntType;
import aiantwars.IAntInfo;
import aiantwars.impl.LogicAnt;

/**
 *
 * @author Tobias Jacobsen
 */
public class EnemySighting {

    private IAntInfo friendly = new LogicAnt(null, null, null, -1, -1, null, null, null);
    private IAntInfo enemy = new LogicAnt(null, null, null, -1, -1, null, null, null);
    private final int turn;

    public EnemySighting(IAntInfo friendly, IAntInfo enemy, int turn) {
        this.friendly = friendly;
        this.enemy = enemy;
        this.turn = turn;
    }

    public IAntInfo getFriendly() {
        return friendly;
    }

    public IAntInfo getEnemy() {
        return enemy;
    }

    public int getTurn() {
        return turn;
    }
}
