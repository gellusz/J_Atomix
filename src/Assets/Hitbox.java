package Assets;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Map;

public abstract class Hitbox {
    protected Hitbox leftSensor, rightSensor, bottomSensor, topSensor;
    protected BufferedImage image;
    protected Rectangle rect;

    /**
     * Sets the sensors of the instance for collision detection
     * @param hitBoxes assets that can be "sensed"
     */
    public void detect(Map<String, Hitbox> hitBoxes){
        hitBoxes.forEach((key,value) ->{
                if((value.getPosX()+32 > this.rect.x - 16 ) && (value.getPosX()+32 < this.rect.x + 16 )
                        && (value.getPosY() + 5 > this.rect.y) && (value.getPosY() + 26 < this.rect.y + 32)){
                    this.leftSensor = value;
                }
                if((value.getPosX() < this.rect.x + 48 ) && (value.getPosX() > this.rect.x + 16 )
                        && (value.getPosY() + 5 > this.rect.y) && (value.getPosY() + 26 < this.rect.y + 32)){
                    this.rightSensor = value;
                }
                if((value.getPosY() < this.rect.y + 48 ) && (value.getPosY() > this.rect.y + 16 )
                        && (value.getPosX() + 5 > this.rect.x) && (value.getPosX() + 26 < this.rect.x + 32)){
                    this.bottomSensor = value;
                }
                if((value.getPosY()+32 > this.rect.y - 16 ) && (value.getPosY()+32 < this.rect.y + 16 )
                        && (value.getPosX() + 5 > this.rect.x) && (value.getPosX() + 26 < this.rect.x + 32)){
                    this.topSensor = value;
                }
        });
    }

    public int getType(){
        return 0;
    }

    public String getBonds(){ return null; }

    public boolean equals(Atom rel){
        return false;
    }

    /**
     * Getter
     * @return horizontal position
     */
    public int getPosX(){
        return rect.x;
    }

    /**
     * Getter
     * @return vertical position
     */
    public int getPosY(){
        return rect.y;
    }

    /**
     * Getter
     * @return Component Rectangle
     */
    public Rectangle getRect(){
        return this.rect;
    }

    /**
     * Constructor:
     * Initiates data
     * @param x the x position
     * @param y the y position
     */
    protected Hitbox(int x, int y){
        this.leftSensor = null;
        this.rightSensor = null;
        this.bottomSensor = null;
        this.topSensor = null;
        this.rect = new Rectangle(x,y,32,32);
        image = null;
    }


    public void drawImg(Graphics g){
    }
}
