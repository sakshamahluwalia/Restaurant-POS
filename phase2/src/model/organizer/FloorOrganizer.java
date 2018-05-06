package model.organizer;

import model.Floor;
import model.Serializer;
import java.util.ArrayList;

/**
 * A FloorOrganizer manages the adding and removing of Floor objects.
 *
 * @author Eric Yuan, Saksham Ahluwalia, Patrick Calleja
 * @version %I%
 */
public class FloorOrganizer {
    /**
     * The ArrayList contains a list of model.Floor objects to be managed
     */
    private ArrayList<Floor> floors;

    /**
     * Serializer object used to serialize the floor list.
     */
    private Serializer serializer = new Serializer();

    /**
     * The only instance of model.organizer.FloorOrganizer
     */
    private static final FloorOrganizer floorOrganizer = new FloorOrganizer();

    /**
     * Constructs a model.organizer.FloorOrganizer.
     */
    private FloorOrganizer() {
        floors = serializer.deserialize("floors.ser");
    }

    /**
     * Returns the only instance of model.organizer.FloorOrganizer.
     *
     * @return model.organizer.FloorOrganizer
     */
    public static FloorOrganizer getInstance() {
        return floorOrganizer;
    }

    /**
     * Adds a model.Floor to this model.organizer.FloorOrganizer.
     *
     * The method checks to see if an existing model.Floor is already in floors.
     * If floor is not in floors then the model.Floor object is added to floors.
     * @param floor a model.Floor that is to be added to model.organizer.FloorOrganizer
     */
    public void addFloor(Floor floor) {
        if (!floors.contains(floor)) {
            floors.add(floor);
        }
    }

    /**
     * Removes a model.Floor from this model.organizer.FloorOrganizer.
     *
     * The method checks to see if an existing model.Floor is in floors.
     * If floor is in floors then the model.Floor object is removed from floors.
     * @param floor a model.Floor that is to be added to model.organizer.FloorOrganizer
     */
    public void removeFloor(Floor floor) {
        if (floors.contains(floor)) {
            floors.remove(floor);
        }
    }

    public void updateFloors() {
        serializer.serialize(floors, "floors.ser");
    }

    /**
     * Returns an ArrayList of model.Floor objects that are on this model.Floor.
     *
     * @return the ArrayList containing all model.Floor objects on this floor
     */
    public ArrayList<Floor> getFloors() {
        return floors;
    }
}
