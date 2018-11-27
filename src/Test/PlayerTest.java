package Test;

import Assets.Difficulty;
import Assets.Player;
import org.junit.*;

import java.io.IOException;

import static org.junit.Assert.*;

public class PlayerTest {
    Player player;

    @Before
    public void setup() {
        player = new Player("Charlie",Difficulty.Hard);

    }
    @After
    public void tearDown() {
        player.dispose();
    }
    @Test
    public void checkLoad() {
        player.dispose();
        assertFalse("Loading should have failed", player.load());
    }
    @Test
    public void checkSave() {
        try{
            player.save();
            player = new Player();
            player.load();
            assertEquals("Should have loaded correctly","Charlie",player.getName());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Test
    public void checkLevel(){
        player.lvlUp(5000);
        player.lvlUp(4000);
        assertEquals("The player should have passed 2 levels",3,player.getLevelId());
    }

    @Test
    public void checkLoadedScore(){
        try{
            player.lvlUp(5000);
            player.lvlUp(4000);
            player.save();
            player = new Player();
            player.load();
            assertEquals("Should have loaded correctly",9000,player.getScore());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
