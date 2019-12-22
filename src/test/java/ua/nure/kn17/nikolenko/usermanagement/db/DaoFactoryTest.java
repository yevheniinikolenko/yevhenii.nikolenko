package ua.nure.kn17.nikolenko.usermanagement.db;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DaoFactoryTest {

	@Before
    public void setUp() throws Exception {
    }
  
    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testGetUserDao() {

        try {
			DaoFactory daoFactory = DaoFactory.getInstance();
			assertNotNull("DaoFactory instance is null", daoFactory);
			UserDAO userDao = daoFactory.getUserDAO();
			assertNotNull("userDao instance is null", userDao);
		} catch (Exception e) {
			e.printStackTrace();
		}
    }
}