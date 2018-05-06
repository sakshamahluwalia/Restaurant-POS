import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.Order;
import model.TakeOut;
import model.employee.Employee;
import model.organizer.*;

import java.io.IOException;

/**
 * Main initializes and starts the Application.
 *
 * @author Eric Yuan
 * @author Saksham Ahluwalia
 * @author Patrick Calleja
 * @version %I%
 */
public class Main extends Application {

    @Override
    public void init() {
    }

    /**
     * Starts the Application with settings locked.
     *
     * @param stage Stage
     * @throws IOException the Exception that is thrown if a .fxml cannot be found
     */
    @Override
    public void start(Stage stage) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/views/login.fxml"));
        Scene scene = new Scene(root, 1280, 800);
        stage.setTitle("Restaurant System");
//        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Stops the Application and serializes important info.
     *
     * @throws IOException IOException.
     */
    @Override
    public void stop() throws Exception {
        EmployeeOrganizer.getInstance().updateEmployees();
        FloorOrganizer.getInstance().updateFloors();
        InventoryOrganizer.getInstance().updateIngredients();
        MenuOrganizer.getInstance().updateMenus();
        OrderOrganizer.getInstance().updateOrderLists();
        OrderOrganizer.updateStats();
        TakeOut.updateNumberOfCustomer();
        Order.updateNumberOfOrders();
        Employee.updateEmployeeIds();
        super.stop();
    }

    /**
     * Launches the Application.
     *
     * @param args String[]
     */
    public static void main(String[] args) {
        launch();
    }
}
