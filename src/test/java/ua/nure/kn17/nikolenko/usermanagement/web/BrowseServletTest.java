package uua.nure.kn17.nikolenko.usermanagement.web;
import ua.nure.kn17.nikolenko.usermanagement.User;
import ua.nure.kn17.nikolenko.usermanagement.db.DatabaseException;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class BrowseServletTest extends MockServletTestCase {

    private static final Long ID_FOR_TUSER = 1000L;
    private static final String ID_FOR_TUSER_STRING = "1000";
    private static final String FIRST_NAME_FOR_TUSER = "John";
    private static final String LAST_NAME_FOR_TUSER = "Doe";
    private static final Date DATE_OF_BIRTHDAY_FOR_TUSER = new Date();

    private static final String DETAILS_OPTION = "Details";
    private static final String EDIT_OPTION = "Edit";
    private static final String DELETE_OPTION = "Delete";

    private static final String ATTR_USERS = "users";
    private static final String ATTR_USER = "user";
    private static final String ATTR_ERROR = "error";


    @Override
    public void setUp() throws Exception {
        super.setUp();
        createServlet(BrowseServlet.class);
    }

    public void testBrowse() {
        User user = new User(
                ID_FOR_TUSER,
                FIRST_NAME_FOR_TUSER,
                LAST_NAME_FOR_TUSER,
                DATE_OF_BIRTHDAY_FOR_TUSER
        );

        List<User> listOfUsers = Collections.singletonList(user);

        getMockUserDao().expectAndReturn("findAll", listOfUsers);

        doGet();

        Collection<User> collectionOfUsers = (Collection<User>) getWebMockObjectFactory()
                .getMockSession()
                .getAttribute(ATTR_USERS);
        assertNotNull("Could not find list of users in sessions", collectionOfUsers);
        assertSame(listOfUsers, collectionOfUsers);
    }

    public void testEdit() {
        User user = new User(
                ID_FOR_TUSER,
                FIRST_NAME_FOR_TUSER,
                LAST_NAME_FOR_TUSER,
                DATE_OF_BIRTHDAY_FOR_TUSER
        );

        getMockUserDao().expectAndReturn("find", ID_FOR_TUSER, user);

        addRequestParameter("editButton", EDIT_OPTION);
        addRequestParameter("id", ID_FOR_TUSER_STRING);

        doPost();

        User userInSession = (User) getWebMockObjectFactory().getMockSession().getAttribute(ATTR_USER);
        assertNotNull("Could not find user in session", userInSession);
        assertSame(user, userInSession);
    }

    public void testEditWithEmptyId() {
        addRequestParameter("editButton", EDIT_OPTION);

        doPost();

        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute(ATTR_ERROR);
        assertNotNull("The session scope must have a error message", errorMessage);
    }

    public void testDetails() {
        User user = new User(
                ID_FOR_TUSER,
                FIRST_NAME_FOR_TUSER,
                LAST_NAME_FOR_TUSER,
                DATE_OF_BIRTHDAY_FOR_TUSER
        );

        getMockUserDao().expectAndReturn("find", ID_FOR_TUSER, user);

        addRequestParameter("detailsButton", DETAILS_OPTION);
        addRequestParameter("id", ID_FOR_TUSER_STRING);

        doPost();

        User userInSession = (User) getWebMockObjectFactory().getMockSession().getAttribute(ATTR_USER);
        assertNotNull("Could not find user in session", userInSession);
        assertSame(user, userInSession);
    }

    public void testDetailsWithEmptyId() {
        addRequestParameter("detailsButton", DETAILS_OPTION);

        doPost();

        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute(ATTR_ERROR);
        assertNotNull("The session scope must have a error message", errorMessage);
    }

    public void testDelete() {
        User user = new User(
                ID_FOR_TUSER,
                FIRST_NAME_FOR_TUSER,
                LAST_NAME_FOR_TUSER,
                DATE_OF_BIRTHDAY_FOR_TUSER
        );

        getMockUserDao().expectAndReturn("find", ID_FOR_TUSER, user);
        getMockUserDao().expect("delete", user);

        addRequestParameter("deleteButton", DELETE_OPTION);
        addRequestParameter("id", ID_FOR_TUSER_STRING);

        doPost();

        User userInSession = (User) getWebMockObjectFactory().getMockSession().getAttribute(ATTR_USER);
        assertNull("The user must not exists in session scope", userInSession);
    }

    public void testDeleteWithEmptyId() {
        addRequestParameter("deleteButton", DELETE_OPTION);

        doPost();

        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute(ATTR_ERROR);
        assertNotNull("The session scope must have a error message", errorMessage);
    }
}
