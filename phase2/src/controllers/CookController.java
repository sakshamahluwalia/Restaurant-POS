package controllers;

import model.Alert;
import model.Order;
import model.Logger;
import java.net.URL;
import javafx.fxml.FXML;
import model.Ingredient;
import java.util.HashMap;
import model.employee.Cook;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.control.ListView;
import model.organizer.OrderOrganizer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * CookController handles actions and controls buttons for the GUI for cook.
 *
 * @author Eric Yuan
 * @author Saksham Ahluwalia
 * @author Patrick Calleja
 * @version %I%
 */
public class CookController implements Initializable {
    /**
     * Used to display special requests of an order
     */
    @FXML
    private TextArea notes;

    /**
     * Used to display add-on ingredients of an order
     */
    @FXML
    private TextArea addOns;

    /**
     * The GridPane that contains all the controls
     */
    @FXML
    private GridPane cookGrid;

    /**
     * Used to display the Server that made the order
     */
    @FXML
    private TextArea serverName;

    /**
     * Used to display the origin table
     */
    @FXML
    private TextArea tableId;

    /**
     * Used to display the MenuItem of the order
     */
    @FXML
    private TextArea menuItem;

    /**
     * Used to display all Order objects that are to be completed
     */
    @FXML
    private ListView<Order> ordersList;

    private Cook cook;

    /**
     * Initializes all necessary objects for proper functionality of the Controller.
     *
     * @param location URL
     * @param resources ResourceBundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateOrderList();
        ordersList.setPlaceholder(new Label("No Orders"));
    }

    /**
     * A method used to communicate between controllers.
     * @param cook Server
     */
    void setup(Cook cook) {
        this.cook = cook;
    }

    /**
     * Pre-loads the Orders ListView with all Orders that are pending to be complete.
     */
    @FXML
    public void loadOrderSelected() {
        Order order = ordersList.getSelectionModel().getSelectedItem();
        if (order != null) {
            serverName.setText(order.getServer().toString());
            tableId.setText(Integer.toString(order.getOrderType().getId()));
            menuItem.setText(order.getMenuItem().toString());
            addOns.setText(buildAddOnsString(order.getAddOns()));
            notes.setText(order.getNotes());
        }
    }

    /**
     * Returns a String concatenation of all add-on Ingredient objects and the quantity of the Ingredient.
     *
     * @param hashMap HashMap of Ingredient and int (quantity)
     * @return String
     */
    private String buildAddOnsString(HashMap<Ingredient, Integer> hashMap) {
        StringBuilder sb = new StringBuilder();
        for (Ingredient ingredient: hashMap.keySet()) {
            sb.append(ingredient + ": " + Integer.toString(hashMap.get(ingredient)));
        }
        return sb.toString();
    }

    /**
     * Selects an order on the orders ListView and completes the order.
     */
    @FXML
    public void completeOrder() {
        Order order = ordersList.getSelectionModel().getSelectedItem();
        if (order != null) {
            if (checkFirstOrder(order)) {
                if (order.isSeen()) {
                    boolean completed = cook.completeOrder(order);
                    if (!completed) {
                        String text = "Not enough ingredients to complete the order.\n" +
                                "Please notify " + order.getServer().toString() + " that Order " + order.getId() +
                                " for Table " + order.getOrderType().getId() +
                                " has not been completed and removed from their bill.";
                        int customerId = order.getOrderType().getBill().getCustomerNum(order);
                        order.getOrderType().getServer().cancelOrder(order.getOrderType(), order, customerId);
                        Alert.showErrorAlert(text);
                    } else {
                        updateOrderList();

                        Logger.LOG("COOK " + cook.getFirstName() + " " + cook.getLastName() +
                                " has completed ORDER #" + order.getId());
                    }
                } else {
                    String text = "Please see the order.";
                    Alert.showErrorAlert(text);
                }
            } else {
                String text = "Please complete the first order.";
                Alert.showErrorAlert(text);
            }
        } else {
            String text = "Please select an order.";
            Alert.showErrorAlert(text);
        }
        updateOrderList();
    }

    /**
     * This method is used to check if the Order to be completed is the first Order in the queue.
     * @return boolean
     */
    private boolean checkFirstOrder(Order orderToCheck) {
        if (orderToCheck != null) {
            return ordersList.getSelectionModel().getSelectedIndex() == 0;
        }
        return false;
    }

    /**
     * Selects an order on the orders ListView and completes the order.
     */
    @FXML
    public void seenOrder() {
        Order order = ordersList.getSelectionModel().getSelectedItem();
        if (order != null) {
            cook.seenOrder(order);
            updateOrderList();
        } else {
            String text = "Please select an order.";
            Alert.showErrorAlert(text);
        }
    }

    /**
     * Updates the orders ListView containing all the orders that are to be completed.
     */
    private void updateOrderList() {
        OrderOrganizer orderOrganizer = OrderOrganizer.getInstance();
        ObservableList<Order> observableList = FXCollections.observableArrayList(orderOrganizer.getOrders());
        ordersList.setItems(observableList);
        ordersList.refresh();
    }

    /**
     * Handles the event of logging out (changing to the initial login GUI).
     *
     * @throws IOException the Exception that is thrown in the case where the FXML file does not exist
     */
    @FXML
    public void logOut() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
        cookGrid.getChildren().setAll(root);
    }
}
