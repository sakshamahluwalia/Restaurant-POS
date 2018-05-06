package controllers;

import model.*;
import model.Alert;
import model.Menu;
import model.MenuItem;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import java.util.ArrayList;
import model.employee.Server;
import javafx.scene.control.*;
import javafx.stage.WindowEvent;
import javafx.scene.input.MouseEvent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.organizer.InventoryOrganizer;

/**
 * AddOrderController handles actions and controls buttons for the GUI for the AddOrder tab.
 *
 * @author Eric Yuan
 * @author Saksham Ahluwalia
 * @author Patrick Calleja
 * @version %I%
 */
public class AddOrderController {

    /**
     * ListView for displaying the Ingredients in inventory.
     */
    @FXML
    private ListView<Ingredient> ingredients;

    /**
     * ChoiceBox for displaying the MenuItems in the current Menu.
     */
    @FXML
    private ChoiceBox<MenuItem> menuItem;

    /**
     * ChoiceBox for displaying different categories in a Menu.
     */
    @FXML
    private ChoiceBox<String> category;

    /**
     * ChoiceBox for displaying all the customers on a Table.
     */
    @FXML
    private ChoiceBox<Integer> customers;

    /**
     * ListView for showing all Ingredients on the Order.
     */
    @FXML
    private ListView<Ingredient> addOns;

    /**
     * TextField used to input notes for the order.
     */
    @FXML
    private TextArea notes;

    /**
     * Button to confirm an Order.
     */
    @FXML
    private Button addOrder;

    /**
     * Represents if this Order is for a Table or TakeOut.
     */
    private OrderType orderType;

    /**
     * The Server for this Table.
     */
    private Server server;

    /**
     * The Menu for this Table.
     */
    private Menu menu;

    /**
     * A list containing all the addOns for an Order.
     */
    private ArrayList<Ingredient> toBeAddedAddOns;

    /**
     * A method used to communicate between controllers.
     * @param orderType OrderType
     * @param menu Menu.
     */
    void setup(OrderType orderType, Menu menu) {
        this.menu = menu;
        this.orderType = orderType;
        this.server = orderType.getServer();
        toBeAddedAddOns = new ArrayList<>();

        loadCustomerSelector();
        updateIngredientListView();
        updateCategoriesChoiceBox();
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
     * This method is responsible for loading up all the categories in a Menu.
     */
    private void updateCategoriesChoiceBox() {
        if (menu.getCategories() != null) {
            ObservableList<String> menuCategories = FXCollections.observableArrayList(menu.getCategories());
            category.setItems(menuCategories);
        }
    }

    /**
     * A handler for categories ChoiceBox. Used to update the menuItem ChoiceBox.
     */
    public void handleCategoryMouseClick() {
        category.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) ->
                updateMenuItemChoiceBox(category.getItems().get(observable.getValue().intValue())));
    }

    /**
     * This method loads all the menuItems from a specific category in a Menu.
     * @param category String
     */
    private void updateMenuItemChoiceBox(String category) {
        ObservableList<MenuItem> menuItems = FXCollections.observableArrayList(menu.getMenuItems(category));
        menuItem.setItems(menuItems);
    }

    /**
     * This method is responsible for loading up all the Ingredients.
     */
    private void updateIngredientListView() {
        InventoryOrganizer inventoryOrganizer = InventoryOrganizer.getInstance();
        ObservableList<Ingredient> observableList = FXCollections.observableArrayList(inventoryOrganizer.getIngredients());
        ingredients.setItems(observableList);
        ingredients.refresh();
    }

    /**
     * EventListener for ingredients ListView.
     */
    public void handleMouseClick() {
        ingredients.setOnMouseClicked(e -> addAddOns(e));
    }

    /**
     * This method adds an Ingredient to the Order.
     * @param event MouseEvent
     */
    private void addAddOns(MouseEvent event) {
        if (event.getClickCount() == 2) {
            Ingredient ingredient = ingredients.getSelectionModel().getSelectedItem();
            toBeAddedAddOns.add(ingredient);
            updateAddOnsListView();
        }
    }

    /**
     * This method is responsible for updating the addOns ListView.
     */
    private void updateAddOnsListView() {
        ObservableList<Ingredient> observableList = FXCollections.observableArrayList(toBeAddedAddOns);
        addOns.setItems(observableList);
        addOns.refresh();
    }

    /**
     * This method is responsible for the creation of an Order.
     */
    public void addOrder() {
        if (menuItem.getValue() != null) {
            Order order = new Order(server, menuItem.getValue(), orderType);

            // logs order creation
            Logger.LOG("SERVER: "  + server.getFirstName() + " " + server.getLastName() +
                    " created ORDER #" + orderType.getId() + ": " + menuItem.getValue().getName() +
                    " for TABLE #" + orderType.getId()
            );
            for (Ingredient addOn: toBeAddedAddOns) {
                order.addAddOns(1, addOn);
            }
            order.setNotes(notes.getText());
            if (customers.getValue() != null) {
                orderType.getBill().addOrder(customers.getValue(), order);
                server.addOrder(order);
                toBeAddedAddOns.clear();
                Stage stage = (Stage)addOrder.getScene().getWindow();
                stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
            } else {
                String text = "Please select a customer.";
                Alert.showErrorAlert(text);
            }
        } else {
            String text = "Please select a menu item first.";
            Alert.showErrorAlert(text);
        }

    }

}
