package model.employee;

import model.Order;
import model.OrderType;
import model.Table;
import java.util.ArrayList;

/**
 * A model.employee.Server manages tables and adds model.Order objects to model.Table objects.
 *
 * @author Eric Yuan, Patrick Calleja, Saksham Ahluwalia
 * @version  %I%
 */
public class Server extends Employee {

    /**
     * The ArrayList of OrderTypes the Server is currently managing.
     */
    private ArrayList<OrderType> currentOrderTypes;

    /**
     * The ArrayList of Orders the Server is currently managing.
     */
    private ArrayList<Order> currentOrders;

    /**
     * Constructs a model.employee.Server.
     */
    public Server(String firstName, String lastName, String password) {
        super(firstName, lastName, password);
        currentOrderTypes = new ArrayList<>();
        currentOrders = new ArrayList<>();
    }

    /**
     * Adds a new OrderType to currentTables.
     *
     * @param orderType orderType to be added
     */
    public void addOrderType(OrderType orderType) {
        currentOrderTypes.add(orderType);
    }

    /**
     * Adds an model.Order associated with the customerNumber to the model.Table's model.Bill.
     *
     * @param order Order
     */
    public void addOrder(Order order) {
        currentOrders.add(order);
        getOrderOrganizer().addOrder(order);
    }

    /**
     * Returns the current Table objects that this server is managing.
     *
     * @return ArrayList of Table objects
     */
    public ArrayList<OrderType> getCurrentOrderTypes() {
        return currentOrderTypes;
    }

    /**
     * Returns the current Table objects that this server is managing.
     *
     * @return ArrayList of Table objects
     */
    public ArrayList<Order> getCurrentOrders() {
        return currentOrders;
    }

    /**
     * Cancels an model.Order associated with the customerNumber from the model.Table's model.Bill.
     *
     * @param orderType model.Table
     * @param order model.Order
     * @param customerNumber int
     */
    public void cancelOrder(OrderType orderType, Order order, int customerNumber) {
        currentOrders.remove(order);
        orderType.getBill().removeOrder(customerNumber, order);
        getOrderOrganizer().cancelOrder(order);
    }

}
