package Assets;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

public class Atom extends Hitbox implements Movable {
    private int type;
    private String bonds;
    private ArrayList<BufferedImage> bondImages;

    /**
     * Constructor:
     * Initiates the data and the images of the class.
     * <p>
     * (For some reason swing only displays everything right if an image is loaded for every instance)
     * @param x the x position
     * @param y the y position
     * @param aType type of atom (an integer as stored in the JSON file)
     * @param bonds "list" of bonds (characters as stored in the JSON file)
     */
    public Atom(int x, int y, int aType, String bonds){
        super(x,y);
        this.type = aType;
        this.bonds = bonds;
        try {
            switch (this.type){
                case 1:
                    this.image = ImageIO.read(getClass().getResourceAsStream("/atom-h.png"));
                    break;
                case 2:
                    this.image = ImageIO.read(getClass().getResourceAsStream("/atom-c.png"));
                    break;
                case 3:
                    this.image = ImageIO.read(getClass().getResourceAsStream("/atom-o.png"));
                    break;
            }
            bondImages = new ArrayList<>();
            for(int i = 0; i < bonds.length(); i++){
                switch (bonds.charAt(i)){
                    case 'B' :
                        bondImages.add(ImageIO.read(getClass().getResourceAsStream("/bonds_bb.png")));
                        break;
                    case 'D' :
                        bondImages.add(ImageIO.read(getClass().getResourceAsStream("/bonds_dd.png")));
                        break;
                    default:
                        bondImages.add(ImageIO.read(getClass().getResourceAsStream("/bonds_" + bonds.charAt(i) + ".png")));
                        break;
                }
            }
        }catch (IOException e){
            System.out.println("Image not loaded.");
        }
    }

    @Override
    /**
     * Getter
     * @return type of Atom
     */
    public int getType() {
        return type;
    }

    @Override
    /**
     * Getter
     * @return bonds of atom
     */
    public String getBonds(){ return this.bonds; }

    /***
     * Compares two Atom by model
     * @param rel Atom to be compared with
     * @return true if they are the same model
     */
    @Override
    public boolean equals(Atom rel){
        return ((type == rel.getType()) && bonds.equals(rel.getBonds()));
    }

    /**
     * Checks for collision and moves the Atom
     * @param assets instances that it can collide
     */
    public void moveUp(Map<String, Hitbox> assets){
        topSensor = null;
        detect(assets);
        if(topSensor != null){
            while(!this.rect.intersects(topSensor.getRect())){
                rect.y -= 1;
            }
            rect.y +=1;
        }else{
            while(topSensor == null){
                detect(assets);
                rect.y -= 1;
            }
            moveUp(assets);
        }
    }

    /**
     * Checks for collision and moves the Atom
     * @param assets instances that it can collide
     */
    public void moveDown(Map<String, Hitbox> assets){
        bottomSensor = null;
        detect(assets);
        if(bottomSensor != null){
            while(!this.rect.intersects(bottomSensor.getRect())){
                rect.y += 1;
            }
            rect.y -=1;
        }else{
            while(bottomSensor == null){
                detect(assets);
                rect.y += 1;
            }
            moveDown(assets);
        }
    }

    /**
     * Checks for collision and moves the Atom
     * @param assets instances that it can collide
     */
    public void moveLeft(Map<String, Hitbox> assets){
        leftSensor = null;
        detect(assets);
        if(leftSensor != null){
            while(!this.rect.intersects(leftSensor.getRect())){
                rect.x -= 1;
            }
            rect.x +=1;
        }else{
            while(leftSensor == null){
                detect(assets);
                rect.x -= 1;
            }
            moveLeft(assets);
        }
    }

    /**
     * Checks for collision and moves the Atom
     * @param assets instances that it can collide
     */
    public void moveRight(Map<String, Hitbox> assets){
        rightSensor = null;
        detect(assets);
        if(rightSensor != null){
            while(!this.rect.intersects(rightSensor.getRect())){
                rect.x += 1;
            }
            rect.x -=1;
        }else{
            while(rightSensor == null){
                detect(assets);
                rect.x += 1;
            }
            moveRight(assets);
        }
    }

    @Override
    /**
     * Draws the image of the Atom on the screen
     * @param g the Graphics instance to handle this
     */
    public void drawImg(Graphics g){
        g.drawImage(this.image,this.rect.x+4,this.rect.y +4,24,24,null);
        for(BufferedImage img : bondImages){
            g.drawImage(img,rect.x,rect.y,32,32,null);
        }
    }

    /**
     * Draws the image of the Atom on the screen, but on the given position
     * @param g the Graphics instance to handle this
     * @param x x position
     * @param y y position
     */
    public void drawImg(Graphics g, final int x, final int y) {
        g.drawImage(this.image,x+4,y +4,24,24,null);
        for(BufferedImage img : bondImages){
            g.drawImage(img,x,y,32,32,null);
        }
    }
}
