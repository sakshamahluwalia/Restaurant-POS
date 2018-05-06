package model;

import model.employee.Server;
import java.io.Serializable;

/**
 * The Parent class for TakeOut and Table objects.
 */
public class OrderType implements Serializable {

    /**
     * The total number of customers in this Table
     */
    private int numberOfCustomers;

    /**
     * Server that is responsible for this table.
     */
    protected Server server;

    /**
     * The bill for this table.
     */
    private Bill bill;

    /**
     * The id of this OrderType
     */
    private int id;

    /**
     * The constructor for this class.
     * @param id int
     */
    public OrderType(int id) {
        this.id = id;
    }

    /**
     * Getter for server.
     * @return Server
     */
    public Server getServer() {
        return server;
    }

    /**
     * Setter for server.
     * @param server Server
     */
    public void setServer(Server server) {
        this.server = server;
    }

    /**
     * Getter for bill.
     * @return Bill
     */
    public Bill getBill() {
        return bill;
    }

    /**
     * Setter for bill.
     * @param bill Bill
     */
    public void setBill(Bill bill) {
        this.bill = bill;
    }

    /**
     * Returns the id of this OrderType.
     *
     * @return id of this OrderType
     */
    public int getId() {
        return id;
    }

    /**
     * Sets the id of this OrderType.
     *
     * @param id of this OrderType
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets the table with the number of people sitting at the Table.
     *
     * @param numberOfCustomers the number sitting at the table
     */
    public void setNumberOfCustomers(int numberOfCustomers) {
        this.numberOfCustomers = numberOfCustomers;
    }

    /**
     * Returns the number of customers currently sitting at the Table
     *
     * @return int number of customers
     */
    public int getNumberOfCustomers() {
        return numberOfCustomers;
    }
}
