package Assets;

import Application.AssetHandler;

import javax.json.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class Level {
    private int id;
    private String name;
    private ArrayList<Atom> recipe;
    private HashMap<String,Hitbox> assets;
    private Molecule molecule;
    private JsonObject highScores;
    private int highScore;
    private int score;
    private int time;
    private Difficulty difficulty;

    /**
     * Constructor
     * Gets an AssetHandler to initiate it's data and the game session's Difficulty.
     * Also reads a JSON file containing highScores
     * @param assetHandler loader of component objects
     * @param difficulty game session difficulty
     */
    public Level(AssetHandler assetHandler, Difficulty difficulty){
        id = 1;
        this.difficulty = difficulty;
        try{
            JsonReader jsonReader = Json.createReader(new FileInputStream("res\\highScores.json"));
            JsonObject obj = jsonReader.readObject();
            this.highScores = obj.getJsonObject("highscores");
        }catch (IOException e){
            e.printStackTrace();
        }
        initLevel(assetHandler);
    }

    /**
     * Constructor
     * Gets an AssetHandler to initiate it's data and a Player to continue it's session.
     * Also reads the highScores by a class method
     * @param assetHandler passed on to initializer method
     * @param plaier the instance containing session information
     */
    public Level(AssetHandler assetHandler,Player plaier){
        id = plaier.getLevelId();
        difficulty = plaier.getDifficulty();
        try{
            JsonReader jsonReader = Json.createReader(new FileInputStream("res\\highScores.json"));
            JsonObject obj = jsonReader.readObject();
            this.highScores = obj.getJsonObject("highscores");
        }catch (IOException e){
            e.printStackTrace();
        }
        initLevel(assetHandler);
    }

    /**
     * Component and data initializer method
     * @param assetHandler loader of component objects
     */
    private void initLevel(AssetHandler assetHandler){
        name = assetHandler.getLevelName(id);
        recipe = assetHandler.loadRecipe(id);
        assets = assetHandler.loadAssets(id,recipe);
        molecule = assetHandler.loadMolecule(id);
        molecule.initChecker(recipe,assets);
        switch (difficulty){
            case Easy:
                time = 10*60;
                break;
            case Normal:
                time = 5*60;
                break;
            case Hard:
                time = 2*60+30;
                break;
        }
        score = 6000;
        readHighScore();
    }

    /**
     * This method handles time and score when called
     */
    public void timerTick(){
        time--;
        switch (difficulty){
            case Easy:
                score -= 10;
                break;
            case Normal:
                score -= 20;
                break;
            case Hard:
                score -= 40;
                break;
        }
    }

    /**
     * Getter
     * @return level Id
     */
    public int getId(){
        return id;
    }

    /**
     * Getter
     * @return time left
     */
    public int getTime(){
        return time;
    }

    /**
     * Getter
     * @return actual score
     */
    public int getScore(){
        return score;
    }

    /**
     * Getter
     * @return Highest score achieved on level (by Difficulty)
     */
    public int getHighScore() {
        return highScore;
    }

    /**
     * Getter
     * @return level name
     */
    public String getName() {
        return name;
    }

    /**
     * Getter
     * @return game session Difficulty
     */
    public Difficulty getDifficulty() {
        return this.difficulty;
    }

    /**
     * Getter
     * @return Molecule to be put together
     */
    public Molecule getMolecule(){
        return molecule;
    }

    /**
     * Getter
     * @return assets of Level
     */
    public HashMap<String,Hitbox> getAssets(){
        return assets;
    }

    /**
     * Getter
     * @return list of Atom models on level
     */
    public ArrayList<Atom> getRecipe(){
        return recipe;
    }

    /**
     * Runs checker algorithm
     * @return true if the algorithm found the molecule and the atoms put together matching
     */
    public boolean isCleared(){
        return molecule.matches(recipe,assets);
    }

    /**
     * Initiates the highScore that was stored corresponding to the Level and Difficulty
     */
    public void readHighScore(){
        highScore = highScores.getJsonObject(this.difficulty.toString().toLowerCase()).getInt("level"+id);
    }

    /**
     * REbuilds an existing JsonObject by modifying the highScore data stored in it corresponding to the Level
     * @return The rebuilt JsonArray
     */
    public JsonObject buildSon(){
        JsonObjectBuilder builder = Json.createObjectBuilder();
        JsonObject obj = highScores.getJsonObject(this.difficulty.toString().toLowerCase());
        for(int i = 1; i <= obj.size(); i++){
            if(i == id){
                builder.add("level"+i,score);
            }else{
                builder.add("level"+i,obj.getInt("level"+i));
            }
        }
        return builder.build();
    }

    /**
     * Updates the JSON file that contains highScores
     */
    public void writeHighScore(){

        JsonObject easy = null;
        JsonObject normal = null;
        JsonObject hard = null;

        switch (difficulty){
            case Easy:
                easy = buildSon();
                normal = Json.createObjectBuilder(highScores.getJsonObject("normal")).build();
                hard = Json.createObjectBuilder(highScores.getJsonObject("hard")).build();
                break;
            case Normal:
                easy = Json.createObjectBuilder(highScores.getJsonObject("easy")).build();
                normal = buildSon();
                hard = Json.createObjectBuilder(highScores.getJsonObject("hard")).build();
                break;
            case Hard:
                easy = Json.createObjectBuilder(highScores.getJsonObject("easy")).build();
                normal = Json.createObjectBuilder(highScores.getJsonObject("normal")).build();
                hard = buildSon();
                break;
        }

        JsonObject obj = Json.createObjectBuilder()
                .add("easy",easy)
                .add("normal",normal)
                .add("hard",hard)
                .build();
        try{
            JsonWriter jsonWriter = Json.createWriter(new FileOutputStream("res\\highScores.json"));
            jsonWriter.writeObject(Json.createObjectBuilder().add("highscores",obj).build());
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    /**
     * Sets up next level when called
     * @param assetHandler passed on to initializer method
     */
    public void nextLevel(AssetHandler assetHandler){
        if(score > highScore){
            writeHighScore();
        }
        id++;
        initLevel(assetHandler);
    }
}
