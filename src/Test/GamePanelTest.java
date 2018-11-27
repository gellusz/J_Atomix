package Test;


import Application.AssetHandler;
import Assets.Atom;
import GUI.GamePanel;
import org.junit.*;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class GamePanelTest {

    GamePanel gamePanel;

    @Before
    public void setUp(){
        AssetHandler assetHandler = new AssetHandler("levels.json");
        ArrayList<Atom> list = assetHandler.loadRecipe(1);
        gamePanel = new GamePanel(assetHandler.loadAssets(1,list));
    }

    @Test
    public void checkFlagQuit(){
        gamePanel.gameOver();
        assertTrue("It should be quit as it is game over",gamePanel.isQuit());
    }

    @Test
    public void checkFlagPause(){
        assertFalse("It should not be pause as you can't pause it now",gamePanel.isPaused());
    }

}
