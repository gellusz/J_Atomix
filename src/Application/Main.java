package Application;

import Assets.Difficulty;
import Assets.Level;
import Assets.Player;
import GUI.*;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;


public class Main extends JFrame implements ActionListener, KeyListener{

    private Menu menu;
    private DifficultyMenu difficultyMenu;
    private DifficultyMenu difficultyBoard;
    private HUD hud;
    private GamePanel gamePanel;
    private MoleculePanel moleculePanel;
    private ScoreBoardPanel scoreBoardPanel;
    private Level level;
    private AssetHandler assetHandler;
    private ScoreBoardHandler scoreBoardHandler;
    private PlayerData playerData;
    private Player player;
    private Timer timer;
    private int cnt;

    /**
     * Constructor:
     * Initiates the used Classes
     */
    private Main(){
        difficultyMenu = new DifficultyMenu(this);
        difficultyBoard = new DifficultyMenu(this);
        difficultyBoard.label.setText("Scoreboard by difficulty");
        menu = new Menu(this);
        assetHandler = new AssetHandler("levels.json");
        scoreBoardHandler = new ScoreBoardHandler("scoreboard.json");
        playerData = new PlayerData();
        scoreBoardPanel = new ScoreBoardPanel(new JTable(playerData),this);
        setBounds(480,96,1024,720);
        setTitle("J_Atomix_by_SzKG");
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        add(menu);
        player = new Player();
        menu.resumeButton.setEnabled(false);
        menu.resumeButton.setText("Loading");
        if(player.load()){
            level = new Level(assetHandler,player);
            menu.resumeButton.setText("Resume");
            menu.resumeButton.setEnabled(true);
        }else{
            menu.resumeButton.setText("Resume");
            player = null;
            level = null;
        }
    }

    /**
     * Makes the program runnable
     * @param args args
     */
    public static void main(String[] args){
        JFrame mainFrame = new Main();
    }

    /**
     * Asks the Player's name, sets the values of the corresponding fields and starts the Timer,
     * then switches the panel to show the playing surface.
     */
    private void initHUD(){
        if(player == null){
            popDialog();
        }
        gamePanel = new GamePanel(level.getAssets());
        moleculePanel = new MoleculePanel(level.getMolecule(),level.getRecipe());
        hud = new HUD(moleculePanel,gamePanel);
        hud.levelId.setText("" + level.getId());
        hud.moleculeName.setText(level.getName());
        hud.highScore.setText("" + level.getHighScore());
        timeDisplay();
        hud.scoreField.setText("" + player.getScore());
        hud.playerName.setText(player.getName());
        if(timer == null){
            timer = new Timer(100,this);
            timer.start();
        }
        cnt = 10;
        this.setContentPane(hud);
    }

    /**
     * Sets up a new player for the session by asking his/her name
     */
    private void popDialog(){
        player = new Player(JOptionPane.showInputDialog(this,
                 "Enter your name!", "player1"),level.getDifficulty());
        player.dispose();
    }

    /**
     * This method helps in the right display of how much time is left for the level
     */
    private void timeDisplay(){
        int time = level.getTime();
        if(time % 60 == 0){
            hud.timeLeft.setText(time / 60 + ":00");
        }else if(time % 60 < 10){
            hud.timeLeft.setText(time / 60 + ":0" + time % 60);
        }else {
            hud.timeLeft.setText(time / 60 + ":" + time % 60);
        }
    }

    @Override
    /**
     * The "state machine" that controls the flow of the program
     */
    public void actionPerformed(ActionEvent e) {
        if( gamePanel != null ){
            if(level.getTime() == 0){
                player.lvlUp(0);
                timer.stop();
                scoreBoardHandler.updateBoard(level.getDifficulty(),player);
                gamePanel.gameOver();
                player.dispose();
                player = null;
            }else if( gamePanel.isPaused()){
                if(gamePanel.isQuit()){
                    try{
                        player.save();
                        System.exit(0);
                    }catch (IOException ie){
                        ie.printStackTrace();
                    }
                }
            }else{
                if(timer != null) {
                    timer.start();
                    cnt--;
                    if(level.isCleared()){
                        player.lvlUp(level.getScore());
                        level.nextLevel(assetHandler);
                        initHUD();
                    }
                }
                if(level != null && cnt == 0){
                    level.timerTick();
                    timeDisplay();
                    cnt = 10;
                }
            }
        }
        if (e.getSource() == menu.newGameButton) {
            this.setContentPane(difficultyMenu);
        } else if (e.getSource() == difficultyMenu.backToMainMenuButton ||
                e.getSource() == difficultyBoard.backToMainMenuButton) {
            this.setContentPane(menu);
        } else if (e.getSource() == difficultyMenu.easyButton) {
            level = new Level(assetHandler, Difficulty.Easy);
            player = null;
            initHUD();
        } else if (e.getSource() == difficultyMenu.normalButton) {
            level = new Level(assetHandler, Difficulty.Normal);
            player = null;
            initHUD();
        } else if (e.getSource() == difficultyMenu.hardButton) {
            level = new Level(assetHandler, Difficulty.Hard);
            player = null;
            initHUD();
        } else if (e.getSource() == menu.resumeButton){
            initHUD();
        }else if (e.getSource() == menu.scoreBoardButton ||
                e.getSource() == scoreBoardPanel.backButton ){
            playerData.dispose();
            this.setContentPane(difficultyBoard);
        }else if(e.getSource() == menu.exitButton){
            System.exit(0);
        }else if (e.getSource() == difficultyBoard.easyButton){
            scoreBoardHandler.loadBoard(Difficulty.Easy,playerData);
            this.setContentPane(scoreBoardPanel);
        }else if (e.getSource() == difficultyBoard.normalButton){
            scoreBoardHandler.loadBoard(Difficulty.Normal,playerData);
            this.setContentPane(scoreBoardPanel);
        }else if (e.getSource() == difficultyBoard.hardButton){
            scoreBoardHandler.loadBoard(Difficulty.Hard,playerData);
            this.setContentPane(scoreBoardPanel);
        }
        this.repaint();
        this.revalidate();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    /**
     * Passes on KeyEvent to gamePanel
     */
    public void keyPressed(KeyEvent e) {
        if(gamePanel != null){
            gamePanel.keyPressed(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
