package model;

/**
 * Alert handles creating popup messages.
 */
public class Alert {

    /**
     * Displays an error if all the fields have not been completed.
     *
     * @param text the text that is displayed in the error Alert.
     */
    public static void showErrorAlert(String text) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    /**
     * Displays information in the GUI.
     *
     * @param text the text that is displayed in the Information Alert.
     */
    public static void showInformationAlert(String text) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

}
