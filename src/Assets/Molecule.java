package Assets;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;

public class Molecule {
    private int[][] chkArr;
    private boolean atomInPlace[][];
    private Atom relMin, actor;
    private int offsetX, offsetY;
    private int i,j,x0;

    /**
     * Constructor:
     * Initiates data of the class, and checker arrays
     * @param rows forming a matrix as stored in the JSON file
     */
    public Molecule(final String[] rows){
        relMin = null;
        actor = null;
        chkArr = new int[rows.length][rows[0].length()];
        atomInPlace = new boolean[rows.length][rows[0].length()];
        offsetX = 9999;
        offsetY = 9999;
        x0 = 0;
        for(int i = 0; i < rows.length; i++){
            for(int j = 0; j < rows[0].length(); j++){
                if(rows[i].charAt(j) == '.'){
                    atomInPlace[i][j] = true;
                    chkArr[i][j] = 0;
                }else {
                    atomInPlace[i][j] = false;
                    chkArr[i][j] = Integer.parseInt(String.valueOf(rows[i].charAt(j)));
                }
            }
        }
    }

    /**
     * Draws the image of the Molecule Atom by Atom on the screen
     * @param g the Graphics instance to handle this
     * @param recipe the models of atoms to be drawn
     */
    public void drawImg(Graphics g, ArrayList<Atom> recipe){
        for(int i = 0; i < chkArr.length; i++){
            for(int j = 0; j < chkArr[0].length; j++){
                for(int k = 0; k < recipe.size(); k++){
                    if(chkArr[i][j] == k+1 ){
                        recipe.get(k).drawImg(g,j*32,i*32);
                    }
                }
            }
        }
    }

    /**
     * Initializes the components to make the checker algorithm
     * (that checks whether the Player has successfully put the atoms together) work.
     * @param recipe models of atoms in molecule for comparison
     * @param assets contains the corresponding Atom for the algorithm
     */
    public void initChecker(ArrayList<Atom> recipe, Map<String, Hitbox> assets){
        int iter = 0;
        while(relMin == null){
            for(int j = 0; j < recipe.size(); j++){
                if(chkArr[0][iter] == j+1){
                    relMin = recipe.get(j);

                }
            }
            iter++;
        }
        x0 = (iter-1)*32;

        assets.forEach((key,value) ->{
            if(key.matches("atom(.*)")){
                if((value.getPosX() < offsetX) && (value.getPosY() < offsetY) && value.equals(relMin)){
                    offsetX = value.getPosX();
                    offsetY = value.getPosY();
                    actor = (Atom)value;
                }
            }
        });

        relMin = actor;
    }

    /**
     * Implements the checker algorithm (that checks whether the Player has successfully put the atoms together)
     * @param recipe models of atoms in molecule for comparison
     * @param assets atoms on playing field for comparison
     * @return true if the Player succeeded
     */
    public boolean matches(ArrayList<Atom> recipe, Map<String, Hitbox> assets){
        offsetX = relMin.getPosX() - x0;
        offsetY = relMin.getPosY();
        for(i = 0; i < chkArr.length; i++){
            for(j = 0; j < chkArr[0].length; j++){
                if(chkArr[i][j] != 0 ){
                    atomInPlace[i][j] = false;
                    actor = recipe.get(chkArr[i][j]-1);
                    assets.forEach((key, value) ->{
                        if(key.matches("atom(.*)")){
                            if((value.getPosX() - offsetX == 32* j) && (value.getPosY() - offsetY == 32* i)
                                    && value.equals(actor)){
                                atomInPlace[i][j] = true;
                            }
                        }
                    });
                }
            }
        }

        int truthCnt = 0;
        for(int i = 0; i < chkArr.length; i++){
            for(int j = 0; j < chkArr[0].length; j++) {
                if(atomInPlace[i][j]){
                    truthCnt++;
                }
            }
        }

        return (truthCnt == chkArr.length*chkArr[0].length);
    }
}
