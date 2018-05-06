package model;

import java.io.Serializable;
import java.util.ArrayList;
/**
 * A Floor contains Table objects.
 *
 * @author Eric Yuan, Saksham Ahluwalia, Patrick Calleja
 * @version %I%
 */
public class Floor implements Serializable {
    /**
     * The name of this Floor
     */
    private String floorName;

    /**
     * The ArrayList that contains a list of Table objects.
     */
    private ArrayList<Table> tables;

    /**
     * The serializer for this class used to serialize/deserialize tables.
     */
    private Serializer<Table> serializer = new Serializer<>();

    /**
     * Constructs a Floor.
     *
     * @param floorName the name of this Floor
     */
    public Floor(String floorName) {
        this.floorName = floorName;
        tables = serializer.deserialize(this.floorName+".ser");
    }

    /**
     * Adds a Table object to tables.
     *
     * The method checks to see if an existing Table with the same ID is already in tables.
     * If table is not in table then the Table object is added to tables.
     * @param table a Table that is to be added this Floor
     * @see Table
     */
    public void addTable(Table table) {
        if (!tables.contains(table)) {
            tables.add(table);
        }
        updateTables();
    }

    /**
     * Removes a Table object from tables.
     *
     * The method checks to see if the Table object is already in tables.
     * If table is inside of tables, then the table is removed.
     *
     * @param table a Table that is to be removed from this Floor
     * @see Table
     */
    public void removeTable(Table table) {
        if (tables.contains(table)) {
            tables.remove(table);
        }
        updateTables();
    }

    /**
     * Returns a Table object with the same tableID as the tableID given as a parameter.
     *
     * The method checks to see if there exists a table with the same tableID.
     * If found, the method returns the Table, otherwise returns null.
     * @param tableID the tableID of the Table that we're trying to find in tables
     * @return the model.Table object with the same tableID
     */
    public Table getTable(int tableID) {
        for (Table table: tables) {
            if (table.getId() == tableID) {
                return table;
            }
        }
        return null;
    }

    private void updateTables() {
        serializer.serialize(tables, floorName+".ser");
    }

    /**
     * Returns the ArrayList of Tables that are currently on this Floor.
     *
     * @return the ArrayList of Tables on this Floor
     */
    public ArrayList<Table> getTables() {
        return tables;
    }

    /**
     * Returns true if the object is an instance of Floor, object contains the same
     * name as this Floor.
     *
     * @param object the object that is to be compared with this model.Floor
     * @return true if the two Objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        return object instanceof Floor && ((Floor)object).floorName.equalsIgnoreCase(floorName);
    }

    /**
     * Returns a String representation of Floor.
     *
     * @return String version of Floor object
     */
    @Override
    public String toString() {
        return floorName;
    }
}
