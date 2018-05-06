package model.employee;

import model.Serializer;
import model.organizer.OrderOrganizer;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * An model.employee.Employee object represents an individual that works for the restaurant.
 */
public class Employee implements Serializable {

    private final String password;

    /**
     * The first name of an Employee
     */
    private final String firstName;

    /**
     * The last name of an Employee
     */
    private final String lastName;

    /**
     * The date this Employee was hired
     */
    private final Date date;

    /**
     * The current number of all model.employee.Employee objects
     */
    private static int employeeNumber;

    /**
     * The ID of this model.employee.Employee
     */
    private final int id;

    /**
     * The orderOrganizer that all Employees have access to.
     */
    private static OrderOrganizer orderOrganizer = OrderOrganizer.getInstance();

    /**
     * The serializer for this class.
     */
    private Serializer serializer = new Serializer();

    /**
     * Constructs an model.employee.Employee.
     */
    public Employee(String firstName, String lastName, String password) {
        date = new Date();
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        Integer integer = serializer.deserializeInteger("EmployeeIds.ser");
        if (integer != null) {
            employeeNumber = integer;
            this.id = ++employeeNumber;
        } else {
            this.id = 0;
        }
        updateEmployeeIds();
    }

    /**
     * Sets the model.organizer.OrderOrganizer for all Employees.
     *
     * @param orderOrganizer the model.organizer.OrderOrganizer that is to be set for all Employees
     */
    public static void setOrderOrganizer(OrderOrganizer orderOrganizer) {
        Employee.orderOrganizer = orderOrganizer;
    }

    /**
     * Returns the model.organizer.OrderOrganizer that belongs to all Employees.
     *
     * @return model.organizer.OrderOrganizer that all Employees use
     */
    public OrderOrganizer getOrderOrganizer() {
        return orderOrganizer;
    }

    /**
     * Returns the unique ID of this model.employee.Employee
     *
     * @return the ID of this model.employee.Employee
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the first name of an Employee.
     *
     * @return the first name of an Employee
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the last name of an Employee.
     *
     * @return the last name of an Employee
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the password of an Employee
     *
     * @return the password of an Employee
     */
    public String getPassword() {
        return password;
    }

    /**
     * Returns the Date the Employee was created(hired)
     *
     * @return Date
     */
    public Date getDate() {
        return date;
    }

    /**
     * This method is used to serialize the orders int.
     */
    public static void updateEmployeeIds() {
        Serializer serializer = new Serializer();
        serializer.serialize(employeeNumber, "EmployeeIds.ser");
    }

    /**
     * Returns true if the two objects have the same ID, false otherwise.
     *
     * @param object the object to be compare with this model.employee.Employee
     * @return true if two objects are equal
     */
    @Override
    public boolean equals(Object object) {
        return object instanceof Employee && ((Employee)object).getId() == id;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }
}
