package controllers.admin;

import model.Bill;
import model.Order;
import model.Table;
import model.Floor;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import model.organizer.FloorOrganizer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * FloorsTabController handles actions and controls buttons for the GUI for the Floor tab.
 *
 * @author Eric Yuan
 * @author Saksham Ahluwalia
 * @author Patrick Calleja
 * @version %I%
 */
public class FloorsTabController implements Initializable {
    /**
     * Used to display all Floor objects in the Restaurant
     */
    @FXML
    private ListView<Floor> floorList;

    /**
     * Used to display the selected Table's id
     */
    @FXML
    private TextField selectedTableId;

    /**
     * Used to display the selected Table's bill balance
     */
    @FXML
    private TextField selectedTableBalance;

    /**
     * Used to display the selected Table's Server employee
     */
    @FXML
    private TextField selectedTableServer;

    /**
     * Used to display the selected Table's Order objects
     */
    @FXML
    private TextArea selectedTableOrders;

    /**
     * Used to display all the Table objects on a given Floor
     */
    @FXML
    private ListView<Table> tableList;

    /**
     * Used as a id parameter for creating Table objects
     */
    @FXML
    private TextField tableId;

    /**
     * Used as a name parameter for creating Floor objects
     */
    @FXML
    private TextField floorName;

    /**
     * An instance of FloorOrganizer
     */
    private FloorOrganizer floorOrganizer = FloorOrganizer.getInstance();

    /**
     * Initializes all necessary objects for proper functionality of the Controller.
     *
     * @param location URL
     * @param resources ResourceBundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateFloorList();
    }

    /**
     * Handles the actions made on the FloorList ListView.
     */
    @FXML
    public void handleFloorListActions() {
        if (tableList.getSelectionModel().getSelectedItem() != null) {
            tableList.getItems().clear();
            selectedTableId.setText("");
            selectedTableServer.setText("");
            selectedTableBalance.setText("");
            selectedTableOrders.setText("");
        }
        if (floorList.getSelectionModel().getSelectedItem() != null) {
            updateTableList();
        }
    }

    /**
     * Handles the event of adding a Floor object to the FloorOrganizer.
     */
    @FXML
    public void addFloor() {
        String name = floorName.getText();
        if (name.length() != 0) {
            Floor floor = new Floor(name);
            floorOrganizer.addFloor(floor);
            floorName.clear();
            updateFloorList();
        }
    }

    /**
     * Removes a selected Floor object when the back space button or delete button are pressed.
     *
     * @param keyEvent KeyEvent
     */
    @FXML
    public void removeFloor(KeyEvent keyEvent) {
        if (floorList.getSelectionModel().getSelectedItem() != null) {
            if ((keyEvent.getCode() == KeyCode.BACK_SPACE) || (keyEvent.getCode() == KeyCode.DELETE)) {
                Floor floor = floorList.getSelectionModel().getSelectedItem();
                floorOrganizer.removeFloor(floor);
                updateFloorList();
            }
        }
    }

    /**
     * Used to update the ListView of Floor objects.
     */
    private void updateFloorList() {
        ObservableList<Floor> observableList = FXCollections.observableArrayList(floorOrganizer.getFloors());
        floorList.setItems(observableList);
        floorList.refresh();
    }

    /**
     * Handles the event of adding a Table object to a selected Floor.
     */
    @FXML
    public void addTable() {
        int id = Integer.parseInt(tableId.getText());
        Floor floor = floorList.getSelectionModel().getSelectedItem();
        if (id != 0 && floor != null) {
            Table table = new Table(floor, id);
            floor.addTable(table);
            tableId.clear();
            updateTableList();
        }
    }

    /**
     * Removes a selected Table object when the back space button or delete button are pressed.
     *
     * @param keyEvent KeyEvent
     */
    @FXML
    public void removeTable(KeyEvent keyEvent) {
        Floor floor = floorList.getSelectionModel().getSelectedItem();
        Table table = tableList.getSelectionModel().getSelectedItem();
        if (floor != null && table != null) {
            if ((keyEvent.getCode() == KeyCode.BACK_SPACE) || (keyEvent.getCode() == KeyCode.DELETE)) {
                floor.removeTable(table);
                updateTableList();
            }
        }
    }

    /**
     * Updates the ListView of Table objects.
     */
    private void updateTableList() {
        Floor floor = floorList.getSelectionModel().getSelectedItem();
        ObservableList<Table> observableList = FXCollections.observableArrayList(floor.getTables());
        tableList.setItems(observableList);
        tableList.refresh();
    }

    /**
     * Updates the all the information of a selected Table object.
     */
    @FXML
    public void updateTableInfo() {
        Table table = tableList.getSelectionModel().getSelectedItem();
        if (table != null) {
            selectedTableId.setText(Integer.toString(table.getId()));
            if (!table.isAvailable()) {
                selectedTableServer.setText(table.getServer().toString());
                selectedTableBalance.setText(Double.toString(table.getBill().getAmountPayable()));
                selectedTableOrders.setText(buildOrderString(table.getBill()));
            }
        }
    }

    /**
     * Returns a String that is ready to be outputted as all orders for customer objects.
     *
     * @param bill Bill for a given Table
     * @return a String of all the customers and their orders
     */
    private String buildOrderString(Bill bill) {
        StringBuilder sb = new StringBuilder();
        for (Integer integer: bill.getOrders().keySet()) {
            for (Order order: bill.getOrders().get(integer)) {
                sb.append("Customer " + integer + ": " + order.getMenuItem().toString() + System.lineSeparator());
            }
        }
        return sb.toString();
    }
}
