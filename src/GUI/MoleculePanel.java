package GUI;

import Assets.Atom;
import Assets.Molecule;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class MoleculePanel extends JPanel {
    private Molecule molecule;
    private ArrayList<Atom> recipe;

    /**
     * Constructor:
     * Initializes components.
     * @param m molecule to be shown
     * @param r models of Atoms in Molecule
     */
    public MoleculePanel(Molecule m, ArrayList<Atom> r){
        molecule = m;
        recipe = r;
    }

    @Override
    /**
     * Overridden method of JComponent.
     * Draws molecule on screen.
     */
    public void paint(Graphics g){
        molecule.drawImg(g,recipe);
    }
}
