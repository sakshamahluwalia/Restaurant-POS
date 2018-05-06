package controllers;

import model.*;
import javafx.fxml.FXML;
import java.util.ArrayList;
import javafx.scene.control.*;
import javafx.scene.text.Text;
import java.text.DecimalFormat;
import javafx.scene.control.cell.PropertyValueFactory;

public class BillController {

    /**
     * TableView to hold MenuItems.
     */
    @FXML
    private TableView<ReceiptContent> billTableView;

    /**
     * TableColumn to hold a customer number.
     */
    @FXML
    private TableColumn<ReceiptContent, String> customerNum;

    /**
     * TableColumn to hold item info.
     */
    @FXML
    private TableColumn<ReceiptContent, String> itemInfo;

    /**
     * TableColumn to hold quantity of an ingredient.
     */
    @FXML
    private TableColumn<ReceiptContent, String> quantity;

    /**
     * TableColumn to hold the price.
     */
    @FXML
    private TableColumn<ReceiptContent, String> price;

    /**
     * TableView to hold added Menu Items.
     */
    @FXML
    private TableView<ReceiptContent> receiptView;

    /**
     * TableColumn to hold the item info.
     */
    @FXML
    private TableColumn<ReceiptContent, String> itemInfoReceipt;

    /**
     * TableColumn to hold the quantity of the added item.
     */
    @FXML
    private TableColumn<ReceiptContent, String> quantityInfoReceipt;

    /**
     * TableColumn to hold the price of the added item.
     */
    @FXML
    private TableColumn<ReceiptContent, String> priceReceipt;

    /**
     * Label to show the total.
     */
    @FXML
    private Label tableTotal;

    /**
     * Label to show the  receipt total.
     */
    @FXML
    private Label receiptTotal;

    /**
     * Label to show the table/takeout id.
     */
    @FXML
    private Text tableNumText;

    /**
     * Label to hold table/takeout.
     */
    @FXML
    private Text tableText;

    /**
     * Label to show tax.
     */
    @FXML
    private Label taxOutLabel;

    /**
     * Label to show gratuity.
     */
    @FXML
    private Label gratuityOutLabel;

    /**
     * Button for a 10% tip option.
     */
    @FXML
    private Button tipOption1;

    /**
     * Button for a 15% tip option.
     */
    @FXML
    private Button tipOption2;

    /**
     * Button for a 20% tip option.
     */
    @FXML
    private Button tipOption3;

    /**
     * Represents the OrderType this bill belongs too.
     */
    private OrderType orderType;

    /**
     * Receipt object linked to the bill.
     */
    private Receipt tableReceipt;

    /**
     * Receipt object linked to the bill.
     */
    private Receipt generatedReceipt;

    /**
     * Represents the gratuity.
     */
    private double gratuity = 0;

    /**
     * Represents the tax on the order.
     */
    private double tax = 0;

    /**
     * Represents the total amount to be payed.
     */
    private double total;

    /**
     * Represents the controller for the parent.
     */
    private ServerController parent;

    /**
     * This method is used to communicate between controllers.
     * @param orderType OrderType
     */
    public void setUp(OrderType orderType) {
        this.orderType = orderType;
        setViews();
        if (orderType.getClass() == Table.class) {
            tableNumText.setText(Integer.toString(this.orderType.getId()));
        } else {
            tableText.setText("TakeOut #");
            tableNumText.setText(Integer.toString(this.orderType.getId()));
        }
        setOrderTypeViews();


    }

    /**
     * Helper method to calculate tip.
     * @param tipOption Button
     * @param tipOption0 Button
     * @param rate double
     */
    private void tipOption(Button tipOption, Button tipOption0, double rate) {
        if (generatedReceipt.getObservable().size() != 0) {

            if (tipOption.isDisabled() && tipOption0.isDisabled()) {
                tipOption.setDisable(false);
                tipOption0.setDisable(false);
                gratuity = 0;
            } else {
                tipOption.setDisable(true);
                tipOption0.setDisable(true);
                gratuity = generatedReceipt.getTotal() * rate;
            }
            refresh();
        }
    }

    /**
     * The button function for the first tip option.
     */
    @FXML
    private void tipOption1() {
        tipOption(tipOption2, tipOption3, 0.1);
    }

    /**
     * The button function for the second tip option.
     */
    @FXML
    private void tipOption2() {
        tipOption(tipOption1, tipOption3, 0.15);
    }

    /**
     * The button function for the third tip option.
     */
    @FXML
    private void tipOption3() {
        tipOption(tipOption2, tipOption1, 0.2);
    }

    /**
     * Handles the 'move order back to table' button.
     */
    @FXML
    private void backOrderBtn() {

        // Update the Table and generated Receipt
        tableReceipt.concat(receiptView.getSelectionModel().getSelectedItems());
        generatedReceipt.removeAll(receiptView.getSelectionModel().getSelectedItems());
        refresh();
    }

    /**
     * Handles the 'Add to Receipt' button.
     */
    @FXML
    private void addToReceipt() {

        generatedReceipt.concat(billTableView.getSelectionModel().getSelectedItems());
        tableReceipt.removeAll(billTableView.getSelectionModel().getSelectedItems());
        // Update the Table and generated Receipt
        if (orderType.getNumberOfCustomers() >= 8) {
            gratuity = generatedReceipt.getTotal() * 0.15;
            tipOption1.setDisable(true);
            tipOption2.setDisable(true);
            tipOption3.setDisable(true);
        }
        refresh();
    }

    /**
     * Method to pay the bill.
     */
    @FXML
    private void payButton() {
        // Remove orders from Bill
        for (ReceiptContent receiptContent: generatedReceipt.getObservable()) {
            // Retrieve orders from a customer
            ArrayList<Order> orders = orderType.getBill().getOrder(receiptContent.getCustomerNum());
            Order paidOrder = null;
            for (Order o: orders) {
                if (o.getId() == receiptContent.getOrderId()){
                    paidOrder = o;
                }
            }
            if (paidOrder != null) {
                orderType.getBill().removeOrder(receiptContent.getCustomerNum(), paidOrder);
            }
        }
        if (billTableView.getItems().size() == 0) {
            orderType.getServer().getCurrentOrderTypes().remove(orderType);

        }
        generatedReceipt.getObservable().clear();
        refresh();
        parent.updateOrderTypeList();
        if (orderType instanceof Table) {
            ((Table) orderType).reset();
        }
    }
    /**
     * Sets the Views for Table and Receipt.
     */
    @FXML
    private void setOrderTypeViews() {

        // Allow to select multiple models
        receiptView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        billTableView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        tableReceipt = new Receipt();
        billTableView.setItems(tableReceipt.generateReceipt(orderType.getBill()));
        tableTotal.setText(new DecimalFormat("#0.00").format(tableReceipt.getTotal()));
        billTableView.refresh();
    }

    /**
     * Helper function that sets up all TableViews.
     */
    private void setViews() {

        tableReceipt = new Receipt();
        generatedReceipt = new Receipt();

        //For the Table View
        customerNum.setCellValueFactory(new PropertyValueFactory<>("customerNum"));
        itemInfo.setCellValueFactory(new PropertyValueFactory<>("menuItem"));
        quantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        price.setCellValueFactory(new PropertyValueFactory<>("price"));

        //For the Receipt View
        itemInfoReceipt.setCellValueFactory(new PropertyValueFactory<>("menuItem"));
        quantityInfoReceipt.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceReceipt.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Refresh the TableView and Label fields
     */
    private void refresh() {
        // Sets the data for both Views
        if (this.orderType instanceof Table) {
            billTableView.setItems(tableReceipt.getObservable());
        }
        receiptView.setItems(generatedReceipt.getObservable());

        tax = generatedReceipt.getTotal() * 0.13;
        gratuityOutLabel.setText(new DecimalFormat("#0.00").format(gratuity));
        taxOutLabel.setText(new DecimalFormat("#0.00").format(tax));
        total = tax + generatedReceipt.getTotal() + gratuity;

        // Add price to Labels
        tableTotal.setText(new DecimalFormat("#0.00").format(tableReceipt.getTotal()));
        receiptTotal.setText(new DecimalFormat("#0.00").format(total));
        billTableView.refresh();
        receiptView.refresh();

        tax = 0;
        gratuity = 0;
        total = 0;
    }

    void serverCon(ServerController parent) {
        this.parent = parent;
    }
}
