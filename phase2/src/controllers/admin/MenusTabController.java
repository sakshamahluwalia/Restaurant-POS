package controllers.admin;

import model.*;
import model.Menu;
import java.util.*;
import java.net.URL;
import model.MenuItem;
import model.Ingredient;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.IOException;
import javafx.scene.Parent;
import javafx.scene.input.*;
import javafx.scene.chart.*;
import javafx.stage.Modality;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import model.organizer.MenuOrganizer;
import model.organizer.OrderOrganizer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.value.ObservableValue;
import javafx.beans.property.SimpleStringProperty;

/**
 * MenusTabController handles actions and controls buttons for the GUI for the Menus tab.
 *
 * @author Eric Yuan
 * @author Saksham Ahluwalia
 * @author Patrick Calleja
 * @version %I%
 */
public class MenusTabController implements Initializable {

    /**
     * TableView containing Ingredients for the current MenuItem.
     */
    @FXML
    private TableView<Map.Entry<Ingredient, Integer>> selectedItemIngredients;

    /**
     * ChoiceBox for selecting menuItem.
     */
    @FXML
    private ChoiceBox<MenuItem> selectedItem;

    /**
     * A TextField to enter menu name.
     */
    @FXML
    private TextField menuName;

    /**
     * TextField for displaying the menu category selected.
     */
    @FXML
    private TextField selectedItemCategory;

    /**
     * TextField to display the MenuItem's name.
     */
    @FXML
    private TextField selectedItemName;

    /**
     * ChoiceBox for selecting a Menu.
     */
    @FXML
    private ChoiceBox<Menu> selectedMenu;

    /**
     * ListView for displaying all Menu.
     */
    @FXML
    private ListView<Menu> menusList;

    /**
     * TableColumn that represents Ingredients.
     */
    @FXML
    private TableColumn<Map.Entry<Ingredient, Integer>, String> selectedIngredient;

    /**
     * TableColumn that represents Ingredients quantity.
     */
    @FXML
    private TableColumn<Map.Entry<Ingredient, Integer>, String> selectedIngredientQuantity;

    /**
     * TableColumn that represents Ingredients price.
     */
    @FXML
    private TextField selectedItemPrice;

    /**
     * The chart to represent wastage of the given Menu Item
     */
    @FXML
    private PieChart pieChart;

    /**
     * MenuOrganizer object to store the newly created Menu objects.
     */
    private MenuOrganizer menuOrganizer = MenuOrganizer.getInstance();

    /**
     * Initializes all necessary objects for proper functionality of the Controller.
     *
     * @param location URL
     * @param resources ResourceBundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        menusList.setPlaceholder(new Label("No Menus"));
        selectedMenu.getSelectionModel().selectedIndexProperty().addListener(
            (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                int index = observable.getValue().intValue();

                if (index != -1) {
                    Menu menu = selectedMenu.getItems().get(index);
                    updateMenuItemSelector(menu);
                }
            }
        );
        selectedItem.getSelectionModel().selectedIndexProperty().addListener(
            (ObservableValue<? extends Number> observable, Number oldValue, Number newValue) -> {
                Menu menu = selectedMenu.getSelectionModel().getSelectedItem();
                int index = newValue.intValue();
                if (index != -1 && menu != null) {
                    MenuItem menuItem = selectedItem.getItems().get(index);
                    selectedItemName.setText(menuItem.getName());
                    selectedItemCategory.setText(menu.getCategory(menuItem));
                    selectedItemPrice.setText("$" + Double.toString(menuItem.getPrice()));
                    updateTableView(menuItem);
                    updatePieChart(menuItem);
                }
            }
        );
        buildTableView();
        buildMenusListView();
        updateMenusList();
        updateMenuSelector();
    }

    /**
     * Builds the ListView for menus and sets the behaviours for actions.
     */
    private void buildMenusListView() {
        ContextMenu contextMenu = new ContextMenu();
        javafx.scene.control.MenuItem item = new javafx.scene.control.MenuItem("Add Category");
        item.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/admin/add_category.fxml"));
                try {
                    Parent root = fxmlLoader.load();
                    AddCategoryController addCategoryController = fxmlLoader.getController();
                    addCategoryController.setCurrentMenu(menusList.getSelectionModel().getSelectedItem());
                    Stage stage = new Stage();
                    stage.setTitle("Add Category");
                    stage.setScene(new Scene(root, 200, 110));
                    stage.initModality(Modality.APPLICATION_MODAL);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        contextMenu.getItems().addAll(item);
        menusList.setOnContextMenuRequested((ContextMenuEvent event) ->
                contextMenu.show(menusList, event.getScreenX(), event.getScreenY())
        );
    }

    /**
     * Handles the event of adding a Menu object to the MenuOrganizer.
     */
    @FXML
    public void addMenu(KeyEvent keyEvent) {
        clearViewArea();
        String name = menuName.getText();
        if (name.length() != 0 && keyEvent.getCode() == KeyCode.ENTER) {
            Menu menu = new Menu(name);
            menuOrganizer.addMenu(menu);
            menuName.clear();
            updateMenusList();
            updateMenuSelector();
        }
    }

    /**
     * Removes a selected Menu object when the back space button or delete button are pressed.
     *
     * @param keyEvent KeyEvent
     */
    @FXML
    public void removeMenu(KeyEvent keyEvent) {
        if (menusList.getSelectionModel().getSelectedItem() != null) {
            if ((keyEvent.getCode() == KeyCode.BACK_SPACE) || (keyEvent.getCode() == KeyCode.DELETE)) {
                Menu menu = menusList.getSelectionModel().getSelectedItem();
                menuOrganizer.removeMenu(menu);
                updateMenusList();
                updateMenuSelector();
            }
        }
    }

    /**
     * Updates the MenuList view with all the Menu objects available.
     */
    private void updateMenusList() {
        ObservableList<Menu> observableList = FXCollections.observableArrayList(menuOrganizer.getMenus());
        menusList.setItems(observableList);
        menusList.refresh();
    }

    /**
     * Handles the action of opening the Create Menu Item window.
     */
    @FXML
    public void openCreateMenuItem() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/admin/create_menu_item.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("Create Menu Item");
            stage.setOnCloseRequest(e -> clearViewArea());
            stage.setScene(new Scene(root, 450, 500));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is responsible for updating the menu ListView.
     */
    private void updateMenuSelector() {
        ObservableList<Menu> menuObservableList = FXCollections.observableArrayList(menuOrganizer.getMenus());
        selectedMenu.setItems(menuObservableList);
    }

    /**
     * Updates the MenuItem selector with all available Menu Items for a selected menu.
     *
     * @param menu Menu
     */
    private void updateMenuItemSelector(Menu menu) {
        ArrayList<MenuItem> menuItems = new ArrayList<>();
        for (ArrayList<MenuItem> items: menu.getMenu().values()) {
            menuItems.addAll(items);
        }
        ObservableList<MenuItem> observableList = FXCollections.observableArrayList(menuItems);
        selectedItem.setItems(observableList);
    }

    /**
     * Updates the total menu ingredients Table View with the most current keys and values of the selectedAddOns hashmap.
     */
    private void updateTableView(MenuItem item) {
        ObservableList<Map.Entry<Ingredient, Integer>> observableList =
                FXCollections.observableArrayList(item.getIngredients().entrySet());

        selectedItemIngredients.getItems().setAll(observableList);
        selectedItemIngredients.refresh();
    }

    /**
     * Builds and sets the cell value factories for the Table Columns for selectedIngredient and selectedQuantity.
     */
    private void buildTableView() {
        //Code adapted from https://stackoverflow.com/questions/18618653/binding-hashmap-with-tableview-javafx
        //On March 28th, 2018 at 11:10PM
        selectedIngredient.setCellValueFactory(
                (TableColumn.CellDataFeatures<Map.Entry<Ingredient, Integer>, String> p) ->
                        new SimpleStringProperty(p.getValue().getKey().getName()));
        selectedIngredientQuantity.setCellValueFactory(
                (TableColumn.CellDataFeatures<Map.Entry<Ingredient, Integer>, String> p) ->
                        new SimpleStringProperty(Integer.toString(p.getValue().getValue())));

    }

    /**
     * Clears all fields in the view Menu Item area.
     */
    @FXML
    public void clearViewArea() {
        selectedItem.getSelectionModel().clearSelection();
        selectedMenu.getSelectionModel().clearSelection();
        selectedItemCategory.clear();
        selectedItemName.clear();
        selectedItemPrice.clear();
        selectedItemIngredients.getItems().clear();
        pieChart.getData().clear();
    }

    /**
     * Updates the pie chart with the most up to date information of completed orders and wasted orders.
     */
    private void updatePieChart(MenuItem menuItem) {
        OrderOrganizer orderOrganizer = OrderOrganizer.getInstance();
        int soldCount = 0;
        int wastedCount = 0;
        for (Order order: orderOrganizer.getCompletedOrders()) {
            if (order.getMenuItem().equals(menuItem)) {
                soldCount++;
            }
        }
        for (Order order: orderOrganizer.getWastedOrders()) {
            if (order.getMenuItem().equals(menuItem)) {
                wastedCount++;
            }
        }
        PieChart.Data sold = new PieChart.Data("Sold", soldCount);
        PieChart.Data wasted = new PieChart.Data("Wasted", wastedCount);
        pieChart.getData().addAll(sold, wasted);
    }
}