package ua.nure.kn17.nikolenko.usermanagement.db;
import ua.nure.kn17.nikolenko.usermanagement.User;
import java.util.Collection;

public interface UserDAO {
    User create (User user) throws DatabaseException;
    void update (User user) throws DatabaseException;
    void delete (User user) throws DatabaseException;
    User find (Long id) throws DatabaseException;
    Collection<User> find(String firstName, String lastName) throws DatabaseException;
    Collection findAll () throws DatabaseException;
    void setConnectionFactory(ConnectionFactory connectionFactory);
}