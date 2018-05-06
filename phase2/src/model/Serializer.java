package model;

import java.io.*;
import java.util.*;

/**
 * This class is responsible for serializing an ArrayList Object.
 *
 * @author Martin Baroody
 * @author Jai Aggarwal
 * @author Aleksa Zatezalo
 * @author Saksham Ahluwalia
 */
public class Serializer<T> implements Serializable {

    /**
     * The path for the config directory to contain log.txt and saved directory.
     */
    private final String configDirectoryPath = System.getProperty("user.dir") + "/data/";

    /**
     * Write the ArrayList Object to a file.
     * @param list ArrayList to serialize
     * @param fileName File to write to.
     */
    @SuppressWarnings("Duplicates")
    public void serialize(ArrayList<T> list, String fileName) {
        //note that filename must end in .ser
        try {
            File file = new File(configDirectoryPath+fileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fout = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(list);
            oos.close();
            fout.close();
        }
        catch(IOException e) {
            Logger.writeToFile(e.getStackTrace());
        }
        /*This code was taken from
        https://stackoverflow.com/questions/17293991/how-to-write-and-read-java-serialized-objects-into-a-file
         */
    }

    /**
     * Write the ArrayList Object to a file.
     * @param map ArrayList to serialize
     * @param fileName File to write to.
     */
    public void serialize(HashMap map, String fileName) {
        //note that filename must end in .ser
        try {
            File file = new File(configDirectoryPath+fileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fout = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(map);
            oos.close();
            fout.close();
        }
        catch(IOException e) {
            Logger.writeToFile(e.getStackTrace());
        }
        /*This code was taken from
        https://stackoverflow.com/questions/17293991/how-to-write-and-read-java-serialized-objects-into-a-file
         */
    }

    /**
     * Write the ArrayList Object to a file.
     * @param number ArrayList to serialize
     * @param fileName File to write to.
     */
    @SuppressWarnings("Duplicates")
    public void serialize(Integer number, String fileName) {
        //note that filename must end in .ser
        try {
            File file = new File(configDirectoryPath+fileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fout = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fout);
            oos.writeObject(number);
            oos.close();
            fout.close();
        }
        catch(IOException e) {
            Logger.writeToFile(e.getStackTrace());
        }
        /*This code was taken from
        https://stackoverflow.com/questions/17293991/how-to-write-and-read-java-serialized-objects-into-a-file
         */
    }

    /**
     * Retrieve the object from a serialised file
     * @param fileName String
     * @return ArrayList<String>
     */
    public Integer deserializeInteger(String fileName){
        Integer integer = null;
        try {
            File file = new File(configDirectoryPath+fileName);
            if(file.isFile()) {
                FileInputStream fileIn = new FileInputStream(file.getAbsolutePath());
                ObjectInputStream in = new ObjectInputStream(fileIn);
                integer = (Integer) in.readObject();
                in.close();
                fileIn.close();

            }
        }
        catch (IOException e) {
            Logger.writeToFile(e.getStackTrace());
        }
        catch (ClassNotFoundException e) {
            Logger.writeToFile(e.getStackTrace());
        }
        return integer;

    }

    /**
     * Retrieve the object from a serialised file
     * @param fileName String
     * @return ArrayList<String>
     */
    public HashMap deserializeHashMap(String fileName){
        HashMap list = new HashMap<>();
        try {
            File file = new File(configDirectoryPath+fileName);
            if(file.isFile()) {
                FileInputStream fileIn = new FileInputStream(file.getAbsolutePath());
                ObjectInputStream in = new ObjectInputStream(fileIn);
                list = (HashMap) in.readObject();
                in.close();
                fileIn.close();

            }
        }
        catch (IOException e) {
            Logger.writeToFile(e.getStackTrace());
        }
        catch (ClassNotFoundException e) {
            Logger.writeToFile(e.getStackTrace());
        }
        return list;

    }

    /**
     * Retrieve the object from a serialised file
     * @param fileName String
     * @return ArrayList<String>
     */
    public ArrayList<T> deserialize(String fileName){
        ArrayList<T> list = new ArrayList<>();
        try {
            File file = new File(configDirectoryPath+fileName);
            if(file.isFile()) {
                FileInputStream fileIn = new FileInputStream(file.getAbsolutePath());
                ObjectInputStream in = new ObjectInputStream(fileIn);
                list = (ArrayList<T>) in.readObject();
                in.close();
                fileIn.close();

            }
        }
        catch (IOException e) {
            Logger.writeToFile(e.getStackTrace());
        }
        catch (ClassNotFoundException e) {
            Logger.writeToFile(e.getStackTrace());
        }
        return list;

    }
}