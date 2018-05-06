package controllers;

import javafx.scene.control.Button;
import model.*;
import model.Alert;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.IOException;
import javafx.scene.Parent;
import java.util.ArrayList;
import javafx.stage.Modality;
import model.employee.Server;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import model.organizer.MenuOrganizer;
import javafx.scene.control.ListView;
import javafx.scene.control.ChoiceBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * OrderTypeController handles actions and controls buttons for the GUI for Table and TakeOut.
 *
 * @author Eric Yuan
 * @author Saksham Ahluwalia
 * @author Patrick Calleja
 * @version %I%
 */
public class OrderTypeController {

    /**
     * ChoiceBox for displaying the Menu objects.
     */
    @FXML
    private ChoiceBox<Menu> menuChoiceBox;

    /**
     * Label used for clarity.
     */
    @FXML
    private Label numOfCustomers;

    /**
     * ListView for displaying the all Orders from a table/takeout .
     */
    @FXML
    private ListView<Order> allOrders;

    /**
     * Label added for clarity.
     */
    @FXML
    private Label serverName;

    /**
     * Label added for clarity.
     */
    @FXML
    private Label tableId;

    /**
     * OrderType object representing the Table/TakeOut object.
     */
    private OrderType orderType;

    /**
     * This represents the parents controller.
     */
    private ServerController parent;


    /**
     * This method is responsible for displaying Ids, number of customers and server name.
     */
    private void loadLabels() {
        tableId.setText(Integer.toString(orderType.getId()));
        numOfCustomers.setText(Integer.toString(orderType.getNumberOfCustomers()));
        serverName.setText(orderType.getServer().toString());
    }

    /**
     * This method is responsible for updating the menuChoiceBox with
     * a list of available menus.
     */
    private void loadMenus() {
        ArrayList<Menu> menus = MenuOrganizer.getInstance().getMenus();
        ObservableList<Menu> observableList = FXCollections.observableArrayList(menus);
        menuChoiceBox.setItems(observableList);
    }

    /**
     * Retrieves the Bill linked to this OrderType.
     */
    private void loadBill() {
        if (orderType.getBill() != null) {
            updateOrderListView();
        } else {
            orderType.setBill(new Bill());
        }
    }

    /**
     * This method is responsible for updating the ListView of all Orders.
     */
    private void updateOrderListView() {
        ArrayList<Order> ordersList = new ArrayList<>();
        for (ArrayList<Order> orders: orderType.getBill().getOrders().values()) {
            for (Order order: orders) {
                if (!order.isMade()) {
                    ordersList.add(order);
                }
            }

        }
        ObservableList<Order> observableList = FXCollections.observableArrayList(ordersList);
        allOrders.setItems(observableList);
        allOrders.refresh();
    }

    /**
     * A method used to communicate between controllers.
     * @param orderType OrderType
     */
    void setup(OrderType orderType, ServerController serverController) {
        this.parent = serverController;
        this.orderType = orderType;
        if (orderType instanceof Table) {
            loadLabels();
        } else {
            serverName.setText(orderType.getServer().toString());
        }
        loadMenus();
        loadBill();
    }

    /**
     * This method is responsible for creating a new scene to add Orders.
     * @throws IOException IOException
     */
    public void addOrder() throws IOException {
        Menu menu = menuChoiceBox.getSelectionModel().getSelectedItem();
        if (menu != null) {
            Server server = parent.getCurrentServer();
            if (canServe(server.getCurrentOrders())) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/server/add_order.fxml"));
                Parent root = fxmlLoader.load();
                AddOrderController addOrderController = fxmlLoader.getController();
                addOrderController.setup(orderType, menu);
                Stage stage = new Stage();
                stage.setTitle("Add Order");
                stage.setScene(new Scene(root, 400, 650));
                stage.initModality(Modality.APPLICATION_MODAL);
                stage.setOnCloseRequest(e -> {
                    updateOrderListView();
                    parent.updateOrdersTableView();
                });
                stage.show();
            } else {
                String text = "Please serve orders that have been completed.";
                Alert.showErrorAlert(text);
            }
        } else {
            String text = "Please select a Menu.";
            Alert.showErrorAlert(text);
        }
    }

    private boolean canServe(ArrayList<Order> orders) {
        for (Order order: orders) {
            if (order.isSeen() && order.isMade() && !order.isServed()) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method is responsible for creating a new scene to cancel Orders.
     * @throws IOException IOException
     */
    public void cancelOrder() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/server/cancel_order.fxml"));
        Parent root = fxmlLoader.load();
        CancelOrderController cancelOrderController = fxmlLoader.getController();
        cancelOrderController.setup(orderType);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("Cancel Order");
        stage.setScene(new Scene(root, 300, 500));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setOnCloseRequest(e ->
        {
            updateOrderListView();
            parent.updateOrdersTableView();
        });
        stage.show();
    }

    /**
     * This method is responsible for creating a new scene to pay Bill.
     * @throws IOException IOException
     */
    public void collectPayment() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/server/bill.fxml"));
        Parent root = fxmlLoader.load();
        BillController billController = fxmlLoader.getController();
        billController.setUp(orderType);
        billController.serverCon(parent);
        Stage stage = new Stage();
        stage.setTitle("Table Bill");
        stage.setScene(new Scene(root, 913, 651));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Sets the serverController.
     *
     * @param serverController the ServerController is to be set
     */
    public void setServerController(ServerController serverController) {
        this.parent = serverController;
    }
}
