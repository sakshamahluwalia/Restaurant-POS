package controllers;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;

/**
 * AdminController handles actions and controls buttons for the GUI for admin.
 *
 * @author Eric Yuan
 * @author Saksham Ahluwalia
 * @author Patrick Calleja
 * @version %I%
 */
public class AdminController {

    /**
     * The GridPane that contains all the controls
     */
    @FXML
    private GridPane adminGrid;

    /**
     * The AnchorPane that contains employees_tab.fxml
     */
    @FXML
    private AnchorPane employeesPane;

    /**
     * The AnchorPane that contains floors_tab.fxml
     */
    @FXML
    private AnchorPane floorsPane;

    /**
     * The AnchorPane that contains menus_tab.fxml
     */
    @FXML
    private AnchorPane menusPane;

    /**
     * The AnchorPane that contains orders_tab.fxml
     */
    @FXML
    private AnchorPane ordersPane;

    /**
     * The AnchorPane that contains inventory_tab.fxml
     */
    @FXML
    private AnchorPane inventoryPane;

    /**
     * Pre-loads all the views in the EmployeesTab with data.
     */
    @FXML
    public void loadEmployeesTab() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/admin/employees_tab.fxml"));
            Parent root = fxmlLoader.load();
            employeesPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Pre-loads all the views in the FloorsTab with data.
     */
    @FXML
    public void loadFloorsTab() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/admin/floors_tab.fxml"));
            Parent root = fxmlLoader.load();
            floorsPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Pre-loads all the views in the EmployeesTab with data.
     */
    @FXML
    public void loadMenusTab() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/admin/menus_tab.fxml"));
            Parent root = fxmlLoader.load();
            menusPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Pre-loads all the views in the OrdersTab with data.
     */
    @FXML
    public void loadOrdersTab() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/admin/orders_tab.fxml"));
            Parent root = fxmlLoader.load();
            ordersPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Pre-loads all the views in the InventoryTab with data.
     */
    @FXML
    public void loadInventoryTab() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/admin/inventory_tab.fxml"));
            Parent root = fxmlLoader.load();
            inventoryPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the event of logging out (changing to the initial login GUI).
     *
     * @throws IOException the Exception that is thrown in the case where the FXML file does not exist
     */
    @FXML
    public void logOut() throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
        adminGrid.getChildren().setAll(root);
    }
}