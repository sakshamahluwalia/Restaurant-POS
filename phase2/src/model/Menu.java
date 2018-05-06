package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * This class represents a Menu object and contains a list MenuItems.
 */
public class Menu implements Serializable {

    /**
     * The name of this menu.
     */
    private String menuName;

    /**
     * A HashMap containing all menuItems under respective categories.
     */
    private HashMap<String, ArrayList<MenuItem>> menu;

    /**
     * A serializer object used to serialize the menu.
     */
    private Serializer serializer = new Serializer();

    /**
     * A list of ingredients that can be used in additions for a MenuItem
     */
    private ArrayList<Ingredient> addOn;

    /**
     * Instantiates a Menu.
     *
     * @param name the string for the name of this menu
     */
    public Menu(String name) {
        this.menuName = name;
        menu = serializer.deserializeHashMap(menuName + ".ser");
        addOn = new ArrayList<>();
    }

    /**
     * Stores ingredients used for additions that can be used for a MenuItem
     *
     * @param ingredient the Ingredient
     */
    public void addModifications(Ingredient ingredient) {
        addOn.add(ingredient);
    }

    /**
     * Returns the categories within the Menu.
     *
     * @return ArrayList<String>
     */
    public ArrayList<String> getCategories() {
       return new ArrayList<String>(menu.keySet());
    }

    /**
     * Returns the category of a given MenuItem.
     *
     * @param menuItem menuItem to find the category of
     * @return the category of the MenuItem
     */
    public String getCategory(MenuItem menuItem) {
        for (String string: menu.keySet()) {
            if (menu.get(string).contains(menuItem)) {
                return string;
            }
        }
        return null;
    }

    public ArrayList<MenuItem> getMenuItems(String category) {
        return menu.get(category);
    }

    /**
     * Returns a Hashmap of categories to MenuItems.
     *
     * @return HashMap<String, ArrayList<model.MenuItem>>
     */
    public HashMap<String, ArrayList<MenuItem>> getMenu() {
        return menu;
    }

    /**
     * Returns a menuItem.
     * @param menuItem the string name of the menuItem object
     * @return model.MenuItem
     */
    public MenuItem getMenuItem(String menuItem) {
        for (ArrayList<MenuItem> value: menu.values()) {
            for (MenuItem dish: value) {
                if (dish.getName().equals(menuItem)) {
                    return dish;
                }
            }
        }
        return null;
    }

    /**
     * Adds a MenuItem in a category to this model.Menu.
     *
     * @param menuItem the model.MenuItem to be stored in a category
     * @param category the string category where model.MenuItem will be stored
     */
    public void addMenuItem(MenuItem menuItem, String category) {
        ArrayList<MenuItem> menuItemsList = menu.get(category);
        // category has been made and menuItem is not contained
        if (!(menuItemsList.contains(menuItem))) {
            menuItemsList.add(menuItem);
        }
        updateMenu();
    }

    /**
     * Remove a model.MenuItem.
     *
     * @param menuItem the model.MenuItem that will be removed in this model.Menu
     * @param category the string category where model.MenuItem is stored
     */
    public void removeMenuItem(MenuItem menuItem, String category) {
        menu.remove(category, menuItem);
        updateMenu();
    }

    /**
     * Adds a new category to the menu.
     *
     * @param category the string name of a category that is to be added to the menu.
     */
    public void addCategory(String category) {
        if (!menu.keySet().contains(category)) {
            menu.put(category, new ArrayList<>());
        }
    }

    /**
     * Remove a category
     *
     * @param category the string category
     */
    public void removeCategory(String category) {
        menu.remove(category);
        updateMenu();
    }

    /**
     * Returns the name of this model.Menu
     *
     * @return String
     */
    public String getMenuName() {
        return this.menuName;
    }

    /**
     * This method is used to serialize the menu ArrayList.
     */
    private void updateMenu() {
        serializer.serialize(menu, menuName+".ser");
    }

    /**
     * Returns a String representation of Menu.
     *
     * @return a String version of Menu
     */
    @Override
    public String toString() {
        return menuName;
    }
}
