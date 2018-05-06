package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Represents a Bill object.
 *
 * @author Saksham Ahluwalia
 * @author Patrick Calleja
 * @author Eric    Yuan
 *
 * @version %I%
 */
public class Bill implements Serializable {

    /**
     * Represents the balance of this Bill.
     */
    private double amountPayable;

    /**
     * Represents total amount to be paid for specific individuals.
     */
    private double subTotal;

    /**
     * HashMap containing all the orders from a specific table.
     */
    private HashMap<Integer, ArrayList<Order>> orders = new HashMap<>();

    /**
     * This method is responsible for updating the lists in orders.
     * @param customerId int
     * @param order model.Order
     */
    public void addOrder(int customerId, Order order) {
        if (!this.orders.containsKey(customerId)) {
            this.orders.put(customerId, new ArrayList<>());
        }
        this.orders.get(customerId).add(order);
        setAmountPayable();
    }

    /**
     * This method removes the order of a given employee from the HashMap.
     *
     * @param customerId int
     * @param order model.Order
     */
    public void removeOrder(int customerId, Order order) {
        if (orders.containsKey(customerId)) {
            orders.get(customerId).remove(order);
        }
        setAmountPayable();
    }

    /**
     * Getter for orders.
     * @return HashMap<>
     */
    public HashMap<Integer, ArrayList<Order>> getOrders() {
        return orders;
    }

    /**
     * Setter for amountPayable. Adds up the prices of all orders for
     * the table.
     */
    private void setAmountPayable() {
        amountPayable = 0;
        for (ArrayList<Order> orderId: this.orders.values()) {
            for (Order order: orderId) {
                amountPayable += order.getMenuItem().getPrice();
                for (Ingredient ingredient: order.getAddOns().keySet()) {
                    amountPayable += ingredient.getAddOnPrice() * order.getAddOns().get(ingredient);
                }
            }
        }
        subTotal = amountPayable;
    }

    /**
     * Return a String representation of this object.
     * @return String
     */
    @Override
    public String toString() {
        return "Current total for this table is: " + subTotal;
    }

    /**
     * Returns the amount remaining to be paid.
     *
     * @return returns the double of the price.
     */
    public double getAmountPayable() {
        setAmountPayable();
        return amountPayable;
    }

    public ArrayList<Order> getOrder(int customerId) {
        return orders.get(customerId);
    }

    /**
     * Returns the customer number for a bill.
     *
     * @param order The order
     * @return the customer Number
     */
    public int getCustomerNum(Order order) {
        for (int customerNum: orders.keySet()) {
            if (orders.get(customerNum).contains(order)) {
                return customerNum;
            }
        }
        return -1;
    }
}
