package Assets;

import java.util.Map;

public interface Movable {
    /**
     * Moves object in corresponding direction
     * @param assets needed for collision detection
     */
    void moveUp(Map<String, Hitbox> assets);
    /**
     * Moves object in corresponding direction
     * @param assets needed for collision detection
     */
    void moveDown(Map<String, Hitbox> assets);
    /**
     * Moves object in corresponding direction
     * @param assets needed for collision detection
     */
    void moveLeft(Map<String, Hitbox> assets);
    /**
     * Moves object in corresponding direction
     * @param assets needed for collision detection
     */
    void moveRight(Map<String, Hitbox> assets);
}
