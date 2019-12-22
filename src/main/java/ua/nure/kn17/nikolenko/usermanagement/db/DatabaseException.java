package ua.nure.kn17.nikolenko.usermanagement.db;

/**
 * Class with custom DB exceptions. Checked.
 */
public class DatabaseException extends Exception {
    private String name;

    /**
     * Constructor which set up name for new exception
     * @param name
     */
    DatabaseException (String name) {
        this.name = name;
    }

    /**
     * @return String name of exception
     */
    public String getName() {
        return name;
    }

    /**
     * Returns exception message from parent class
     * @return super.getMessage()
     */
    @Override
    public String getMessage() {
        return super.getMessage();
    }
}