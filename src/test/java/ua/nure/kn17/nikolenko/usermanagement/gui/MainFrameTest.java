package ua.nure.kn17.nikolenko.usermanagement.gui;
import com.mockobjects.dynamic.Mock;
import junit.extensions.jfcunit.JFCTestCase;
import junit.extensions.jfcunit.JFCTestHelper;
import junit.extensions.jfcunit.TestHelper;
import junit.extensions.jfcunit.eventdata.MouseEventData;
import junit.extensions.jfcunit.eventdata.StringEventData;
import junit.extensions.jfcunit.finder.NamedComponentFinder;
import org.junit.Assert;
import ua.nure.kn17.nikolenko.usermanagement.User;
import ua.nure.kn17.nikolenko.usermanagement.db.DaoFactory;
import ua.nure.kn17.nikolenko.usermanagement.db.MockDaoFactory;
import ua.nure.kn17.nikolenko.usermanagement.util.Message;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Properties;

public class MainFrameTest extends JFCTestCase {
    private MainFrame mainFrame;
    private Mock mockUserDAO;

    public void setUp() throws Exception {
        super.setUp();

        try {
            Properties properties = new Properties();
            properties.setProperty("dao.factory", MockDaoFactory.class.getName());

            DaoFactory.init(properties);

            mockUserDAO = ((MockDaoFactory) DaoFactory.getInstance()).getMockUserDao();
            mockUserDAO.expectAndReturn("findAll", new ArrayList<>());

            setHelper(new JFCTestHelper());
            mainFrame = new MainFrame();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mainFrame.setVisible(true);
    }

    public void tearDown() throws Exception {
        try {
            mockUserDAO.verify();
            mainFrame.setVisible(false);
            TestHelper.cleanUp(this);
            super.tearDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Component find(Class componentElement, String name) {
        NamedComponentFinder finder = new NamedComponentFinder(componentElement, name);
        finder.setWait(0);

        Component component = finder.find(mainFrame, 0);
        Assert.assertNotNull("Could not find element " + name + ",", component);

        return component;
    }

    public void testBrowseConsole () {
        find(JButton.class, "detailsButton");
        find(JButton.class, "deleteButton");
        find(JPanel.class, "browserPanel");
        find(JButton.class, "editButton");
        find(JButton.class, "addButton");

        int expectedRowCount = 3;
        String expectedFirstColumn = Message.getString("id");
        String expectedSecondColumn = Message.getString("name");
        String expectedThirdColumn = Message.getString("surname");

        JTable table = (JTable) find(JTable.class, "userTable");

        assertEquals(expectedRowCount, table.getColumnCount());

        assertEquals(expectedFirstColumn, table.getColumnName(0));
        assertEquals(expectedSecondColumn, table.getColumnName(1));
        assertEquals(expectedThirdColumn, table.getColumnName(2));


    }

    public void testAddUser() {
        String firstName = "Ivan";
        String lastName = "Ivanov";
        LocalDate dateOfBirth = LocalDate.now();

        User user = new User(firstName, lastName, dateOfBirth);
        User expectedUser = new User(1L, firstName, lastName, dateOfBirth);

        mockUserDAO.expectAndReturn("create", user, expectedUser);

        ArrayList<User> userList = new ArrayList<>();
        userList.add(expectedUser);
        mockUserDAO.expectAndReturn("findAll", userList);

        JTable table = (JTable) find(JTable.class, "userTable");
        int expectedRows = 0;
        assertEquals(expectedRows, table.getRowCount());

        JButton addButton = (JButton) find(JButton.class, "addButton");
        getHelper().enterClickAndLeave(new MouseEventData(this, addButton));

        find(JPanel.class, "addPanel");
        find(JButton.class, "cancelButton");

        JTextField firstNameField = (JTextField) find(JTextField.class, "firstNameField");
        JTextField dateOfBirthField = (JTextField) find(JTextField.class, "dateOfBirthField");
        JTextField lastNameField = (JTextField) find(JTextField.class, "lastNameField");
        JButton okButton = (JButton) find(JButton.class, "okButton");

        getHelper().sendString(new StringEventData(this, firstNameField, firstName));
        getHelper().sendString(new StringEventData(this, lastNameField, lastName));
        getHelper().sendString(new StringEventData(this, dateOfBirthField, String.valueOf(dateOfBirth)));

        getHelper().enterClickAndLeave(new MouseEventData(this, okButton));

        find(JPanel.class, "browserPanel");
        table = (JTable) find(JTable.class, "userTable");
        expectedRows = 1;
        assertEquals(expectedRows, table.getRowCount());
    }
}