package controllers.manager;

import java.io.*;
import java.net.URL;
import javafx.fxml.FXML;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

/**
 * ViewRequestsController handles actions and controls buttons for the GUI for view_requests.fxml
 *
 * @author Eric Yuan
 * @author Saksham Ahluwalia
 * @author Patrick Calleja
 * @version %I%
 */
public class ViewRequestsController implements Initializable {
    /**
     * The area to load the requests file for the manager to copy
     */
    @FXML
    private TextArea requestsField;

    /**
     * The path for the config directory to contain log.txt and saved directory.
     */
    private final String configDirectoryPath = System.getProperty("user.dir") + "/";

    /**
     * Initializes all necessary objects for proper functionality of the Controller.
     *
     * @param location URL
     * @param resources ResourceBundle
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        readRequests();
    }

    /**
     * Processes the requests.txt file and loads requestsField with the data from the text file.
     */
    private void readRequests() {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            File file = new File(configDirectoryPath+"requests.txt");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line + "\n");
            }
            requestsField.setText(stringBuilder.toString());
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}