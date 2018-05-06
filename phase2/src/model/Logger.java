package model;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;

/**
 * This class is responsible for logging changes whenever
 * a file name is changed.
 */
public class Logger {

    /**
     * The path for the config directory to contain log.txt and saved directory.
     */
    private static final String configDirectoryPath = System.getProperty("user.dir") + "/";


    /**
     * This function writes the changes to a file.
     * @param change String The input to write to the file
     */
    public static void writeToFile(String change) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(configDirectoryPath+"requests.txt", false));
            writer.write(change + System.lineSeparator());
            writer.close();
        } catch (IOException error) {
            writeToFile(error.getMessage());
        }
    }

    /**
     * This function logs the error to ErrorLog.txt
     * @param e StackTraceElement
     */
    public static void writeToFile(StackTraceElement[] e) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(configDirectoryPath+"error.txt", true));
            for (Object obj: e) {
                writer.write(obj + System.lineSeparator());
            }
            writer.write("<--End of Message-->" + System.lineSeparator());
            writer.close();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    /**
     * Log strings to log.txt. log.txt is used to record of all food that was ordered, prepared,
     * delivered to the customer, returned to the kitchen,and any change to the inventory of
     * ingredients
     *
     * @param toLog the string that is to be logged
     */
    public static void LOG(String toLog) {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(configDirectoryPath+"log.txt", true));
            writer.write(LocalDateTime.now() +": " + toLog + System.lineSeparator());
            writer.close();
        } catch (IOException error) {
            writeToFile(error.getMessage());
        }
    }
}
