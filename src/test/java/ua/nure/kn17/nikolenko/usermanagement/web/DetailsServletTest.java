package ua.nure.kn17.nikolenko.usermanagement.web;
import ua.nure.kn17.nikolenko.usermanagement.User;
public class DetailsServletTest extends MockServletTestCase {

    private static final String ATTR_USER = "user";
    private static final String BACK_OPTION = "Back";

    @Override
    public void setUp() throws Exception {
        super.setUp();
        createServlet(DetailsServlet.class);
    }

    public void testBack() {
        addRequestParameter("backButton", BACK_OPTION);
        User nullUser = (User) getWebMockObjectFactory().getMockSession().getAttribute(ATTR_USER);
        assertNull("User must not exist in session scope", nullUser);
    }
}
