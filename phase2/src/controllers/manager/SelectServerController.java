package controllers.manager;

import model.Alert;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import java.util.ArrayList;
import model.employee.Server;
import model.employee.Employee;
import javafx.stage.WindowEvent;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.organizer.EmployeeOrganizer;

/**
 * SelectServerController handles actions and controls buttons for GUI that's responsible for selecting a Server.
 *
 * @author Eric Yuan
 * @author Saksham Ahluwalia
 * @author Patrick Calleja
 * @version %I%
 */
public class SelectServerController implements Initializable {

    /**
     * The button that is responsible for firing the event of selecting a Server
     */
    @FXML
    private Button selectServer;

    /**
     * The choicebox that displays all Server objects
     */
    @FXML
    private ChoiceBox<Server> serverSelector;

    /**
     * An instance of the ManagerController
     */
    private ManagerController managerController;

    /**
     * An instance of the EmployeeOrganizer
     */
    private EmployeeOrganizer employeeOrganizer = EmployeeOrganizer.getInstance();

    /**
     * Initializes all necessary objects for proper functionality of the Controller.
     *
     * @param location URL
     * @param resources ResourceBundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loadServerSelector();
    }

    /**
     * Sets the ManagerController.
     *
     * @param managerController the ManagerController we are setting
     */
    public void setManagerController(ManagerController managerController) {
        this.managerController = managerController;
    }

    /**
     * Loads the ChoiceBox for selecting servers.
     */
    private void loadServerSelector() {
        ArrayList<Server> servers = new ArrayList<>();
        for (Employee employee: employeeOrganizer.getEmployees()) {
            if (employee instanceof Server) {
                servers.add((Server)employee);
            }
        }
        ObservableList<Server> observableList = FXCollections.observableArrayList(servers);
        serverSelector.setItems(observableList);
    }

    /**
     * Handles action of closing the Select Server GUI.
     */
    @FXML
    public void addCategory() {
        Server server = serverSelector.getSelectionModel().selectedItemProperty().get();
        if (server != null) {
            managerController.openCheckServerScreen(server);
            Stage stage = (Stage)selectServer.getScene().getWindow();
            stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        } else {
            String text = "Please select a Server.";
            Alert.showErrorAlert(text);
        }
    }

}

