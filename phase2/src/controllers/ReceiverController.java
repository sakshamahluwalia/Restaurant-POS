package controllers;

import model.Logger;
import java.net.URL;
import javafx.fxml.FXML;
import model.Ingredient;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.fxml.FXMLLoader;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.layout.GridPane;
import javafx.scene.control.TableView;
import javafx.collections.FXCollections;
import javafx.scene.control.TableColumn;
import javafx.collections.ObservableList;
import model.organizer.InventoryOrganizer;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * ReceiverController handles actions and controls buttons for the GUI for receiver.
 *
 * @author Eric Yuan
 * @author Saksham Ahluwalia
 * @author Patrick Calleja
 * @version %I%
 */
public class ReceiverController implements Initializable {

    /**
     * Used to display the Ingredient and their corresponding quantity
     */
    @FXML
    private TableView<Ingredient> inventoryTable;

    /**
     * The column that holds all Ingredient objects
     */
    @FXML
    private TableColumn<Ingredient, String> ingredientColumn;

    /**
     * The column that holds all the Ingredient object's quantity
     */
    @FXML
    private TableColumn<Ingredient, Integer> quantityColumn;

    /**
     * The GridPane that contains all the controls
     */
    @FXML
    private GridPane receiverGrid;

    private InventoryOrganizer inventoryOrganizer = InventoryOrganizer.getInstance();

    /**
     * Initializes all necessary objects for proper functionality of the Controller.
     *
     * @param location URL
     * @param resources ResourceBundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buildInventoryCellValueFactories();
        updateInventoryTable();
    }

    /**
     * Updates the InventoryTable with new Inventory items.
     */
    private void updateInventoryTable() {
        ObservableList<Ingredient> observableList = FXCollections.observableArrayList(inventoryOrganizer.getIngredients());
        inventoryTable.setItems(observableList);
        inventoryTable.refresh();

    }

    //TODO: non-integers values do not work
    /**
     * Builds the CellValueFactory for the columns of the TableView with PropertyValueFactories.
     */
    private void buildInventoryCellValueFactories() {
        ingredientColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        quantityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        quantityColumn.setOnEditCommit((TableColumn.CellEditEvent<Ingredient, Integer> event) -> {
            int quantity = event.getNewValue();
            Ingredient ingredient = quantityColumn.getTableView().getSelectionModel().getSelectedItem();
            inventoryOrganizer.setIngredients(ingredient, quantity);


            // Logs the new ingredient
            Logger.LOG("INGREDIENT ADDED " + ingredient.getName() +", QUANTITY: " +
                    quantity);
        });
    }

    /**
     * Handles the event of logging out (changing to the initial login GUI).
     *
     * @throws IOException the Exception that is thrown in the case where the FXML file does not exist
     */
    @FXML
    public void logOut() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
        receiverGrid.getChildren().setAll(root);
    }
}
