package Test;

import Application.AssetHandler;
import Assets.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.assertEquals;
import Assets.Difficulty;

public class LevelTest {
    Level level;

    @Before
    public void setup() {
        level = new Level(new AssetHandler("levels.json"),Difficulty.Hard);
    }
    @Test
    public void checkScore() {
        for (int i = 0; i< 10; i++){
            level.timerTick();
        }
        assertEquals("Score should have been reduced",5600,level.getScore());
    }

    @Test
    public void checkTick(){
        for (int i = 0; i< 5; i++){
            level.timerTick();
        }
        assertEquals("Time should have passed",145,level.getTime());
    }
    @Test
    public void checkDifficulty() {
        level = new Level(new AssetHandler("levels.json"),new Player("Danny",Difficulty.Easy));
        assertEquals("Difficulty should be easy",Difficulty.Easy,level.getDifficulty());
    }
    @Test
    public void checkLevel(){
        level.nextLevel(new AssetHandler("levels.json"));
        level.nextLevel(new AssetHandler("levels.json"));
        assertEquals("Should have stepped 2 levels",3,level.getId());
    }
}
