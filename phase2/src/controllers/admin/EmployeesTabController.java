package controllers.admin;

import java.net.URL;
import model.employee.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.image.ImageView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Order;
import model.organizer.EmployeeOrganizer;
import model.organizer.OrderOrganizer;

/**
 * EmployeesTabController handles actions and controls buttons for the GUI for the Employees tab.
 *
 * @author Eric Yuan
 * @author Saksham Ahluwalia
 * @author Patrick Calleja
 * @version %I%
 */
public class EmployeesTabController implements Initializable {

    /**
     * Used for providing the firstName parameter in Employee object
     */
    @FXML
    private TextField employeeFirstName;

    /**
     * Used for providing the lastName parameter in Employee object
     */
    @FXML
    private TextField employeeLastName;

    /**
     * Used for providing the position parameter in Employee object
     */
    @FXML
    private TextField employeePosition;

    /**
     * Used for providing the password parameter in Employee object
     */
    @FXML
    private PasswordField employeePassword;

    /**
     * Used to display all Employee objects in the Restaurant
     */
    @FXML
    private ListView<Employee> employees;

    /**
     * Used to display the name of the Employee selected
     */
    @FXML
    private TextField nameField;

    /**
     * Used to display the date of hiring of the Employee selected
     */
    @FXML
    private TextField employedSinceField;

    /**
     * Used to display the position of the Employee selected
     */
    @FXML
    private TextField positionField;

    /**
     * Used to display the id of the Employee selected
     */
    @FXML
    private TextField idField;

    /**
     * The chart that displays the data of the Server objects
     */
    @FXML
    private BarChart<String, Integer> barChart;

    /**
     * An instance of an EmployeeOrganizer
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
        employees.setPlaceholder(new Label("No Employees"));
        updateEmployeesList();
    }

    /**
     * Handles the event of adding an Employee object to the EmployeeOrganizer.
     */
    @FXML
    public void addEmployee() {
        String firstName = employeeFirstName.getText();
        String lastName = employeeLastName.getText();
        String position = employeePosition.getText();
        String password = employeePassword.getText();
        employeeOrganizer.buildEmployee(firstName, lastName, password, position);
        employeeFirstName.clear();
        employeeLastName.clear();
        employeePosition.clear();
        updateEmployeesList();
    }

    /**
     * Removes a selected Employee object when the back space button or delete button are pressed.
     *
     * @param keyEvent KeyEvent
     */
    @FXML
    public void removeEmployee(KeyEvent keyEvent) {
        if (employees.getSelectionModel().getSelectedItem() != null) {
            if ((keyEvent.getCode() == KeyCode.BACK_SPACE) || (keyEvent.getCode() == KeyCode.DELETE)) {
                Employee employee = employees.getSelectionModel().getSelectedItem();
                employeeOrganizer.removeEmployee(employee);
                updateEmployeesList();
            }
        }
    }

    /**
     * Used to update the ListView of Employees.
     */
    private void updateEmployeesList() {
        ObservableList<Employee> observableArrayList = FXCollections.observableArrayList(employeeOrganizer.getEmployees());
        employees.setItems(observableArrayList);
        employees.setCellFactory(param -> new ListCell<Employee>() {
            @Override
            public void updateItem(Employee employee, boolean empty) {
                // Images from https://www.flaticon.com/authors/smashicons
                Image cookIcon = new Image(getClass().getResourceAsStream("/images/icons/cook-64.png"));
                Image managerIcon = new Image(getClass().getResourceAsStream("/images/icons/manager-64.png"));
                Image serverIcon = new Image(getClass().getResourceAsStream("/images/icons/server-64.png"));
                Image receiverIcon = new Image(getClass().getResourceAsStream("/images/icons/receiver-64.png"));
                ImageView imgView = null;
                super.updateItem(employee, empty);
                if (empty) {
                    setText(null);
                    setGraphic(null);
                } else {
                    if (employee instanceof Cook) {
                        imgView = new ImageView(cookIcon);
                        setText(" Employee Type: Cook\n" + " Name: " + employee.toString());
                    } else if (employee instanceof Receiver) {
                        imgView = new ImageView(receiverIcon);
                        setText(" Employee Type: Receiver\n" + " Name: " + employee.toString());
                    } else if (employee instanceof Manager) {
                        imgView = new ImageView(managerIcon);
                        setText(" Employee Type: Manager\n" + " Name: " + employee.toString());
                    } else if (employee instanceof Server) {
                        imgView = new ImageView(serverIcon);
                        setText(" Employee Type: Server\n" + " Name: " + employee.toString());
                    }
                    setGraphic(imgView);
                }
            }
        });
        employees.refresh();
    }

    /**
     * Handles selection actions of the listview.
     */
    @FXML
    public void handleListViewSelection() {
        clearEmployeeFields();
        Employee employee = employees.getSelectionModel().getSelectedItem();
        if (employee != null) {
            nameField.setText(employee.getFirstName() + " " + employee.getLastName());
            idField.setText(Integer.toString(employee.getId()));
            employedSinceField.setText(employee.getDate().toString());
            if (employee instanceof Manager) {
                positionField.setText("Manager");
                buildBarGraphs((Manager)employee);
            } else if (employee instanceof Server) {
                positionField.setText("Server");
                buildBarGraphs((Server)employee);
            }
        }
    }

    /**
     * Builds the bar chart for a passed in Server.
     *
     * @param server displays the data for this server
     */
    private void buildBarGraphs(Server server) {
        OrderOrganizer orderOrganizer = OrderOrganizer.getInstance();
        int total = 0;
        int refunds = 0;
        for (Order order: orderOrganizer.getWastedOrders()) {
            if (order.getServer().equals(server)) {
                refunds++;
            }
        }
        for (Order order: orderOrganizer.getCompletedOrders()) {
            if (order.getServer().equals(server)) {
                total++;
            }
        }
        total += refunds;

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Sales");
        series1.getData().add(new XYChart.Data<>("Sales", total));

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Refunds");
        series2.getData().add(new XYChart.Data<>("Refunds", refunds));

        barChart.getData().addAll(series1, series2);
        barChart.setAnimated(false);
    }

    /**
     * Clears all the text fields and bar chart values.
     */
    private void clearEmployeeFields() {
        employedSinceField.clear();
        nameField.clear();
        positionField.clear();
        idField.clear();
        barChart.getData().clear();
    }
}
