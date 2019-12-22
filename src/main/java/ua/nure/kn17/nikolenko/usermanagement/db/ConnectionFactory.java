package ua.nure.kn17.nikolenko.usermanagement.db;
import java.sql.Connection;

/**
 * Interface to create connection with DB
 */
public interface ConnectionFactory {
    Connection createConnection () throws DatabaseException;
}