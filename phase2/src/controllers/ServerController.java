package controllers;

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
import javafx.scene.layout.GridPane;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ContextMenu;
import javafx.collections.ObservableList;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.control.cell.PropertyValueFactory;



/**
 * ServerController handles actions and controls buttons for the GUI for server.
 *
 * @author Eric Yuan
 * @author Saksham Ahluwalia
 * @author Patrick Calleja
 * @version %I%
 */
public class ServerController {

    /**
     * LsitView responsible for displaying all the Tables and TakeOuts.
     */
    @FXML
    private ListView<OrderType> orderType;

    /**
     * GridPane encasing the Server grid.
     */
    @FXML
    private GridPane serverGrid;

    /**
     * AnchorPane encasing orderTypePane.
     */
    @FXML
    private AnchorPane orderTypePane;

    /**
     * Represents the current Server.
     */
    private Server currentServer;

    /**
     * Table View to hold the current orders for Server.
     */
    @FXML
    private TableView<Order> orders;

    /**
     * TableColumn in TableView that displays Order id.
     */
    @FXML
    private TableColumn<Order, Integer> ordersOrder;

    /**
     * TableColumn in TableView that displays if the Order has been seen by the cook.
     */
    @FXML
    private TableColumn<Order, Boolean> ordersSeen;

    /**
     * TableColumn in TableView that displays if the Order has been made by the cook.
     */
    @FXML
    private TableColumn<Order, Boolean> ordersCooked;

    /**
     * TableColumn in TableView that displays if the Order has been served by the server.
     */
    @FXML
    private TableColumn<Order, Boolean> ordersServed;

    /**
     * A method used to communicate between controllers.
     * @param server Server
     */
    public void setup(Server server) {
        currentServer = server;
        buildOrdersTableView();
        buildMenusListView();
        updateOrderTypeList();
        updateOrdersTableView();
    }

    /**
     * This method is responsible for updating the orders Table View.
     */
    void updateOrdersTableView() {
        if (currentServer.getCurrentOrders() != null) {
            ObservableList<Order> observableList = FXCollections.observableArrayList(currentServer.getCurrentOrders());
            orders.setItems(observableList);
            orders.refresh();
        }
    }

    /**
     * This method is responsible for creating the columns in the orders TableView.
     */
    private void buildOrdersTableView() {
        ordersOrder.setCellValueFactory(new PropertyValueFactory<>("id"));
        ordersSeen.setCellValueFactory(new PropertyValueFactory<>("seen"));
        ordersCooked.setCellValueFactory(new PropertyValueFactory<>("made"));
        ordersServed.setCellValueFactory(new PropertyValueFactory<>("served"));
    }

    /**
     * This method is responsible for creating a window to create a new Table.
     */
    @FXML
    public void openAddTable() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/server/add_table.fxml"));
            Parent root = fxmlLoader.load();
            AddTableController addTableController = fxmlLoader.getController();
            addTableController.setCurrentServer(currentServer);
            Stage stage = new Stage();
            stage.setTitle("Add Table");
            stage.setScene(new Scene(root, 300, 200));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setOnCloseRequest(e -> updateOrderTypeList());
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is responsible for adding a TakeOut object.
     */
    @FXML
    public void addTakeOut() {
        TakeOut takeOut = new TakeOut();
        takeOut.setServer(currentServer);
        takeOut.setNumberOfCustomers(1);
        currentServer.addOrderType(takeOut);
        updateOrderTypeList();
    }

    /**
     * This method is responsible for updating the OrderTypeListView.
     */
    public void updateOrderTypeList() {
        ArrayList<OrderType> orderTypes = currentServer.getCurrentOrderTypes();
        if (orderTypes.size() != 0) {
            ObservableList<OrderType> observableList = FXCollections.observableArrayList(orderTypes);
            orderType.setItems(observableList);
            orderType.refresh();
        } else {
            orderType.getItems().clear();
        }
    }

    /**
     * This method is responsible for showcasing the TakeOut/Table GUI.
     */
    @FXML
    public void updateOrderTypeGridPane() {
        OrderType selectedItem = orderType.getSelectionModel().getSelectedItem();
        Parent root = null;
        try {
            if (selectedItem != null) {
                FXMLLoader fxmlLoader = null;
                if (selectedItem instanceof TakeOut) {
                    fxmlLoader = new FXMLLoader(getClass().getResource("/views/server/takeout.fxml"));
                } else if (selectedItem instanceof Table) {
                    fxmlLoader = new FXMLLoader(getClass().getResource("/views/server/table.fxml"));
                }
                if (fxmlLoader != null) {
                    root = fxmlLoader.load();
                    OrderTypeController orderTypeController = fxmlLoader.getController();
                    orderTypeController.setup(selectedItem, this);
                    orderTypeController.setServerController(this);
                }
            }
            if (root != null) {
                orderTypePane.getChildren().setAll(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method will take us back to the Login screen.
     * @throws IOException IOException
     */
    @FXML
    public void logOut() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
        serverGrid.getChildren().setAll(root);
    }

    /**
     * Builds the ListView for menus and sets the behaviours for actions.
     */
    private void buildMenusListView() {
        ContextMenu contextMenu = new ContextMenu();
        javafx.scene.control.MenuItem item = new javafx.scene.control.MenuItem("Serve Order");
        item.setOnAction(e -> {
                Order order = orders.getSelectionModel().getSelectedItem();
                if (order != null) {
                    if (order.isMade() && order.isSeen()) {
                        order.setServed(true);
                        currentServer.getCurrentOrders().remove(order);
                        updateOrdersTableView();
                    } else {
                        String text = "The order has not been seen or cooked by the Cook.";
                        Alert.showErrorAlert(text);
                    }
                } else {
                    String text = "Please select an order.";
                    Alert.showErrorAlert(text);
                }
        });
        contextMenu.getItems().addAll(item);
        orders.setOnContextMenuRequested((ContextMenuEvent event) ->
                contextMenu.show(orders, event.getScreenX(), event.getScreenY())
        );
    }

    /**
     * Returns the current Server object for this controller.
     *
     * @return the current Server object
     */
    Server getCurrentServer() {
        return currentServer;
    }
}
