package model;

import model.employee.Server;

import java.io.Serializable;

/**
 * Represents a table in a restaurant.
 *
 * @author Saksham Ahluwalia
 * @author Patrick Calleja
 * @author Eric Yuan
 *
 * @version %I%
 */
public class Table extends OrderType {

    /**
     * Represents if the table is available.
     */
    private boolean available;

    /**
     * The floor that this table belongs to.
     */
    protected Floor floor;

    /**
     * The constructor for this class.
     * @param tableId int
     */
    public Table(Floor floor, int tableId) {
        super(tableId);
        this.floor = floor;
        this.available = true;
    }

    /**
     * Changes the values of bill, server and available to default.
     */
    public void reset() {
        //TODO not currently using setBill since bill doesnt exist
        setBill(null);
        setServer(null);
        setNumberOfCustomers(0);
        this.available = true;
    }

    /**
     * Getter for available.
     * @return boolean
     */
    public boolean isAvailable() {
        return available;
    }

    /**
     * Getter for floor
     * @return Floor
     */
    public Floor getFloor() {
        return this.floor;
    }

    /**
     * Setter for server.
     * @param server Server
     */
    public void setServer(Server server) {
        super.setServer(server);
        available = false;
    }

    /**
     * Returns true if Table has the same floor and Table id.
     *
     * @param object Object to be compared to model.Table
     * @return true if the two Objects are equal, false otherwise.
     */
    @Override
    public boolean equals(Object object) {
        return object instanceof Table && ((Table) object).getFloor().equals(this.floor)
                && ((Table) object).getId() == getId();
    }

    /**
     * Returns a String representation of Table.
     *
     * @return the String version of Table
     */
    @Override
    public String toString() {
        return "Table: " + getId();
    }
}
