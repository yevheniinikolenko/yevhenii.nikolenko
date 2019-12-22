package ua.nure.kn17.nikolenko.usermanagement;
import org.junit.Before;
import org.junit.Test;
import java.time.LocalDate;
import static org.junit.Assert.assertEquals;
public class UserTest {
    private User user;
    private static final Long ID = 1L;
    private static final String FIRSTNAME = "Иван";
    private static final String LASTNAME = "Иванов";

    //Set up method to initialize user object
    @Before
    public void setUp()  throws Exception {
        user = new User(ID, FIRSTNAME, LASTNAME, null);
    }

    //Some of this methods may not work after October 31

    // Test to get user full name
    @Test
    public void testGetFullName() throws Exception {
        assertEquals("Иванов, Иван", user.getFullName());
    }

    // Birthday before current day and month
    @Test
    public void testGetAgeAfter() throws Exception {
        LocalDate localDate =LocalDate.of(1998, 4, 8);
        User user = new User(ID, FIRSTNAME, LASTNAME, localDate);
        assertEquals(20, user.getAge());
    }

    // Birthday after current day and month
    @Test
    public void testGetAgeBefore() throws Exception {
        LocalDate localDate = LocalDate.of(1998, 12, 13);
        User user = new User(ID, FIRSTNAME, LASTNAME, localDate);
        assertEquals(19, user.getAge());
    }

    // Birthday in current month already passed
    @Test
    public void testGetAgeSameMonthAfter() throws Exception {
        LocalDate localDate = LocalDate.of(1998, 10, 10);
        User user = new User(ID, FIRSTNAME, LASTNAME, localDate);
        assertEquals(20, user.getAge());
    }

    // Birthday in current month not passed yet
    @Test
    public void testGetAgeSameMonthBefore() throws Exception {
        LocalDate localDate = LocalDate.of(1998, 10, 31);
        User user = new User(ID, FIRSTNAME, LASTNAME, localDate);
        assertEquals(19, user.getAge());
    }

    // Birthday is today
    @Test
    public void testGetAgeSameDay () throws Exception {
        LocalDate localDate = LocalDate.now();
        localDate = localDate.withYear(1998);
        User user = new User(ID, FIRSTNAME, LASTNAME, localDate);
        assertEquals(20, user.getAge());
    }
}