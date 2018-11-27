package Application;

import Assets.Player;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;

public class PlayerData extends AbstractTableModel {

    List<Player> players = new ArrayList<Player>();

    /**
     * Implemented TableModel method
     */
    public int getColumnCount() {
        return 2;
    }

    /**
     * Implemented TableModel method
     */
    public int getRowCount() {
        return players.size();
    }

    /**
     * Clears the list of players
     */
    public void dispose(){
        players.clear();
    }

    /**
     * Overridden AbstractTableModel method
     */
    public Object getValueAt(int rowIndex, int columnIndex) {
        Player player= players.get(rowIndex);
        switch(columnIndex) {
            case 0: return player.getName();
            case 1: return player.getScore();



            default: return null;


        }
    }

    @Override
    /**
     * Overridden AbstractTableModel method
     */
    public String getColumnName(int col) {
        switch(col) {
            case 0: return "Name";
            case 1: return "Score";
            default: return null;
        }
    }

    @Override
    /**
     * Overridden AbstractTableModel method
     */
    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }

    @Override
    /**
     * Overridden AbstractTableModel method
     */
    public boolean isCellEditable(int row, int col) {
        switch(col) {
            case 0: return false;
            case 1: return false;
            default: return false;
        }
    }

    @Override
    /**
     * Overridden AbstractTableModel method
     */
    public void setValueAt(Object value, int row, int col) {
    }
}
