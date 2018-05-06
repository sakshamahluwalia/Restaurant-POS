package model.employee;

import model.Ingredient;
import model.organizer.InventoryOrganizer;

/**
 * A model.employee.ReceiverController is an model.employee.Employee that can update the inventory of the restaurant.
 *
 * @author Eric Yuan, Patrick Calleja, Saksham Ahluwalia
 * @version  %I%
 */
public class Receiver extends Employee {
    /**
     * Constructs a ReceiverController.
     *
     * @param firstName the first name of ReceiverController
     * @param lastName the last name of ReceiverController
     */
    public Receiver(String firstName, String lastName, String password) {
        super(firstName, lastName, password);
    }

    /**
     * The model.organizer.InventoryOrganizer that model.employee.ReceiverController uses to update inventory.
     */
    private static InventoryOrganizer inventoryOrganizer = InventoryOrganizer.getInstance();

    /**
     * Sets the model.organizer.InventoryOrganizer for all model.employee.ReceiverController objects.
     *
     * @param inventoryOrganizer the model.organizer.InventoryOrganizer that is to be set for all Receivers
     */
    public static void setInventoryOrganizer(InventoryOrganizer inventoryOrganizer) {
        Receiver.inventoryOrganizer = inventoryOrganizer;
    }

    /**
     * Returns the model.organizer.InventoryOrganizer that is used by all model.employee.ReceiverController
     *
     * @return model.organizer.InventoryOrganizer
     */
    public InventoryOrganizer getInventoryOrganizer() {
        return inventoryOrganizer;
    }

    /**
     * Updates the model.organizer.InventoryOrganizer with more of an model.Ingredient.
     *
     * @param ingredient model.Ingredient to be updated in model.organizer.InventoryOrganizer
     * @param quantity number of model.Ingredient to be updated in model.organizer.InventoryOrganizer
     */
    public void addInventory(Ingredient ingredient, int quantity) {
        inventoryOrganizer.addIngredients(ingredient, quantity);
    }
}
