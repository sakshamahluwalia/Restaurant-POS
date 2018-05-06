package model.organizer;


import model.employee.*;
import model.Serializer;

import java.util.ArrayList;

/**
 * An model.organizer.EmployeeOrganizer uses the Singleton design pattern and is responsible for managing and building Employees.
 */
public class EmployeeOrganizer {

    /**
     * The ArrayList containing all employees
     */
    private ArrayList<Employee> employees;

    /**
     * Serializer object used to serialize the employee list.
     */
    private Serializer serializer = new Serializer();

    /**
     * The only instance of this model.organizer.EmployeeOrganizer
     */
    private static final EmployeeOrganizer employeeOrganizer = new EmployeeOrganizer();

    /**
     * Constructs a private model.organizer.EmployeeOrganizer
     */
    private EmployeeOrganizer() {
        employees = serializer.deserialize("employees.ser");
    }

    /**
     * Returns an instance of this model.organizer.EmployeeOrganizer.
     *
     * @return only instance of model.organizer.EmployeeOrganizer
     */
    public static EmployeeOrganizer getInstance() {
        return employeeOrganizer;
    }

    /**
     * Returns an model.employee.Employee with the a given id.
     *
     * @param id id of model.employee.Employee we're finding
     * @return model.employee.Employee with the same id
     */
    public Employee getEmployee(int id) {
        for (Employee employee: employees) {
            if (employee.getId() == id) {
                return employee;
            }
        }
        return null;
    }

    /**
     * Adds an model.employee.Employee object to this model.organizer.EmployeeOrganizer.
     *
     * @param employee model.employee.Employee to be added to the ArrayList of employees
     */
    private void addEmployee(Employee employee) {
        if (!employees.contains(employee)) {
            employees.add(employee);
        }
    }

    /**
     * Removes an model.employee.Employee object from this model.organizer.EmployeeOrganizer.
     *
     * @param employee model.employee.Employee to be removed from ArrayList of employees
     */
    public void removeEmployee(Employee employee) {
        employees.remove(employee);
    }

    /**
     * Returns an ArrayList of all Employee objects.
     *
     * @return ArrayList of all Employee objects
     */
    public ArrayList<Employee> getEmployees() {
        return employees;
    }

    /**
     * This method is responsible for updating the data in the employee list
     * and s
     */
    public void updateEmployees() {
        serializer.serialize(employees, "employees.ser");
    }

    /**
     * Builds subclass objects of Employees.
     *
     * @param firstName the first name of this Employee
     * @param lastName the last name of this Employee
     * @param employeeType the type of model.employee.Employee we want to build
     * @return the model.employee.Employee that is built
     */
    public Employee buildEmployee(String firstName, String lastName, String password, String employeeType) {
        Employee employee = null;
        if (employeeType.equalsIgnoreCase("Server")) {
            employee = new Server(firstName, lastName, password);
        } else if (employeeType.equalsIgnoreCase("Cook")) {
            employee = new Cook(firstName, lastName, password);
        } else if (employeeType.equalsIgnoreCase("Receiver")) {
            employee = new Receiver(firstName, lastName, password);
        } else if (employeeType.equalsIgnoreCase("Manager")) {
            employee = new Manager(firstName, lastName, password);
        }
        if (employee != null) {
            addEmployee(employee);
        }
        return employee;
    }

    /**
     * Validates whether the password is valid or not for the Employee
     * @param employee employee we are verifying
     * @param password password to check
     * @return true if information is true
     */
    public boolean validateEmployee(Employee employee, String password) {
        return employee.getPassword().equals(password);
    }
}
