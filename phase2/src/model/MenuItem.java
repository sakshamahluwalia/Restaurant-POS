package model;

import java.io.Serializable;
import java.util.HashMap;

/**
 * This class represents food or beverage offered to the customers within a specified model.Menu.
 */

public class MenuItem implements Serializable {

    /**
     * The price of this MenuItem.
     */
    private double price;

    /**
     * The cost to make this MenuItem.
     */
    private double expenses;

    /**
     * The name of this MenuItem.
     */
    private String name;

    /**
     * Ingredients used to make this MenuItem.
     */
    private HashMap<Ingredient, Integer> ingredients;

    /**
     * Instantiates a new MenuItem
     *
     * @param name the string this MenuItem is named
     * @param price the price of MenuItem for the customers
     */
    public MenuItem(String name, double price) {
        this.name = name;
        this.price = price;
        ingredients = new HashMap<>();
    }

    /**
     * Sets the current HashMap of Ingredients and quantity to a new HashMap of Ingredients and quantity.
     *
     * @param ingredientsMap the HashMap to replace ingredients
     */
    public void setIngredients(HashMap<Ingredient, Integer> ingredientsMap) {
        ingredients = ingredientsMap;
    }

    /**
     * Add ingredients used to make this MenuItem
     *
     * @param ingredient An model.Ingredient Object
     * @param quantity quantity of an model.Ingredient
     */
    public void addIngredients(Ingredient ingredient, int quantity) {
        ingredients.put(ingredient, quantity);
        expenses += ingredient.getCost() * quantity;
    }

    /**
     * Return of the price of this MenuItem.
     *
     * @return double
     */
    public double getPrice() {
        return price;
    }

    /**
     * Sets the price of this MenuItem.
     *
     * @param price double
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Return the expenses used to create this dish.
     *
     * @return double
     */
    public double getExpenses() {
        return expenses;
    }

    /**
     * Sets the name of this MenuItem
     *
     * @param name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Return the name of this MenuItem
     *
     * @return String
     */
    public String getName() {
        return this.name;
    }

    /**
     * Return the HashMap of Ingredients and the Integer amount
     *
     * @return HashMap<model.Ingredient, Integer>
     */
    public HashMap<Ingredient, Integer> getIngredients() {
        return this.ingredients;
    }

    /**
     * Return a String representation of this object.
     * @return String
     */
    @Override
    public String toString() {
        return this.name;
    }

    /**
     * Checks for equality.
     * @param o Object
     * @return boolean
     */
    @Override
    public boolean equals(Object o) {
        return ((o instanceof  MenuItem) && (this.name.equals(((MenuItem) o).getName())));
    }
}
