package controllers.admin;

import model.Alert;
import model.Logger;
import java.net.URL;
import javafx.fxml.FXML;
import model.Ingredient;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.TableColumn;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.organizer.InventoryOrganizer;
import javafx.util.converter.DoubleStringConverter;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * InventoryTabController handles actions and controls buttons for the GUI for the Inventory tab.
 *
 * @author Eric Yuan
 * @author Saksham Ahluwalia
 * @author Patrick Calleja
 * @version %I%
 */
public class InventoryTabController implements Initializable {

    /**
     * Used to display a the Restaurant's inventory level
     */
    @FXML
    private TableView<Ingredient> inventoryTable;

    /**
     * Used to display all Ingredients' name
     */
    @FXML
    private TableColumn<Ingredient, String> ingredientColumn;

    /**
     * Used to display all Ingredients' quantity
     */
    @FXML
    private TableColumn<Ingredient, Integer> quantityColumn;

    /**
     * Used to display all Ingredients' cost to buy
     */
    @FXML
    private TableColumn<Ingredient, Double> costColumn;

    /**
     * Used to display all Ingredients' addOn cost
     */
    @FXML
    private TableColumn<Ingredient, Double> addOnPriceColumn;

    /**
     * Used to provide the Ingredient's name to be added to InventoryOrganizer
     */
    @FXML
    private TextField ingredientName;

    /**
     * Used to provide the Ingredient's cost to be added to InventoryOrganizer
     */
    @FXML
    private TextField ingredientCost;

    /**
     * Used to provide the Ingredient's addOn price to be added to InventoryOrganizer
     */
    @FXML
    private TextField ingredientAddOnPrice;

    /**
     * An instance of InventoryOrganizer
     */
    private InventoryOrganizer inventoryOrganizer = InventoryOrganizer.getInstance();

    /**
     * Initializes all necessary objects for proper functionality of the Controller.
     *
     * @param location URL
     * @param resources ResourceBundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateInventoryTable();
        buildInventoryCellValueFactories();
    }

    /**
     * Creates and adds an Ingredient with the values provided to the InventoryOrganizer.
     */
    public void addIngredient() {
        String name = ingredientName.getText();
        String costString = ingredientCost.getText();
        String addOnString = ingredientAddOnPrice.getText();
        if (name.length() != 0 && costString.length() != 0 && addOnString.length() != 0) {
            Double cost = Double.parseDouble(costString);
            Double addOn = Double.parseDouble(addOnString);
            Ingredient ingredient = new Ingredient(name, cost, addOn);
            inventoryOrganizer.addIngredients(ingredient, 0);
            updateInventoryTable();
            ingredientCost.setText("");
            ingredientName.setText("");
            ingredientAddOnPrice.setText("");

            // Logs the new ingredient
            Logger.LOG("INGREDIENT ADDED " + ingredientName.getText() +", QUANTITY: " +
                    inventoryOrganizer.getQuantity(ingredient) );
        } else {
            String text = "You have not entered in all the required information. " +
                    "Please make sure all fields are completed.";
            Alert.showErrorAlert(text);
        }


    }

    /**
     * Updates the InventoryTable with new Inventory items.
     */
    private void updateInventoryTable() {
        ObservableList<Ingredient> observableList = FXCollections.observableArrayList(inventoryOrganizer.getIngredients());
        inventoryTable.setItems(observableList);
        inventoryTable.refresh();
    }

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
        });
        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));
        costColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        costColumn.setOnEditCommit((TableColumn.CellEditEvent<Ingredient, Double> event) -> {
            double cost = event.getNewValue();
            Ingredient ingredient = costColumn.getTableView().getSelectionModel().getSelectedItem();
            ingredient.setCost(cost);
        });
        addOnPriceColumn.setCellValueFactory(new PropertyValueFactory<>("addOnPrice"));
        addOnPriceColumn.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        addOnPriceColumn.setOnEditCommit((TableColumn.CellEditEvent<Ingredient, Double> event) -> {
            double addOnCost = event.getNewValue();
            Ingredient ingredient = costColumn.getTableView().getSelectionModel().getSelectedItem();
            ingredient.setAddOnPrice(addOnCost);
        });
    }


}
