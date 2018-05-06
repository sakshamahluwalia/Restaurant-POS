package model.organizer;

import model.Order;
import model.Ingredient;
import model.Serializer;
import java.util.ArrayList;

/**
 * This class is responsible for keeping track
 * of the completedOrder, wastedOrders and the profits made in a day.
 *
 * @author Saksham Ahluwalia
 * @author Patrick Calleja
 * @author Eric Yuan
 *
 * @version %I%
 */
public class OrderOrganizer {

    /**
     * Revenue made in a day.
     */
    private static double revenue = 0;

    /**
     * Resources used for orders.
     */
    private static double expenses = 0;

    /**
     * A collection to store all the pending orders.
     */
    private ArrayList<Order> orders;

    /**
     * A list containing all the completed orders.
     */
    private ArrayList<Order> completedOrders;

    /**
     * A list containing orders that were wasted.
     */
    private ArrayList<Order> wastedOrders;

    /**
     * The serializer object used to serialize lists of orders.
     */
    private Serializer<Order> serializer = new Serializer<>();

    /**
     * Represents an InventoryManager responsible for
     * updating the inventory.
     */
    private InventoryOrganizer inventoryOrganizer = InventoryOrganizer.getInstance();

    /**
     * Exists to remove Instantiation.
     */
    private static final OrderOrganizer orderOrganizer = new OrderOrganizer();

    /**
     * The constructor for this class.
     */
    private OrderOrganizer() {
        orders = serializer.deserialize("orders.ser");
        completedOrders = serializer.deserialize("completedOrders.ser");
        wastedOrders = serializer.deserialize("wastedOrders.ser");
        Integer integer = serializer.deserializeInteger("expenses.ser");
        if (integer != null) {
            expenses = integer;
        } else {
            expenses = 0;
        }
        integer = serializer.deserializeInteger("revenue.ser");
        if (integer != null) {
            revenue = integer;
        } else {
            revenue = 0;
        }
        updateStats();
    }

    /**
     * Returns an instance of this class.
     * @return model.organizer.OrderOrganizer
     */
    public static OrderOrganizer getInstance() {
        return orderOrganizer;
    }

    /**
     * Responsible for adding an order to orders.
     * @param order model.Order
     */
    public void addOrder(Order order) {
        orders.add(order);
//        updateOrderLists();
    }

    /**
     * Is used to mark a order as completed. Updates the revenue and
     * expenses accordingly.
     * @param order model.Order
     */
    public void completeOrder(Order order) {
        orders.remove(order);
        completedOrders.add(order);
        for (Ingredient ingredient: order.getAddOns().keySet()) {
            expenses += ingredient.getCost();
            revenue  += ingredient.getAddOnPrice();
        }
        expenses += order.getMenuItem().getExpenses();
        revenue += order.getMenuItem().getPrice();
        inventoryOrganizer.removeIngredients(order.getAddOns());
        inventoryOrganizer.removeIngredients(order.getMenuItem().getIngredients());
    }

    /**
     * Responsible for the cancellation of an model.Order. If the order has
     * not been completed remove it from the list otherwise move it from
     * completedOrder to wastedOrder and update the revenue.
     *
     * @param order model.Order
     */
    public void cancelOrder(Order order) {
        if (orders.contains(order)) {
            orders.remove(order);
            if (order.getAddOns().size() > 0) {
                inventoryOrganizer.removeIngredients(order.getAddOns());
                inventoryOrganizer.removeIngredients(order.getMenuItem().getIngredients());
            }
        } else if (completedOrders.contains(order)) {
            completedOrders.remove(order);
            wastedOrders.add(order);
            if (order.getAddOns().size() > 0) {
                for (Ingredient ingredient: order.getAddOns().keySet()) {
                    revenue  -= ingredient.getAddOnPrice();
                }
            }
            revenue -= order.getMenuItem().getPrice();
        }
        updateOrderLists();
    }

    /**
     * Getter for completedOrder
     * @return ArrayList<model.Order>
     */
    public ArrayList<Order> getCompletedOrders() {
//        completedOrders = serializer.deserialize("completedOrders.ser");
        return completedOrders;
    }


    /**
     * Returns the ArrayList containing all model.Order objects that have been made but cancelled.
     *
     * @return ArrayList of model.Order objects that have been cancelled after being made
     */
    public ArrayList<Order> getWastedOrders() {
//        wastedOrders = serializer.deserialize("wastedOrders.ser");
        return wastedOrders;
    }

    /**
     * Returns the ArrayList containing all model.Order objects that are currently in queue to be completed.
     *
     * @return the ArrayList containing all pending model.Order objects.
     */
    public ArrayList<Order> getOrders() {
//        orders = serializer.deserialize("orders.ser");
        return orders;
    }

    /**
     * Getter for expenses.
     * @return double
     */
    public double getExpenses() {
        return expenses;
    }

    /**
     * Getter for revenue
     * @return double
     */
    public double getRevenue() {
        return revenue;
    }

    /**
     * Setter for inventoryManager.
     */
    public void setInventoryOrganizer() {
        this.inventoryOrganizer = InventoryOrganizer.getInstance();
    }

    /**
     * Helper method to reset the instance variables marking a end of a day.
     */
    public void reset() {
        revenue = 0;
        expenses = 0;
        completedOrders = new ArrayList<>();
        wastedOrders = new ArrayList<>();
        orders.clear();
        updateOrderLists();
    }

    /**
     * Updates all the ArrayLists of this class by serializing the information.
     */
    public void updateOrderLists() {
        serializer.serialize(orders, "orders.ser");
        serializer.serialize(completedOrders, "completedOrders.ser");
        serializer.serialize(wastedOrders, "wastedOrders.ser");
    }

    /**
     * This method is used to serialize the orders int.
     */
    public static void updateStats() {
        Serializer serializer = new Serializer();
        serializer.serialize((int)expenses, "expenses.ser");
        serializer.serialize((int)revenue, "revenue.ser");
    }
}
