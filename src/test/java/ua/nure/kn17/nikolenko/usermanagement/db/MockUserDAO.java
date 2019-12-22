package ua.nure.kn17.nikolenko.usermanagement.db;
import ua.nure.kn17.nikolenko.usermanagement.User;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MockUserDAO implements UserDAO {
    private Long id = 0L;
    private Map<Long, User> users = new HashMap<>();

    @Override
    public User create(User user) throws DatabaseException {
        Long currentId = ++id;
        user.setId(currentId);
        users.put(currentId, user);
        return user;
    }

    @Override
    public void update(User user) throws DatabaseException {
        Long currentId = user.getId();
        users.remove(currentId);
        users.put(currentId, user);
    }

    @Override
    public void delete(User user) throws DatabaseException {
        Long currentId = user.getId();
        users.remove(currentId);
    }
    
    @Override
    public Collection<User> find(String firstName, String lastName) throws DatabaseException {
        throw new UnsupportedOperationException();
    }

    @Override
    public User find(Long id) throws DatabaseException {
        return users.get(id);
    }

    @Override
    public Collection findAll() throws DatabaseException {
        return users.values();
    }

    @Override
    public void setConnectionFactory(ConnectionFactory connectionFactory) {

    }
}
