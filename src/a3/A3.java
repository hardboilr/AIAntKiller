package a3;

import a3.ai.JT_Destroyer;
import aiantwars.IAntAI;
import tournament.player.PlayerFactory;

/**
 * @author Tobias Jacobsen
 */
public class A3 implements PlayerFactory<IAntAI> {

    @Override
    public IAntAI getNewInstance() {
        return new JT_Destroyer();
    }

    @Override
    public String getID() {
        return "A3";
    }

    @Override
    public String getName() {
        return "JT_Destroyer";
    }
}
