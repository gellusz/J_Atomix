package GUI;

import javax.swing.*;
import javax.swing.Timer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.*;

import Assets.*;
import Assets.Cursor;

public class GamePanel extends JPanel implements KeyListener, ActionListener {

    private Map<String,Hitbox> assets;
    private Cursor cursor = new Cursor(32*5+1,32*2+1);
    private Timer timer;
    private int speed = 10;
    private boolean pause = false;
    private boolean quit = false;

    /**
     * Constructor:
     * Initializes panel and sets up timer.
     * @param assets assets for drawing on screen
     */
    public GamePanel( Map<String, Hitbox> assets){
        this.assets = assets;
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        timer = new Timer(speed,this);
        timer.start();
    }

    @Override
    /**
     * Overridden method of JComponent.
     * Draws the assets of playing zone and other status texts
     */
    public void paint(Graphics g){
        //background
        g.setColor(Color.black);
        g.fillRect(1,1,this.getWidth(),this.getHeight());

        //walls and atoms
        assets.forEach((key,value) ->
            value.drawImg(g));

        //cursor
        cursor.drawImg(g);

        if(pause){
            g.setColor(Color.green);
            g.setFont(new Font("serif", Font.BOLD,30));
            g.drawString("PAUSED",this.getWidth()/3,this.getHeight()/2);
            g.setFont(new Font("serif", Font.PLAIN,22));
            g.drawString("Press enter to quit,",this.getWidth()/3,this.getHeight()/2 + 30);
            g.drawString("or press esc again to resume",this.getWidth()/3,this.getHeight()/2 + 52);
        }

        if(quit && !isPaused()){
            g.setColor(Color.red);
            g.setFont(new Font("stencil", Font.BOLD,36));
            g.drawString("GAME OVER",this.getWidth()/3,this.getHeight()/2);
            g.setFont(new Font("serif", Font.PLAIN,22));
            g.drawString("Press esc to quit",this.getWidth()/3,this.getHeight()/2 + 36);
        }

        g.dispose();
    }

    /**
     * Sets the quit flag true.
     */
    public void gameOver(){
        quit = true;
    }

    /**
     * Getter
     * @return the pause flag's state
     */
    public boolean isPaused() {
        return pause;
    }

    /**
     * Getter
     * @return the quit flag's state
     */
    public boolean isQuit() {
        return quit;
    }

    @Override
    /**
     * Runs timer and refreshes display
     */
    public void actionPerformed(ActionEvent e){
        timer.start();
        repaint();
    }

    @Override
    /**
     * Unimplemented method of class KeyListener
     */
    public void keyTyped(KeyEvent e){
    }

    @Override
    /**
     * Handles keyboard input
     */
    public void keyPressed(KeyEvent e){
        switch(e.getKeyCode()){
            case KeyEvent.VK_LEFT:
                if(!pause){
                    System.out.println("Left key pressed");
                    if(cursor.getPosX() > 1) {
                        cursor.moveLeft(assets);
                    }
                }
                break;
            case KeyEvent.VK_RIGHT:
                if(!pause){
                    System.out.println("Right key pressed");
                    if(cursor.getPosX() < this.getWidth() - 32) {
                        cursor.moveRight(assets);
                    }
                }
                break;
            case KeyEvent.VK_DOWN:
                if(!pause){
                    System.out.println("Down key pressed");
                    if(cursor.getPosY() < this.getHeight() - 32){
                        cursor.moveDown(assets);
                    }
                }
                break;
            case KeyEvent.VK_UP:
                if(!pause){
                    System.out.println("Up key pressed");
                    if(cursor.getPosY() > 1) {
                        cursor.moveUp(assets);
                    }
                }
                break;
            case KeyEvent.VK_ESCAPE:
                pause = !pause;
                System.out.println("Escape key pressed");
                if(quit){
                    System.exit(0);
                }
                break;
            case KeyEvent.VK_SPACE:
                if(!pause){
                    cursor.grab(assets);
                    System.out.println("Space key pressed");
                }
                break;
            case KeyEvent.VK_ENTER:
                if(pause){
                    quit = true;
                }
                break;
        }
    }

    @Override
    /**
     * Unimplemented method of class KeyListener
     */
    public void keyReleased(KeyEvent e){
    }
}
