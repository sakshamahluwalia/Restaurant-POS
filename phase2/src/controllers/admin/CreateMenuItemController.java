package controllers.admin;


import model.Menu;
import model.Alert;
import java.net.URL;
import java.util.Map;
import model.MenuItem;
import javafx.fxml.FXML;
import model.Ingredient;
import java.util.HashMap;
import javafx.scene.control.*;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import model.organizer.MenuOrganizer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.organizer.InventoryOrganizer;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.SimpleStringProperty;



/**
 * CreateMenuItemController handles the creation of a new Menu Item by using input from the GUI.
 *
 * @author Eric Yuan
 * @author Saksham Ahluwalia
 * @author Patrick Calleja
 * @version %I%
 */
public class CreateMenuItemController implements Initializable {

    /**
     * The price of the MenuItem
     */
    @FXML
    private TextField menuItemPrice;

    /**
     * A list of all ingredients that can be added to the MenuItem
     */
    @FXML
    private ListView<Ingredient> allIngredients;

    /**
     * The name of the MenuItem
     */
    @FXML
    private TextField menuItemName;

    /**
     * A selection of all the menus
     */
    @FXML
    private ChoiceBox<Menu> menuItemMenu;

    /**
     * The TableView to display the ingredients that are to be added to the MenuItem
     */
    @FXML
    private TableView<Map.Entry<Ingredient, Integer>> menuItemIngredients;

    /**
     * The TableColumn to display the ingredient
     */
    @FXML
    private TableColumn<Map.Entry<Ingredient, Integer>, String> selectedIngredient;

    /**
     * The TableColumn to display the quantity of the ingredient
     */
    @FXML
    private TableColumn<Map.Entry<Ingredient, Integer>, String> selectedQuantity;

    /**
     * A selection of all the categories in the menu
     */
    @FXML
    private ChoiceBox<String> menuItemCategory;

    /**
     * An instance of a MenuOrganizer
     */
    private MenuOrganizer menuOrganizer = MenuOrganizer.getInstance();

    /**
     * An instance of an InventoryOrganizer
     */
    private InventoryOrganizer inventoryOrganizer = InventoryOrganizer.getInstance();

    /**
     * Holds all the ingredients that the MenuItem will have
     */
    private HashMap<Ingredient, Integer> selectedAddOns = new HashMap<>();

    /**
     * Initializes all necessary objects for proper functionality of the Controller.
     *
     * @param location URL
     * @param resources ResourceBundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadSelectMenu();
        loadIngredients();
        buildTableView();
    }

    /**
     * Loads the menu selection choice box with all the available menus.
     */
    private void loadSelectMenu() {
        ObservableList<Menu> observableList = FXCollections.observableArrayList(menuOrganizer.getMenus());
        menuItemMenu.setItems(observableList);
        menuItemMenu.getSelectionModel().selectedIndexProperty().addListener(
            (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                int index = observable.getValue().intValue();
                if (index != -1) {
                    Menu menu = menuItemMenu.getItems().get(index);
                    loadCategories(menu);
                }
            }
        );
    }

    /**
     * Loads all the categories of a selected Menu.
     *
     * @param menu the menu where we are loading the categories from
     */
    private void loadCategories(Menu menu) {
        ObservableList<String> observableList = FXCollections.observableArrayList(menu.getCategories());
        menuItemCategory.setItems(observableList);
    }

    /**
     * Loads the allIngredients ListView with all the available ingredients.
     */
    private void loadIngredients() {
        ObservableList<Ingredient> observableList = FXCollections.observableArrayList(inventoryOrganizer.getIngredients());
        allIngredients.setItems(observableList);
        allIngredients.refresh();
    }

    /**
     * Handles the event of adding an ingredient to the Menu Item that is about to be made.
     *
     * @param mouseEvent listens for the number of clicks of the mouseEvent
     */
    @FXML
    public void addIngredient(MouseEvent mouseEvent) {
        Ingredient ingredient = allIngredients.getSelectionModel().getSelectedItem();
        if (ingredient != null && mouseEvent.getClickCount() == 2) {
            if (selectedAddOns.containsKey(ingredient)) {
                int num = selectedAddOns.get(ingredient) + 1;
                selectedAddOns.put(ingredient, num);
            } else {
                selectedAddOns.put(ingredient, 1);
            }
            updateTableView();
        }
    }

    /**
     * Updates the total menu ingredients Table View with the most current keys and values of the selectedAddOns hashmap.
     */
    private void updateTableView() {
        ObservableList<Map.Entry<Ingredient, Integer>> observableList = FXCollections.observableArrayList(selectedAddOns.entrySet());
        menuItemIngredients.getItems().setAll(observableList);
        menuItemIngredients.refresh();
    }

    /**
     * Builds and sets the cell value factories for the Table Columns for selectedIngredient and selectedQuantity.
     */
    private void buildTableView() {
        //Code adapted from https://stackoverflow.com/questions/18618653/binding-hashmap-with-tableview-javafx
        //On March 28th, 2018 at 11:10PM
        selectedIngredient.setCellValueFactory((TableColumn.CellDataFeatures<Map.Entry<Ingredient, Integer>, String> p)
                -> new SimpleStringProperty(p.getValue().getKey().getName())
            );
        selectedQuantity.setCellValueFactory( (TableColumn.CellDataFeatures<Map.Entry<Ingredient, Integer>, String> p)
            -> new SimpleStringProperty(Integer.toString(p.getValue().getValue()))
        );

    }

    /**
     * Handles the creation of a MenuItem.
     * Uses all the input entered by the user to create this MenuItem.
     */
    @FXML
    public void createMenuItem() {
        String name = menuItemName.getText();
        String priceString = menuItemPrice.getText();
        Menu menu = menuItemMenu.getSelectionModel().getSelectedItem();
        String category = menuItemCategory.getSelectionModel().getSelectedItem();

        if (menu != null && name != null && priceString != null && category != null && selectedAddOns.size() != 0) {
            Double price = Double.parseDouble(priceString);
            MenuItem menuItem = new MenuItem(name, price);
//            menuItem.setIngredients(selectedAddOns);
            for (Ingredient ingredient: selectedAddOns.keySet()) {
                menuItem.addIngredients(ingredient, selectedAddOns.get(ingredient));
            }
            menu.addMenuItem(menuItem, category);
            menuItemMenu.getSelectionModel().clearSelection();
            menuItemName.clear();
            menuItemPrice.clear();
            menuItemCategory.getSelectionModel().clearSelection();
            selectedAddOns.clear();
            updateTableView();
        } else {
            String text = "You have not entered in all the required information. " +
                          "Please make sure all fields are completed.";
            Alert.showErrorAlert(text);
        }

    }
}