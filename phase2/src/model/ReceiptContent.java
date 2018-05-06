package model;

/**
 * The Receipt Content is the Model for the TableView
 */
public class ReceiptContent {

    private int customerNum;
    private String menuItem;
    private int quantity;
    private double price;
    private int orderId;

    /**
     * Constructor for Receipt Content
     *
     * @param customerNum customer number
     * @param menuItem menut item
     * @param quantity quantity
     * @param price the price
     */
    public ReceiptContent(int customerNum, String menuItem, int quantity, double price) {
        this.customerNum = customerNum;
        this.menuItem = menuItem;
        this.quantity = quantity;
        this.price = price;
    }

    /**
     * Sets the order ID.
     *
     * @param id the new order id
     */
    public void setOrderId(int id) {
        this.orderId = id;
    }

    public int getOrderId() {
        return this.orderId;
    }

    /**
     * Returns the customer number.
     *
     * @return the customer number
     */
    public int getCustomerNum() {
        return customerNum;
    }

    /**
     * Sets the customer number.
     *
     * @param customerNum the customer number
     */
    public void setCustomerNum(int customerNum) {
        this.customerNum = customerNum;
    }

    /**
     * Returns the menu item.
     *
     * @return the menu item
     */
    public String getMenuItem() {
        return menuItem;
    }

    /**
     * Set the menu item for this content.
     *
     * @param menuItem the menu item
     */
    public void setMenuItem(String menuItem) {
        this.menuItem = menuItem;
    }

    /**
     * Returns the quantity of this content.
     *
     * @return the quantity
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * The quantity of this content.
     *
     * @param quantity the quantity
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Returns the price of this content.
     *
     * @return the price
     */
    public double getPrice() {
        return price;
    }

    /**
     * The price of this content.
     *
     * @param price the price
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Two ReceiptContent is the same if it contains the same customer number and order.
     *
     * @param o the Object to compare
     * @return True if equal
     */
    @Override
    public boolean equals(Object o) {
        return o instanceof ReceiptContent && ((ReceiptContent) o).getMenuItem().equals(this.getMenuItem()) &&
                ((ReceiptContent) o).customerNum == this.customerNum;
    }
}