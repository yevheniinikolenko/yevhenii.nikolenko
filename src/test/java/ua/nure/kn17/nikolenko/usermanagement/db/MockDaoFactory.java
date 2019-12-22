package ua.nure.kn17.nikolenko.usermanagement.db;
import com.mockobjects.dynamic.Mock;

public class MockDaoFactory extends DaoFactory {
    private Mock mockUserDao;
    
    private static final Class<UserDao> MOCKED_CLASS = UserDAO.class;

    public MockDaoFactory() {
        this.mockUserDao = new Mock(MOCKED_CLASS);
    }

    public Mock getMockUserDao() {
        return mockUserDao;
    }

    @Override
    public UserDAO getUserDAO(){
        return (UserDAO) mockUserDao.proxy();
    }
}