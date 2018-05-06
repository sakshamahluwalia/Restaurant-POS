package model.employee;

import model.*;
import model.organizer.FloorOrganizer;
import model.organizer.InventoryOrganizer;
import model.organizer.MenuOrganizer;

/**
 * A model.employee.Manager objects has access to model.organizer.InventoryOrganizer, model.organizer.FloorOrganizer and model.organizer.MenuOrganizer.
 *
 * @author Eric Yuan, Patrick Calleja, Saksham Ahluwalia
 * @version  %I%
 */
public class Manager extends Server {
    /**
     * Constructs a Manager.
     *
     * @param firstName the first name of Manager
     * @param lastName the last name of Manager
     */
    public Manager(String firstName, String lastName, String password) {
        super(firstName, lastName, password);
    }
    /**
     * The model.organizer.InventoryOrganizer all model.employee.Manager objects have access to
     */
    private static InventoryOrganizer inventoryOrganizer;

    /**
     * The model.organizer.FloorOrganizer all model.employee.Manager objects have access to
     */
    private static FloorOrganizer floorOrganizer;

    /**
     * The model.organizer.MenuOrganizer all model.employee.Manager object shave access to
     */
    private static MenuOrganizer menuOrganizer;

    /**
     * Sets the model.organizer.InventoryOrganizer that's accessible by all model.employee.Manager objects.
     *
     * @param inventoryOrganizer inventoryOrganizer
     */
    public static void setInventoryOrganizer(InventoryOrganizer inventoryOrganizer) {
        Manager.inventoryOrganizer = inventoryOrganizer;
    }

    /**
     * Sets the model.organizer.FloorOrganizer that's accessible by all model.employee.Manager objects.
     *
     * @param floorOrganizer model.organizer.FloorOrganizer
     */
    public static void setFloorOrganizer(FloorOrganizer floorOrganizer) {
        Manager.floorOrganizer = floorOrganizer;
    }

    /**
     * Sets the model.organizer.MenuOrganizer that's accessible by all model.employee.Manager objects.
     *
     * @param menuOrganizer model.organizer.MenuOrganizer
     */
    public static void setMenuOrganizer(MenuOrganizer menuOrganizer) {
        Manager.menuOrganizer = menuOrganizer;
    }

    /**
     * Returns the model.organizer.InventoryOrganizer that all model.employee.Manager objects have access to.
     *
     * @return model.organizer.InventoryOrganizer
     */
    public InventoryOrganizer getInventoryOrganizer() {
        return inventoryOrganizer;
    }

    /**
     * Returns the model.organizer.FloorOrganizer that all model.employee.Manager objects have access to.
     *
     * @return model.organizer.FloorOrganizer
     */
    public FloorOrganizer getFloorOrganizer() {
        return floorOrganizer;
    }

    /**
     * Returns the model.organizer.MenuOrganizer that all model.employee.Manager objects have access to.
     *
     * @return model.organizer.MenuOrganizer
     */
    public MenuOrganizer getMenuOrganizer() {
        return menuOrganizer;
    }

    /**
     * Adds a new model.MenuItem to a model.Menu.
     *
     * @param menu model.Menu that model.MenuItem is being added to
     * @param menuItem model.MenuItem that is added to model.Menu
     * @param category category of the model.Menu the model.MenuItem is to be added to
     */
    public void addMenuItem(Menu menu, MenuItem menuItem, String category) {
        menu.addMenuItem(menuItem, category);
    }

//    /**
//     * Updates the model.organizer.InventoryOrganizer with more of an model.Ingredient.
//     *
//     * @param ingredient model.Ingredient to be updated in model.organizer.InventoryOrganizer
//     * @param quantity number of model.Ingredient to be updated in model.organizer.InventoryOrganizer
//     */
//    public void addInventory(Ingredient ingredient, int quantity) {
//        HashMap<Ingredient, Integer> inventoryToBeAdded = new HashMap<>();
//        inventoryToBeAdded.put(ingredient, quantity);
//        inventoryOrganizer.addIngredients(inventoryToBeAdded);
//    }

//    public void createMenuItem(String name, double price, HashMap<model.Ingredient, Double> ingredients) {
//
//    }

}
