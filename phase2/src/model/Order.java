package model;

import model.employee.Server;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

/**
 * Represents an model.Order object.
 *
 * @author Saksham Ahluwalia
 * @author Patrick Calleja
 * @author Eric Yuan
 *
 * @version %I%
 */
public class Order implements Serializable {

    /**
     * The time this order was made.
     */
    private Date dateTime;

    /**
     * The MenuItem this order represents.
     */
    private MenuItem menuItem;

    /**
     * tableNumber of the table where this order is ordered.
     */
    private OrderType orderType;

    /**
     * The Server object that created this Order.
     */
    private Server server;

    /**
     * Contains additional information about the Order.
     */
    private String notes;

    /**
     * The id of this Order Object.
     */
    private final int id;

    /**
     * The id of this Order Object.
     */
    private static int orders;

    /**
     * A HashMap of addOns on top of the Order.
     */
    private HashMap<Ingredient, Integer> addOns;

    /**
     * A boolean informing us if the order has been served.
     */
    private boolean served = false;

    /**
     * A boolean informing us if the order has been made.
     */
    private boolean made = false;

    /**
     * A boolean informing us if the order has been seen by the cook.
     */
    private boolean seen = false;

    /**
     * The serializer for this class.
     */
    private Serializer serializer = new Serializer();

    /**
     * The constructor for this class.
     * @param menuItem MenuItem
     * @param orderType int
     */
    public Order(Server server, MenuItem menuItem, OrderType orderType) {
        this.server = server;
        this.menuItem = menuItem;
        this.addOns = new HashMap<>();
        this.orderType = orderType;
        this.dateTime = new Date();
        Integer integer = serializer.deserializeInteger("numberOfOrders.ser");
        if (integer != null) {
            orders = integer;
            this.id = ++orders;
        } else {
            this.id = 0;
        }
        updateNumberOfOrders();
    }

    /**
     * This method updates the addOns ArrayList.
     * @param addOn ArrayList<Ingredient>
     */
    public void addAddOns(int quantity, Ingredient addOn) {
        if (this.addOns.containsKey(addOn)) {
            this.addOns.put(addOn, this.addOns.get(addOn) + quantity);
        } else {
            this.addOns.put(addOn, quantity);
        }
    }

    /**
     * Getter for menuItem.
     * @return MenuItem.
     */
    public MenuItem getMenuItem() {
        return menuItem;
    }

    /**
     * Returns the Server that created this order.
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
     * Getter for tableNumber.
     * @return int
     */
    public OrderType getOrderType() {
        return orderType;
    }

    /**
     * Getter for addOns.
     * @return HashMap<>
     */
    public HashMap<Ingredient, Integer> getAddOns() {
        return addOns;
    }

    /**
     * Getter for isServed.
     * @return boolean
     */
    public boolean isServed() {
        return served;
    }

    /**
     * Setter for isServed.
     * @param served boolean
     */
    public void setServed(boolean served) {
        this.served = served;
    }

    /**
     * Setter for notes.
     * @param notes String
     */
    public void setNotes(String notes) {
        this.notes = notes;
    }

    /**
     * Returns the notes for Order.
     *
     * @return the notes entered in this Order
     */
    public String getNotes() {
        return notes;
    }

    /**
     * Returns the ID of the Order.
     *
     * @return the ID of the order
     */
    public int getId() {
        return id;
    }

    /**
     * Getter for isMade.
     * @return boolean
     */
    public boolean isMade() {
        return made;
    }

    /**
     * Setter for isMade.
     * @param made boolean
     */
    public void setMade(boolean made) {
        this.made = made;
    }

    /**
     * Getter for seen.
     * @return boolean
     */
    public boolean isSeen() {
        return seen;
    }

    /**
     * Setter for seen.
     * @param seen boolean
     */
    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    /**
     * Getter for dateTime.
     * @return dateTime
     */
    public Date getDateTime() {
        return dateTime;
    }

    /**
     * Setter for DateTime.
     * @param dateTime DateTime
     */
    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

    /**
     * This method is used to serialize the orders int.
     */
    public static void updateNumberOfOrders() {
        Serializer serializer = new Serializer();
        serializer.serialize(orders, "numberOfOrders.ser");
    }

    /**
     * Used to differentiate between model.Order objects.
     * @param obj Object
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        return (obj instanceof Order) && ((Order) obj).id == this.id;
    }

    /**
     * Returns a String representation of this object.
     * @return
     */
    @Override
    public String toString() {
        return "OrderId: " + id;
    }
}
