package controllers;

import model.Floor;
import model.Table;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import java.util.ArrayList;
import model.employee.Server;
import java.util.ResourceBundle;
import javafx.stage.WindowEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import model.organizer.FloorOrganizer;
import javafx.collections.FXCollections;
import javafx.beans.value.ChangeListener;
import javafx.collections.ObservableList;
import javafx.beans.value.ObservableValue;

/**
 * AddTableController handles actions and controls buttons for the GUI for add_table.
 * add_table is a pop-up window that handles adding Table objects to the Server object.
 *
 * @author Eric Yuan
 * @author Saksham Ahluwalia
 * @author Patrick Calleja
 * @version %I%
 */
public class AddTableController implements Initializable {
    /**
     * Displays the number of Customers that are in a Table in the form of a ChoiceBox
     */
    @FXML
    private ChoiceBox<Integer> customerSelector;

    /**
     * Displays all Floor objects in the form of a ChoiceBox
     */
    @FXML
    private ChoiceBox<Floor> floorSelector;

    /**
     * Displays all Table objects in the form of a ChoiceBox
     */
    @FXML
    private ChoiceBox<Table> tableSelector;

    /**
     * The button that handles the closing of the window and adding Table to currentServer
     */
    @FXML
    private Button addTable;

    /**
     * The Server object that Table objects are to be added to
     */
    private Server currentServer;

    /**
     * An instance of FloorOrganizer to load the ChoiceBoxes
     */
    private FloorOrganizer floorOrganizer = FloorOrganizer.getInstance();


    /**
     * Initializes all necessary objects for proper functionality of the Controller.
     *
     * @param location URL
     * @param resources ResourceBundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) { //could potentially change the way changed method is implemented
        loadFloors();
        loadCustomerSelector(20);
        floorSelector.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable,
                                Number oldVal, Number newVal) {

                int index = observable.getValue().intValue();
                Floor floor = floorSelector.getItems().get(index);
                if (floor != null) {
                    loadTables(floor);
                }
            }
        });
    }

    /**
     * Updates the ChoiceBox for selecting Floor objects.
     */
    private void loadFloors() {
        ObservableList<Floor> observableList = FXCollections.observableArrayList(floorOrganizer.getFloors());
        floorSelector.setItems(observableList);
    }

    /**
     * Updates the ChoiceBox for selecting Table objects with all available Table objects on the passed in Floor object.
     *
     * @param floor the floor where the Table objects are obtained from
     */
    private void loadTables(Floor floor) {
        ArrayList<Table> availableTables = new ArrayList<>();
        for (Table table : floor.getTables()) {
            if (table.isAvailable()) {
                availableTables.add(table);
            }
        }
        ObservableList<Table> observableList = FXCollections.observableArrayList(availableTables);
        tableSelector.setItems(observableList);
    }

    /**
     * Updates the ChoiceBox with integers from 1 to number(inclusive).
     *
     * @param number ChoiceBox is loaded with integers up to number
     */
    private void loadCustomerSelector(int number) {
        ArrayList<Integer> customerNumbers = new ArrayList<>();
        for (int i = 1; i <= number; i++) {
            customerNumbers.add(i);
        }
        ObservableList<Integer> observableList = FXCollections.observableArrayList(customerNumbers);
        customerSelector.setItems(observableList);
    }

    /**
     * Handles the action of adding a Table to the currentServer's ArrayList containing Table objects the
     * Server object is currently managing.
     */
    @FXML
    public void addTable() {
        Table table = tableSelector.getSelectionModel().getSelectedItem();
        Integer numberOfCustomers = customerSelector.getSelectionModel().getSelectedItem();
        if (table != null && numberOfCustomers != null) {
            table.setServer(currentServer); // should fix these this line and next line make it one thing
            table.setNumberOfCustomers(numberOfCustomers);
            currentServer.addOrderType(table);
            Stage stage = (Stage)addTable.getScene().getWindow();
            stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        }
    }

    /**
     * Sets the currentServer of this AddTableController with the Server object that is passed in.
     *
     * @param server the Server object that is to be assigned to this controller.
     */
    void setCurrentServer(Server server) {
        currentServer = server;
    }
}
