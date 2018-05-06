package controllers.admin;

import java.net.URL;
import javafx.fxml.FXML;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.chart.XYChart;
import model.organizer.OrderOrganizer;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.NumberAxis;

/**
 * OrdersTabController handles actions and controls buttons for the GUI for the Orders tab.
 *
 * @author Eric Yuan
 * @author Saksham Ahluwalia
 * @author Patrick Calleja
 * @version %I%
 */
public class OrdersTabController implements Initializable{

    /**
     * The BarChart that displays order information
     */
    @FXML
    private BarChart<String, Integer> barChartLeft;

    /**
     * The axis that represents the number of orders
     */
    @FXML
    private NumberAxis leftYAxis;

    /**
     * The BarChart that displays sales information
     */
    @FXML
    private BarChart<String, Integer> barChartRight;

    /**
     * The axis that represents the amount of sales in dollars
     */
    @FXML
    private NumberAxis rightYAxis;

    /**
     * An instance of OrderOrganizer
     */
    private OrderOrganizer orderOrganizer = OrderOrganizer.getInstance();

    /**
     * Initializes all necessary objects for proper functionality of the Controller.
     *
     * @param location URL
     * @param resources ResourceBundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buildLeftBarChart();
        buildRightBarChart();
    }

    /**
     * Builds the the bar chart that displays the orders overview.
     */
    private void buildLeftBarChart() {
        int numCompletedOrders = orderOrganizer.getCompletedOrders().size();
        int numWastedOrders = orderOrganizer.getWastedOrders().size();
        leftYAxis.setLabel("Number of Orders");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Sale");
        series1.getData().add(new XYChart.Data<>("Sale", numCompletedOrders));

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Wasted");
        series2.getData().add(new XYChart.Data<>("Wasted", numWastedOrders));

        barChartLeft.getData().setAll(series1, series2);
        barChartLeft.setTitle("Orders Overview");
    }

    /**
     * Builds the the bar chart that displays the sales overview.
     */
    private void buildRightBarChart() {
        double restaurantExpenses = orderOrganizer.getExpenses();
        double restaurantRevenue = orderOrganizer.getRevenue();

        rightYAxis.setLabel("Dollars");

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Revenue");
        series1.getData().add(new XYChart.Data<>("Revenue", restaurantRevenue));

        XYChart.Series series2 = new XYChart.Series();
        series2.setName("Expenses");
        series2.getData().add(new XYChart.Data<>("Expenses", restaurantExpenses));

        XYChart.Series series3 = new XYChart.Series();
        series3.setName("Profit");
        series3.getData().add(new XYChart.Data<>("Profit", restaurantRevenue - restaurantExpenses));

        barChartRight.getData().setAll(series1, series2, series3);
        barChartRight.setTitle("Sales Overview");
    }
}
