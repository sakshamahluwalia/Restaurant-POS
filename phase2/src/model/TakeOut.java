package model;

/**
 * Represents a table in a restaurant.
 *
 * @author Saksham Ahluwalia
 * @author Patrick Calleja
 * @author Eric Yuan
 *
 * @version %I%
 */
public class TakeOut extends OrderType {

    /**
     * The number of total TakeOut orders
     */
    private static int numberOfTakeOut = 1;


    /**
     * The serializer for this class.
     */
    private Serializer serializer = new Serializer();

    /**
     * The constructor for this class.
     */
    public TakeOut() {
        super(numberOfTakeOut);
        Integer integer = serializer.deserializeInteger("numberOfTakeOuts.ser");
        if (integer != null) {
            numberOfTakeOut = integer;
            setId(numberOfTakeOut++);
        }
        setId(numberOfTakeOut);
        updateNumberOfCustomer();
    }

    /**
     * This method is used to serialize the numberOfTakeout int.
     */
    public static void updateNumberOfCustomer() {
        Serializer serializer = new Serializer();
        serializer.serialize(numberOfTakeOut, "numberOfTakeOuts.ser");
    }

    /**
     * Returns a String representation of TakeOut.
     *
     * @return the String version of TakeOut
     */
    @Override
    public String toString() {
        return "TakeOut: " + getId();
    }
}
