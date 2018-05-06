package model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * The Receipt is the sum of ReceiptContents.
 */
public class Receipt {
    /**
     * A ObservableList that contains ReceiptContents.
     */
    private ObservableList<ReceiptContent> receipt;

    /**
     * The double of the sum of all prices from the orders.
     */
    private double total;

    /**
     * The constructor for Receipt.
     */
    public Receipt() {
        receipt = FXCollections.observableArrayList();
    }

    /**
     * Takes a Bill and converts it into a ObservableList of ReceiptContents.
     *
     * @param bill The Bill to conver
     * @return the List containing ReceiptContetns
     */
     public ObservableList<ReceiptContent> generateReceipt(Bill bill) {
        for (int customer: bill.getOrders().keySet()) {
            for(Order order: bill.getOrders().get(customer)) {
                add(order, customer);
            }
        }
        return receipt;
     }

    /**
     * Helper function to add Orders and customer numbers in a receipt.
     *
     * @param order Order for a customer
     * @param customerNum the customer number
     */
    private void add(Order order, int customerNum) {
        ReceiptContent receiptContent;
        // Checks for addOns
        if (order.getAddOns().size() != 0) {
            for (Ingredient ingredient: order.getAddOns().keySet()) {
                receiptContent = new ReceiptContent(customerNum, "ADD ON: " + ingredient.getName(), 1,
                        ingredient.getAddOnPrice());
                receipt.add(receiptContent);
                receiptContent.setOrderId(-1);
            }
        }
        receiptContent = new ReceiptContent(customerNum, order.getMenuItem().getName(),
                    1, order.getMenuItem().getPrice());
        receiptContent.setOrderId(order.getId());

        if (receipt.contains(receiptContent)) {
            ReceiptContent sameContent = receipt.get(receipt.indexOf(receiptContent));
            sameContent.setQuantity(sameContent.getQuantity() + 1);
            sameContent.setPrice(sameContent.getPrice() + receiptContent.getPrice());
        } else {
            receipt.add(receiptContent);
        }

    }

    /**
     * The total price of this Receipt.
     *
     * @return the price
     */
    public double getTotal() {
        total = 0;
        for (ReceiptContent e: receipt) {
            total += e.getPrice();
        }
        return total;
    }

    /**
     * Removes all ReceiptContent from this receipt.
     *
     * @param remove The ObservableList containing ReceiptContent to remove
     */
    public void removeAll(ObservableList<ReceiptContent> remove) {
        receipt.removeAll(remove);
    }

    /**
     * Concatenates the receipt with another List of ReceiptContents.
     *
     * @param otherObservable The ObservableList to concatenate this with
     */
    public void concat(ObservableList<ReceiptContent> otherObservable) {
        receipt = FXCollections.concat(getObservable(), otherObservable);
    }

    /**
     * Returns the ObservableList of ReceiptContent.
     *
     * @return The list of ReceiptContent
     */
    public ObservableList<ReceiptContent> getObservable(){
        return this.receipt;
    }
}