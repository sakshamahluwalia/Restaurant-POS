package model.organizer;

import model.Logger;
import model.Ingredient;
import model.Serializer;

import java.util.HashMap;
import java.util.ArrayList;

/**
 * InventoryOrganizer manages the Ingredient quantity of the restaurant.
 *
 * @author Eric Yuan, Saksham Ahluwalia, Patrick Calleja
 * @version  %I%
 */
public class InventoryOrganizer {

    /**
     * A custom marker used to tell if an ingredient is running low.
     */
    private int threshold = 5;

    private Serializer<Ingredient> serializer = new Serializer<>();

    /**
     * The ArrayList of Ingredients that the InventoryOrganizer manages
     */
    private ArrayList<Ingredient> ingredients;

    /**
     * This list contains ingredients that are running low.
     */
    private ArrayList<Ingredient> runningLow;

    /**
     * Exists to remove Instantiation.
     */
    private static final InventoryOrganizer inventoryOrganizer = new InventoryOrganizer();

    /**
     * Constructs an model.organizer.InventoryOrganizer.
     */
    private InventoryOrganizer() {
        ingredients = serializer.deserialize("ingredients.ser");
        runningLow = serializer.deserialize("runninglow.ser");
    }

    public void setIngredients(Ingredient ingredient, int quantity) {
        int num = getQuantity(ingredient);
        int difference;
        if (quantity >= num) {
            difference = quantity - num;
            addIngredients(ingredient, difference);
        } else {
            difference = num - quantity;
            HashMap<Ingredient, Integer> map = new HashMap<>();
            map.put(ingredient, difference);
            removeIngredients(map);
        }
    }

    /**
     * Updates the inventory with a HashMap of model.Ingredient objects and their quantity.
     * If an model.Ingredient is currently not in inventory, then it is added,
     * otherwise it updates the quantity of the model.Ingredient.
     *
     * @param ingredient Ingredient
     * @param quantity int
     */
    public void addIngredients(Ingredient ingredient, int quantity) {
        // check if ingredient is a valid ingredient.
        if (ingredients.contains(ingredient)) {
            // retrieve the ingredient and update its quantity.
            ingredient.setQuantity(getQuantity(ingredient) + quantity);
            // check the quantity of the ingredient.
            if (getQuantity(ingredient) > threshold) {
                runningLow.remove(ingredient);
                processRequests();
            }
        } else {
            // update the quantity of the ingredient
            ingredient.setQuantity(quantity);
            // add it to the ingredients list
            ingredients.add(ingredient);
            if (getQuantity(ingredient) < threshold) {
                if (!runningLow.contains(ingredient)) {
                    runningLow.add(ingredient);
                    processRequests();
                }
            }
        }
    }

    /**
     * Updates the inventory with a HashMap of model.Ingredient objects and their quantity.
     * If an model.Ingredient is  in inventory, then it is removed, otherwise it does nothing.
     *
     * @param ingredientsToRemove the model.Ingredient objects to be removed in inventory
     */
    public void removeIngredients(HashMap<Ingredient, Integer> ingredientsToRemove) {
        for (Ingredient ingredient: ingredientsToRemove.keySet()) {
            // for each ingredient in the hashmap provided
            if (ingredients.contains(ingredient)) {
                // if ingredients are present update their quantity
                getIngredient(ingredient).setQuantity(getQuantity(ingredient) - ingredientsToRemove.get(ingredient));


                // Logs the new ingredient
                Logger.LOG("INGREDIENT QUANTITY DECREMENT " + ingredient.getName() +". QUANTITY: " +
                        inventoryOrganizer.getQuantity(ingredient) );

                // if their quantity is below the threshold add them to runningLow.
                if (getQuantity(ingredient) < threshold) {
                    if (!runningLow.contains(ingredient)) {
                        runningLow.add(ingredient);
                        processRequests();
                    }
                }
            }
        }
    }

    /**
     * Return true if we have enough inventory to fulfill the order, false otherwise.
     *
     * Checks the ingredients of an model.Order object to see if we have enough inventory to
     * fulfill the model.Order.
     * @param ingredientsToCheck the HashMap of ingredients to check inventory
     * @return true if we have inventory, false otherwise.
     */
    public boolean checkIngredients(ArrayList<Ingredient> ingredientsToCheck) {
        for (int i = 0; i < ingredientsToCheck.size(); i++) {
            int count = 1;
            for (int j = i+1; j < ingredientsToCheck.size(); j++) {
                if (ingredientsToCheck.get(i).equals(ingredientsToCheck.get(j))) {
                    count++;
                }
            }
            if (getQuantity(ingredientsToCheck.get(i)) < count) {
                return false;
            }
        }
        return true;
    }

    /**
     * Returns the quantity of a given model.Ingredient.
     *
     * @param ingredient the model.Ingredient that we want to get the quantity of
     * @return the number of model.Ingredient
     */
    public int getQuantity(Ingredient ingredient) {
        return getIngredient(ingredient).getQuantity();
    }

    /**
     * Returns the ArrayList of model.Ingredient objects in model.organizer.InventoryOrganizer.
     *
     * @return all the model.Ingredient objects in model.organizer.InventoryOrganizer
     */
    public ArrayList<Ingredient> getIngredients() {
//        ingredients = serializer.deserialize("ingredients.ser");
        return ingredients;
    }

    public Ingredient getIngredient(Ingredient ingredient) {
        return ingredients.get(ingredients.lastIndexOf(ingredient));
    }

    /**
     * Returns an instance of this class.
     * @return InventoryOrganizer
     */
    public static InventoryOrganizer getInstance() {
        return inventoryOrganizer;
    }

    /**
     * Generates and logs the list of ingredients that are less then the threshold
     */
    private void processRequests() {
        StringBuilder stringBuilder = new StringBuilder();
        if (runningLow.size() != 0) {
            stringBuilder.append("Our restaurant needs to order the following ingredients: " + "\n");
            for (Ingredient ingredient : runningLow) {
                stringBuilder.append(ingredient.getName() + " - \n");
            }
        }
        Logger.writeToFile(stringBuilder.toString());

    }

    /**
     * Returns the ArrayList of ingredients that are running low.
     *
     * @return ArrayList of ingredients
     */
    public ArrayList<Ingredient> getRunningLow() {
        return runningLow;
    }

    /**
     * Serializes the updated ingredients.
     */
    public void updateIngredients() {
        serializer.serialize(ingredients, "ingredients.ser");
        serializer.serialize(runningLow, "runninglow.ser");
    }
}
