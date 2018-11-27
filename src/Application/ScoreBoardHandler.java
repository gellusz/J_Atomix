package Application;

import Assets.Difficulty;
import Assets.Player;

import javax.json.*;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ScoreBoardHandler {

    private JsonObject scoreBoards;
    private String source;

    /**
     * Constructor:
     * Loads the data of scoreboards from a JSON in a JsonObject for further use
     * @param src file name (expected to be in res folder!) */
    public ScoreBoardHandler(final String src){
        source = src;
        try{
            JsonReader jsonReader = Json.createReader(new FileInputStream("res\\" + src));
            JsonObject obj = jsonReader.readObject();
            this.scoreBoards = obj.getJsonObject("scoreboards");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * REbuilds an existing JsonArray by modifying or creating the data corresponding to a player
     * @param board the JsonArray to rebuild
     * @param player the Player providing data
     * @return The rebuilt JsonArray
     */
    public JsonArray buildBoard(JsonArray board,Player player){
        JsonArray array = null;
        boolean found = false;
        int idx = 0;
        String name = player.getName();
        for(int i = 0; i < board.size(); i++){
            if(board.getJsonObject(i).getJsonString("name").getString().equals(name)){
                found = true;
                idx = i;
            }
        }
        JsonObject obj;
        JsonArrayBuilder builderMain = Json.createArrayBuilder();
        if(found){
            JsonArrayBuilder builderSub = Json.createArrayBuilder();
            for(int i = 0; i < board.size(); i++){
                if(i == idx){
                    array = board.getJsonObject(i).getJsonArray("scores");
                    for(int j = 0; j< array.size(); j++){
                        builderSub.add(array.getJsonNumber(j));
                    }
                    builderSub.add(player.getScore());
                    array = builderSub.build();
                    obj = Json.createObjectBuilder().add("name",player.getName())
                            .add("scores",
                                    Json.createArrayBuilder(array))
                            .build();
                    builderMain.add(obj);
                }else{
                    obj = Json.createObjectBuilder().add("name",
                            board.getJsonObject(i).getJsonString("name").getString())
                            .add("scores",Json.createArrayBuilder(board.getJsonObject(i).getJsonArray("scores")))
                            .build();
                    builderMain.add(obj);

                }
            }
            array = builderMain.build();
        }else{
            for(int i = 0; i < board.size(); i++){
                obj = Json.createObjectBuilder().add("name",
                            board.getJsonObject(i).getJsonString("name").getString())
                            .add("scores",board.getJsonObject(i).getJsonArray("scores"))
                            .build();
                builderMain.add(obj);
            }
            obj = Json.createObjectBuilder().add("name",name).add("scores",
                    Json.createArrayBuilder().add(player.getScore())).build();
            builderMain.add(obj);
            array = builderMain.build();
        }

        return array;
    }

    /**
     * Updates scoreboard data to the corresponding Difficulty stored in a JSON file by rebuilding the JSON file itself
     * @param difficulty the difficulty of the game session
     * @param player the data provider
     */
    public void updateBoard(Difficulty difficulty, Player player){
        JsonArray easyArr = null;
        JsonArray normalArr = null;
        JsonArray hardArr = null;

        switch (difficulty){
            case Easy:
                easyArr = buildBoard(scoreBoards.getJsonArray(difficulty.toString().toLowerCase()),player);
                normalArr = Json.createArrayBuilder(scoreBoards.getJsonArray("normal")).build();
                hardArr = Json.createArrayBuilder(scoreBoards.getJsonArray("hard")).build();
                break;
            case Normal:
                easyArr = Json.createArrayBuilder(scoreBoards.getJsonArray("easy")).build();
                normalArr = buildBoard(scoreBoards.getJsonArray(difficulty.toString().toLowerCase()),player);
                hardArr = Json.createArrayBuilder(scoreBoards.getJsonArray("hard")).build();
                break;
            case Hard:
                easyArr = Json.createArrayBuilder(scoreBoards.getJsonArray("easy")).build();
                normalArr = Json.createArrayBuilder(scoreBoards.getJsonArray("normal")).build();
                hardArr = buildBoard(scoreBoards.getJsonArray(difficulty.toString().toLowerCase()),player);
                break;
        }

        JsonObject obj = Json.createObjectBuilder()
                .add("easy", easyArr)
                .add("normal",normalArr)
                .add("hard",hardArr)
                .build();

        try{
            JsonWriter jsonWriter = Json.createWriter(new FileOutputStream("res\\" + source));
            jsonWriter.writeObject(Json.createObjectBuilder().add("scoreboards",obj).build());
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Loads the scoreboard data of the corresponding difficulty in the data container
     * @param diff the Difficulty that we want to load the scoreboard for
     * @param data the data container to be filled
     */
    public void loadBoard(Difficulty diff, PlayerData data){
        JsonArray board = scoreBoards.getJsonArray(diff.toString().toLowerCase());
        JsonArray arr;
        String name;
        int score;
        for(int i = 0; i < board.size(); i++){
            name = board.getJsonObject(i).getJsonString("name").getString();
            arr = board.getJsonObject(i).getJsonArray("scores");

            for(int j = 0; j < arr.size(); j++){
                score = arr.getInt(j);
                data.players.add(new Player(name,score));
            }
        }
    }
}
