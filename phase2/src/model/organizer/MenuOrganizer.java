package model.organizer;

import model.Menu;
import model.Serializer;

import java.util.ArrayList;

/**
 * model.organizer.MenuOrganizer class manages and contains all the model.Menu objects
 */

public class MenuOrganizer {

    /**
     * The ArrayList containing all available Menu objects
     */
    private ArrayList<Menu> menus;

    /**
     * Serializer object used to serialize the menu list.
     */
    private Serializer serializer = new Serializer();

    private static MenuOrganizer menuOrganizer = new MenuOrganizer();

    private MenuOrganizer() {
        menus = serializer.deserialize("menu.ser");
    }

    /**
     * Returns an instance of model.organizer.MenuOrganizer.
     *
     * @return model.organizer.MenuOrganizer
     */
    public static MenuOrganizer getInstance() {
        return menuOrganizer;
    }

    /**
     * Stores the menu.
     *
     * @param menu The model.Menu
     */
    public void addMenu(Menu menu) {
        if (!menus.contains(menu)) {
            menus.add(menu);
        }
    }

    /**
     * Returns the menu given by the name.
     *
     * @param name the String
     * @return model.Menu
     */
    public Menu getMenu(String name) {
        for (Menu menu: menus) {
            if (menu.getMenuName().equals(name)) {
                return menu;
            }
        }
        return null;
    }

    /**
     * Removes the menu from the list
     *
     * @param menu The model.Menu to be removed
     */
    public void removeMenu(Menu menu) {
        menus.remove(menu);
    }

    /**
     * This method is responsible for updating the data in the employee list
     * and s
     */
    public void updateMenus() {
        serializer.serialize(menus, "menu.ser");
    }

    /**
     * Returns an ArrayList of all Menu objects.
     *
     * @return all Menu objects
     */
    public ArrayList<Menu> getMenus() {
        return menus;
    }
}
