package Assets;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;

public class Wall extends Hitbox {
    /**
     * Constructor:
     * Initiates the data and the image of the class.
     * <p>
     * (For some reason swing only displays everything right if an image is loaded for every instance)
     * @param x the x position
     * @param y the y position
     */
    public Wall(int x, int y){
        super(x,y);
        try {
            this.image = ImageIO.read(getClass().getResourceAsStream("/wall.jpg"));
        }catch (IOException e){
            System.out.println("Image not loaded.");
        }
    }

    @Override
    /**
     * Draws the image of the Wall on the screen
     * @param g the Graphics instance to handle this
     */
    public void drawImg(Graphics g) {
        g.drawImage(this.image,this.rect.x,this.rect.y,32,32,null);
    }
}
