package controllers;

import model.*;
import model.Alert;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import java.util.ArrayList;
import model.employee.Server;
import javafx.scene.control.*;
import javafx.stage.WindowEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * CancelOrderController handles actions and controls buttons for the GUI for the CancelOrder GUI.
 *
 * @author Eric Yuan
 * @author Saksham Ahluwalia
 * @author Patrick Calleja
 * @version %I%
 */
public class CancelOrderController {

    /**
     * ListView for displaying the current Orders of a Customer.
     */
    @FXML
    private ListView<Order> orders;

    /**
     * ChoiceBox for displaying the customers at the Table.
     */
    @FXML
    private ChoiceBox<Integer> customers;

    /**
     * Button to cancel Order.
     */
    @FXML
    private Button cancel;

    /**
     * Represents if this Order is for a Table or TakeOut.
     */
    private OrderType orderType;

    /**
     * The Server for this Table.
     */
    private Server server;

    /**
     * A method used to communicate between controllers.
     * @param orderType OrderType
     */
    void setup(OrderType orderType) {
        this.orderType = orderType;
        this.server = orderType.getServer();
        loadCustomerSelector();
    }

    /**
     * Updates the ChoiceBox with integers from 1 to number(inclusive).
     *
     */
    private void loadCustomerSelector() {
        ArrayList<Integer> customerNumbers = new ArrayList<>();
        for (int i = 1; i <= orderType.getNumberOfCustomers(); i++) {
            customerNumbers.add(i);
        }
        ObservableList<Integer> observableList = FXCollections.observableArrayList(customerNumbers);
        customers.setItems(observableList);
    }

    /**
     * A handler for customers ChoiceBox. Used to update the Orders ListView.
     */
    public void handleMouseClick() {
        customers.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> loadOrders(
                customers.getItems().get(observable.getValue().intValue())
        ));
    }

    /**
     * This method is responsible for updating the Order's ListView.
     * @param customerId int
     */
    private void loadOrders(int customerId) {
        if (orderType.getBill() != null && orderType.getBill().getOrder(customerId) != null) {
            ArrayList<Order> orders = orderType.getBill().getOrder(customerId);
            ObservableList<Order> observableList = FXCollections.observableArrayList(orders);
            this.orders.setItems(observableList);
            this.orders.refresh();
        }
    }

    /**
     * This method is responsible for cancelling an Order.
     */
    public void cancelOrder() {
        if (customers.getValue() != null) {
            if (orders.getSelectionModel().getSelectedItem() != null) {
                server.cancelOrder(orderType, orders.getSelectionModel().getSelectedItem(), customers.getValue());

                // Logs the canceled Order
                Logger.LOG("ORDER #" + orders.getSelectionModel().getSelectedItems() +" CANCELLED");

                Stage stage = (Stage)cancel.getScene().getWindow();
                stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
            } else {
                String text = "Please choose an order to cancel.";
                Alert.showErrorAlert(text);
            }
        } else {
            String text = "Please select a customer first.";
            Alert.showErrorAlert(text);
        }
    }

}
