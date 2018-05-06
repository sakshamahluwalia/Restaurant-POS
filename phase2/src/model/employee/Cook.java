package model.employee;

import model.Ingredient;
import model.organizer.InventoryOrganizer;
import model.Order;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * A model.employee.CookController utilises the model.organizer.OrderOrganizer
 * to manage orders to be made and orders completed.
 *
 * @author Eric Yuan, Patrick Calleja, Saksham Ahluwalia
 * @version  %I%
 */
public class Cook extends Employee {
    /**
     * Constructs a CookController.
     *
     * @param firstName the first name of CookController
     * @param lastName the last name of CookController
     */
    public Cook(String firstName, String lastName, String password) {
        super(firstName, lastName, password);
    }

    /**
     * Uses the model.organizer.OrderOrganizer and removes the model.Order adds it
     * to completedOrders in model.organizer.OrderOrganizer.
     * @param order Order
     */
    public boolean completeOrder(Order order) {
        HashMap<Ingredient, Integer> combined = new HashMap<>(order.getMenuItem().getIngredients());
        ArrayList<Ingredient> ingredients = new ArrayList<>();
        for (Ingredient ingredient: combined.keySet()) {
            ingredients.add(ingredient);
        }
        for (Ingredient ingredient: order.getAddOns().keySet()) {
            ingredients.add(ingredient);
        }
        boolean sufficientAmount = InventoryOrganizer.getInstance().checkIngredients(ingredients);
        // Checks if there's sufficient amount of ingredients to make the desired Menu Item.
        if (sufficientAmount) {
            getOrderOrganizer().completeOrder(order);
            order.setMade(true);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Sets the seen variable for Order to true.
     * @param order Order
     */
    public void seenOrder(Order order) {
        order.setSeen(true);
    }
}
