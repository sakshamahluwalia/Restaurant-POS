package controllers;

import model.Alert;
import java.net.URL;
import model.employee.*;
import javafx.fxml.FXML;
import java.io.IOException;
import javafx.scene.Parent;
import java.util.ArrayList;
import model.employee.Cook;
import model.employee.Server;
import javafx.fxml.FXMLLoader;
import model.employee.Receiver;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.control.ChoiceBox;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.organizer.EmployeeOrganizer;
import javafx.scene.control.PasswordField;
import controllers.manager.ManagerController;

/**
 * LoginController handles actions and controls buttons for the GUI for login.
 *
 * @author Eric Yuan
 * @author Saksham Ahluwalia
 * @author Patrick Calleja
 * @version %I%
 */
public class LoginController implements Initializable {

    /**
     * The AnchorPan that contains all the controls
     */
    @FXML
    private AnchorPane loginGrid;

    /**
     * PasswordField used to validate an employee's credentials.
     */
    @FXML
    private PasswordField password;

    /**
     * Used to select Employee objects to view their data
     */
    @FXML
    private ChoiceBox<Employee> employeeSelector;

    /**
     * Initializes all necessary objects for proper functionality of the Controller.
     *
     * @param location URL
     * @param resources ResourceBundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateEmployees();
    }

    /**
     * Loads the next GUI after a given Employee has been selected.
     *
     * @throws IOException the Exception that is thrown in the case where the FXML file does not exist
     */
    public void loadEmployee() throws IOException {
        Parent root = null;
        if (employeeSelector.getValue() != null) {
            Employee employee = employeeSelector.getSelectionModel().getSelectedItem();
            if (EmployeeOrganizer.getInstance().validateEmployee(employee, password.getText())) {
                if (employee instanceof Cook) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/cook.fxml"));
                    root = fxmlLoader.load();
                    CookController cookController = fxmlLoader.getController();
                    cookController.setup((Cook) employee);
                } else if (employee instanceof Receiver) {
                    root = FXMLLoader.load(getClass().getResource("/views/receiver.fxml"));
                } else if (employee instanceof Manager) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/manager/manager.fxml"));
                    root = fxmlLoader.load();
                    ManagerController managerController = fxmlLoader.getController();
                    managerController.setup((Manager)employee);
                } else if (employee instanceof Server) {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/views/server/server.fxml"));
                    root = fxmlLoader.load();
                    ServerController serverController = fxmlLoader.getController();
                    serverController.setup((Server)employee);
                }
            } else {
                String text = "Password was not found in our database.";
                Alert.showErrorAlert(text);
            }
            if (root != null) {
                loginGrid.getChildren().setAll(root);
            }
        } else {
            String text = "Please choose an Employee to log in.";
            Alert.showErrorAlert(text);
        }
    }

    /**
     * Updates the Employee choice box with the most current Employee objects.
     */
    private void updateEmployees() {
        ArrayList<Employee> employeesInDb = EmployeeOrganizer.getInstance().getEmployees();
        ObservableList<Employee> employees = FXCollections.observableArrayList(employeesInDb);
        employeeSelector.setItems(employees);
    }

    /**
     * Handles the event of switching to the Admin GUI.
     *
     * @throws IOException the Exception that is thrown in the case where the FXML file does not exist
     */
    public void loginAdmin() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/admin.fxml"));
        loginGrid.getChildren().setAll(root);
    }

    /**
     * Handles the key event of pressing Enter.
     *
     * @param keyEvent KeyEvent
     * @throws IOException the exception that's thrown
     */
    public void keyEvent(KeyEvent keyEvent) throws IOException {
        if (keyEvent.getCode() == KeyCode.ENTER) {
            loadEmployee();
        }
    }
}