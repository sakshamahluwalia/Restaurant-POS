package controllers.manager;

import model.Alert;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Parent;
import java.io.IOException;
import javafx.stage.Modality;
import model.employee.Server;
import javafx.fxml.FXMLLoader;
import model.employee.Manager;
import controllers.ServerController;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane;
import model.organizer.InventoryOrganizer;

/**
 * ManagerController handles actions and controls buttons for the GUI for the Manager GUI.
 *
 * @author Eric Yuan
 * @author Saksham Ahluwalia
 * @author Patrick Calleja
 * @version %I%
 */
public class ManagerController {

    /**
     * The AnchorPane to display all selectable views.
     */
    @FXML
    private AnchorPane viewPane;

    /**
     * The Grid that contains all controls
     */
    @FXML
    private GridPane serverGrid;

    /**
     * The Manager viewing the Controller
     */
    private Manager currentManager;

    /**
     * An instance of InventoryOrganizer
     */
    private InventoryOrganizer inventoryOrganizer = InventoryOrganizer.getInstance();

    /**
     * Sets up the ManagerController with require parameters for proper functionality of the GUI.
     *
     * @param manager the manager that is to be the currentManager
     */
    public void setup(Manager manager) {
        currentManager = manager;
        openServerScreen();
    }

    /**
     * Processes the Alert when an ingredient runs below the threshold.
     */
    private void processRequestsAlert() {
        System.out.println(inventoryOrganizer.getRunningLow());
        if (inventoryOrganizer.getRunningLow().size() != 0) {
            String text = "An ingredient is running low.\n" +
                          "Please view requests to see what needs to be ordered.";
            Alert.showInformationAlert(text);
        }
    }

    /**
     * Handles the action of opening the view_requests.fxml GUI.
     */
    @FXML
    public void viewRequests() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/manager/view_requests.fxml"));
            Parent root = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setTitle("View Requests");
            stage.setScene(new Scene(root, 450, 400));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the event of opening a server screen.
     */
    @FXML
    public void openServerScreen() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/manager/server.fxml"));
            Parent root = fxmlLoader.load();
            ServerController serverController = fxmlLoader.getController();
            serverController.setup(currentManager);
            viewPane.getChildren().setAll(root);
            processRequestsAlert();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the event of opening an inventory screen.
     */
    @FXML
    public void openInventoryScreen() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/manager/inventory.fxml"));
            Parent root = fxmlLoader.load();
            viewPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the event of opening a GUI to select a server for viewing.
     */
    @FXML
    public void openCheckServer() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/manager/select_server.fxml"));
            Parent root = fxmlLoader.load();
            SelectServerController selectServerController = fxmlLoader.getController();
            selectServerController.setManagerController(this);
            Stage stage = new Stage();
            stage.setTitle("Select Server");
            stage.setScene(new Scene(root, 200, 110));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Handles the event of opening a Server screen with a selected server selected.
     *
     * @param server the server to be viewed
     */
    @SuppressWarnings("Duplicates")
    public void openCheckServerScreen(Server server) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/manager/server.fxml"));
            Parent root = fxmlLoader.load();
            ServerController serverController = fxmlLoader.getController();
            serverController.setup(server);
            viewPane.getChildren().setAll(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method will take us back to the Login screen.
     *
     * @throws IOException the exception that is thrown
     */
    @FXML
    public void logOut() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
        serverGrid.getChildren().setAll(root);
    }
}

