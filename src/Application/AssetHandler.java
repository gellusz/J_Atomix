package Application;

import Assets.Atom;
import Assets.Hitbox;
import Assets.Molecule;
import Assets.Wall;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class AssetHandler {
    private JsonArray levels;

    /**
     * Constructor
     * <p>
     * Reads a JsonArray that is stored by the class for further use
     * @param src file name (expected to be in res folder!) */
    public AssetHandler(final String src){
        try{
            JsonReader jsonReader = Json.createReader(new FileInputStream("res\\" + src));
            JsonObject obj = jsonReader.readObject();
            this.levels = obj.getJsonArray("levels");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * Gets the name of the level (that is the name of the molecule) from the JsonArray of the class
     * @param id the id of the actual level
     * @return name of level (molecule) */
    public String getLevelName(final int id){
        return levels.getJsonObject(id-1).getString("name");
    }

    /**
     * Sets up the models of atoms in an ArrayList from the JsonArray of the class to the actual level
     * @param id the id of the actual level
     * @return ArrayList (recipe) of Atoms */
    public ArrayList<Atom> loadRecipe(final int id){
        ArrayList<Atom> list = new ArrayList<>();
        JsonObject atoms = levels.getJsonObject(id-1).getJsonObject("atoms");
        for(int i = 0; i < atoms.size(); i++ ){
            list.add(new Atom(0,0,Integer.parseInt(atoms.getJsonArray(Integer.toString(i+1)).getJsonString(0).getString()),
                    atoms.getJsonArray(Integer.toString(i+1)).getJsonString(1).getString()));
        }
        return list;
    }

    /**
     * Loads all the Atoms and Walls of the level in the appropriate position from the JsonArray of the class.
     * And puts them into a HashMap.
     * @param id the id of the actual level
     * @param atoms models of different atoms (recipe) to be loaded
     * @return HashMap of Hitboxes */
    public HashMap<String, Hitbox> loadAssets(final int id, ArrayList<Atom> atoms){
        JsonArray arena = levels.getJsonObject(id -1).getJsonArray("arena");
        String[] rows = new String[arena.size()];
        int wallNum = 0;
        int atomNum = 0;
        int idx;

        for(int i = 0; i < arena.size(); i++){
            rows[i] = arena.getJsonString(i).getString();
        }
        HashMap<String, Hitbox> assets = new HashMap<>();

        for(int i = 0; i < rows.length; i++){
            for(int j = 0; j < rows[0].length(); j++){
                switch (rows[i].charAt(j)){
                    case '.':
                        break;
                    case '#':
                        assets.put("wall" + wallNum++, new Wall(32*j + 1,32*i + 1));
                        break;
                    default:
                        idx = Integer.parseInt(String.valueOf(rows[i].charAt(j))) - 1;
                        assets.put("atom" + atomNum++, new Atom(32*j + 1, 32*i + 1,
                                atoms.get(idx).getType(),atoms.get(idx).getBonds()));
                        break;
                }
            }
        }

        return assets;
    }

    /**
     * Loads the corresponding data for the Molecule of the level from the JsonArray of the class, and creates it
     * @param id the id of the actual level
     * @return Molecule for the actual level */
    public Molecule loadMolecule(final int id){
        JsonArray molecule = levels.getJsonObject(id-1).getJsonArray("molecule");
        ArrayList<String> arr = new ArrayList<>();

        for(int i = 0; i < molecule.size(); i++){
            arr.add(molecule.getJsonString(i).getString());
        }

        String[] ret = new String[arr.size()];
        arr.toArray(ret);

        return new Molecule(ret);
    }
}
