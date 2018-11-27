package Assets;

import java.io.*;

public class Player implements Serializable {
    private String name;
    private int score;
    private int levelId;
    private Difficulty difficulty;

    /**
     * Constructor:
     * Initializes data by name and Difficulty
     * @param name the players name
     * @param difficulty the Difficulty of the game session
     */
    public Player(String name, Difficulty difficulty){
        if(name != null){
            this.name = name;
        }else{
            this.name = "player1";
        }
        score = 0;
        levelId = 1;
        this.difficulty = difficulty;
    }

    /**
     * Constructor:
     * Initializes data for placeholder Player
     */
    public Player(){
        this.name = "player1";
        score = 0;
        levelId = 1;
        difficulty = Difficulty.Normal;
    }

    /**
     * Constructor:
     * Initializes data. Used only by PlayerData class
     * @param n name of Player
     * @param points points of Player
     */
    public Player(String n, int points){
        this.name = n;
        this.score = points;
    }

    /**
     * Getter
     * @return player's name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter
     * @return player's earned score
     */
    public int getScore() {
        return score;
    }

    /**
     * Getter
     * @return the Id of level the Player is on
     */
    public int getLevelId() {
        return levelId;
    }

    /**
     * Getter
     * @return Difficulty of game session
     */
    public Difficulty getDifficulty() {
        return difficulty;
    }

    /**
     * This method lets us get rid of current game session (only used in case of Game Over)
     */
    public void dispose(){
        try {
            new FileWriter("res\\player.txt").close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method needs to be called in order to correctly pass level
     * @param points points earned by passing level
     */
    public void lvlUp(final int points){
        score += points;
        levelId++;
    }

    /**
     * Method to save game session. Serializes Player.
     * @throws IOException it is handling files
     */
    public void save() throws IOException {
        FileOutputStream fos = new FileOutputStream("res\\player.txt");
        ObjectOutputStream oos = new ObjectOutputStream(fos);
        oos.writeObject(this);
        oos.close();
    }

    /**
     * Method to load saved game session (for resuming). Deserializes Player.
     * @return true if there is a saved game session
     */
    public boolean load(){
        try {
            FileInputStream fis = new FileInputStream("res\\player.txt");
            ObjectInputStream ois = new ObjectInputStream(fis);
            Player plaier = (Player) ois.readObject();
            this.levelId = plaier.getLevelId();
            this.name = plaier.getName();
            this.score = plaier.getScore();
            this.difficulty = plaier.getDifficulty();
            ois.close();
            return plaier != null;
        } catch (IOException e) {
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
