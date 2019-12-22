package ua.nure.kn17.nikolenko.usermanagement.web;
import ua.nure.kn17.nikolenko.usermanagement.User;
import java.text.DateFormat;
import java.util.Date;

public class EditServletTest extends MockServletTestCase {
    private static final Long ID_FOR_TUSER = 1000L;
    private static final String ID_FOR_TUSER_STRING = "1000";
    private static final String FIRST_NAME_FOR_TUSER = "John";
    private static final String LAST_NAME_FOR_TUSER = "Doe";
    private static final Date DATE_OF_BIRTHDAY_FOR_TUSER = new Date();

    private static final String OK_OPTION = "Ok";

    private static final String ATTR_ERROR = "error";

    @Override
    public void setUp() throws Exception {
        super.setUp();
        createServlet(EditServlet.class);
    }

    public void testEdit() {
        User user = new User(
                ID_FOR_TUSER,
                FIRST_NAME_FOR_TUSER,
                LAST_NAME_FOR_TUSER,
                DATE_OF_BIRTHDAY_FOR_TUSER
        );

        getMockUserDao().expect("update", user);

        addRequestParameter("id", ID_FOR_TUSER_STRING);
        addRequestParameter("firstName", FIRST_NAME_FOR_TUSER);
        addRequestParameter("lastName", LAST_NAME_FOR_TUSER);
        addRequestParameter("dateOfBirth", DateFormat.getDateInstance().format(DATE_OF_BIRTHDAY_FOR_TUSER));
        addRequestParameter("okButton", OK_OPTION);
        doPost();
    }

    public void testEditEmptyFirstName() {
        addRequestParameter("id", ID_FOR_TUSER_STRING);
        addRequestParameter("lastName", LAST_NAME_FOR_TUSER);
        addRequestParameter("dateOfBirth", DateFormat.getDateInstance().format(DATE_OF_BIRTHDAY_FOR_TUSER));
        addRequestParameter("okButton", OK_OPTION);
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute(ATTR_ERROR);
        assertNotNull("The session scope must have a error message", errorMessage);
    }

    public void testEditEmptyLastName() {
        addRequestParameter("id", ID_FOR_TUSER_STRING);
        addRequestParameter("firstName", FIRST_NAME_FOR_TUSER);
        addRequestParameter("dateOfBirth", DateFormat.getDateInstance().format(DATE_OF_BIRTHDAY_FOR_TUSER));
        addRequestParameter("okButton", OK_OPTION);
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute(ATTR_ERROR);
        assertNotNull("The session scope must have a error message", errorMessage);
    }

    public void testEditEmptyDateOfBirth() {
        addRequestParameter("id", ID_FOR_TUSER_STRING);
        addRequestParameter("firstName", FIRST_NAME_FOR_TUSER);
        addRequestParameter("lastName", LAST_NAME_FOR_TUSER);
        addRequestParameter("okButton", OK_OPTION);
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute(ATTR_ERROR);
        assertNotNull("The session scope must have a error message", errorMessage);
    }

    public void testEditInvalidDate() {
        addRequestParameter("id", ID_FOR_TUSER_STRING);
        addRequestParameter("firstName", FIRST_NAME_FOR_TUSER);
        addRequestParameter("lastName", LAST_NAME_FOR_TUSER);
        addRequestParameter("dateOfBirth", "#####");
        addRequestParameter("okButton", OK_OPTION);
        doPost();
        String errorMessage = (String) getWebMockObjectFactory().getMockRequest().getAttribute(ATTR_ERROR);
        assertNotNull("The session scope must have a error message", errorMessage);
    }
}
