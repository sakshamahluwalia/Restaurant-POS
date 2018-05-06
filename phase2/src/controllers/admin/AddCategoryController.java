package controllers.admin;

import model.Menu;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

/**
 * AddCategoryController handles the input of categories from add_category.fxml and updates the Menu.
 *
 * @author Eric Yuan
 * @author Saksham Ahluwalia
 * @author Patrick Calleja
 * @version %I%
 */
public class AddCategoryController {
    /**
     * The field where the category name for menu is inputted
     */
    @FXML
    private TextField categoryName;

    /**
     * The current Menu object for this controller
     */
    private Menu currentMenu;

    /**
     * Handles the action when the 'Add Category' button is clicked.
     */
    @FXML
    public void addCategory() {
        String category = categoryName.getText();
        currentMenu.addCategory(category);
        categoryName.clear();
    }

    /**
     * Sets the current Menu object.
     *
     * @param menu the menu object that currentMenu will be set to.
     */
    void setCurrentMenu(Menu menu) {
        currentMenu = menu;
    }
}
