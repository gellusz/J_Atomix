package Assets;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.util.Map;

public class Cursor extends Hitbox implements Movable{
    private Atom grabbed;
    private int prevX, prevY;

    /**
     * Constructor:
     * Initiates the data and the image of the class.
     * <p>
     * (For some reason swing only displays everything right if an image is loaded for every instance)
     * @param x the x position
     * @param y the y position
     */
    public Cursor(int x, int y){
        super(x,y);
        grabbed = null;
        prevX = x;
        prevY = y;
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/cursor.png"));
        }catch (IOException e){
            System.out.println("Image not loaded.");
        }
    }

    /**
     * Checks whether the Cursor is hovering over an Atom.
     * If it does, the method makes the cursor grab it.
     * @param hitBoxes the assets that contain the items that can be grabbed
     */
    public void grab(Map<String, Hitbox> hitBoxes){
        if(grabbed == null){
        hitBoxes.forEach((key,value) ->{
            if(key.matches("atom(.*)")) {
                if ((value.getPosX() == this.rect.x) && (value.getPosY() == this.rect.y)) {
                    grabbed = (Atom) value;
                    try {
                        this.image = ImageIO.read(getClass().getResourceAsStream("/cursor_grab.png"));
                    }catch (IOException e){
                        System.out.println("Image not loaded.");
                    }
                }
            }
        });
        }else{
            grabbed = null;
            try {
                this.image = ImageIO.read(getClass().getResourceAsStream("/cursor.png"));
            }catch (IOException e){
                System.out.println("Image not loaded.");
            }
        }
    }

    /**
     * Moves the Cursor in the corresponding direction. Also moves the Atom that is grabbed
     * @param assets passed on to the grabbed Atom's move method
     */
    public void moveUp(Map<String, Hitbox> assets){
        if (grabbed != null) {
            grabbed.moveUp(assets);
            this.rect.y = grabbed.getPosY();
        }else{
            while(prevY - 32 < rect.y){
                rect.y -= 1;
            }
        }
        prevY = rect.y;
    }

    /**
     * Moves the Cursor in the corresponding direction. Also moves the Atom that is grabbed
     * @param assets passed on to the grabbed Atom's move method
     */
    public void moveDown(Map<String, Hitbox> assets) {
        if (grabbed != null) {
            grabbed.moveDown(assets);
            this.rect.y = grabbed.getPosY();
        } else {
            while (prevY + 32 > rect.y) {
                rect.y += 1;
            }
        }
        prevY = rect.y;
    }

    /**
     * Moves the Cursor in the corresponding direction. Also moves the Atom that is grabbed
     * @param assets passed on to the grabbed Atom's move method
     */
    public void moveLeft(Map<String, Hitbox> assets) {
        if (grabbed != null) {
            grabbed.moveLeft(assets);
            this.rect.x = grabbed.getPosX();
        } else {
            while (prevX - 32 < rect.x) {
                rect.x -= 1;
            }
        }
        prevX = rect.x;
    }

    /**
     * Moves the Cursor in the corresponding direction. Also moves the Atom that is grabbed
     * @param assets passed on to the grabbed Atom's move method
     */
    public void moveRight(Map<String, Hitbox> assets) {
        if (grabbed != null) {
            grabbed.moveRight(assets);
            this.rect.x = grabbed.getPosX();
        } else {
            while (prevX + 32 > rect.x) {
                rect.x += 1;
            }
        }
        prevX = rect.x;
    }

    /**
     * Draws the image of the Cursor on the screen
     * @param g the Graphics instance to handle this
     */
    @Override
    public void drawImg(Graphics g){
        g.drawImage(this.image,this.rect.x,this.rect.y,32,32,null);
    }

}
