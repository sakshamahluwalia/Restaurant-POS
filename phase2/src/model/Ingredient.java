package model;

import java.io.Serializable;

/**
 * Represents an Ingredient.
 *
 * @author Saksham Ahluwalia
 * @author Patrick Calleja
 * @author Eric Yuan
 *
 * @version %I%
 */
public class Ingredient implements Serializable {

    /**
     * The name of this Ingredient.
     */
    private String name;

    /**
     * The cost of this Ingredient.
     */
    private double cost;

    /**
     * The amount an Ingredient is sold for.
     */
    private double addOnPrice;

    /**
     * The number of of this Ingredient object.
     */
    private int quantity;

    /**
     * The constructor for this class.
     * @param name String
     * @param cost double
     * @param addOnPrice double
     */
    public Ingredient(String name, double cost, double addOnPrice) {
        this.name = name;
        this.cost = cost;
        this.addOnPrice = addOnPrice;
    }
    /**
     * Getter for name.
     * @return String.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for cost.
     * @return double.
     */
    public double getCost() {
        return cost;
    }

    /**
     * Setter for cost.
     * @param cost double
     */
    public void setCost(double cost) {
        this.cost = cost;
    }

    /**
     * Getter for addOnPrice.
     * @return double
     */
    public double getAddOnPrice() {
        return addOnPrice;
    }

    /**
     * Setter for addOnPrice
     * @param addOnPrice double
     */
    public void setAddOnPrice(double addOnPrice) {
        this.addOnPrice = addOnPrice;
    }

    /**
     * Updates to the most current number of itself
     *
     * @param quantity the number of itself that's available
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Returns the number of itself is available.
     *
     * @return the number of itself that's available
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * String representation of an model.Ingredient object.
     * @return String
     */
    @Override
    public String toString() {
        return name;
    }

    /**
     * Used to differentiate between model.Ingredient objects.
     * @param obj Object
     * @return boolean
     */
    @Override
    public boolean equals(Object obj) {
        return obj instanceof Ingredient && ((Ingredient) obj).name.trim().equals(this.name.trim())
                && ((Ingredient) obj).cost == this.cost;
    }
}
