package ua.nure.kn17.nikolenko.usermanagement.web;
import ua.nure.kn17.nikolenko.usermanagement.User;
import ua.nure.kn17.nikolenko.usermanagement.db.DaoFactory;
import ua.nure.kn17.nikolenko.usermanagement.db.DatabaseException;
import ua.nure.kn17.nikolenkoo.usermanagement.web.Exception.ValidationException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;

public class EditServlet extends HttpServlet {
    private static final long serialVersionUID = -2174999686644907833L;
    private static final String EDIT_PAGE = "/edit.jsp";
    private static final String BROWSE_SERVLET = "/browse";
    private static final String ATTR_ERROR = "error";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("okButton") != null) {
            doOk(req, resp);
        } else if (req.getParameter("cancelButton") != null) {
            doCancel(req, resp);
        } else {
            showPage(req, resp);
        }
    }

    protected void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(EDIT_PAGE).forward(req, resp);
    }

    private void doCancel(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(BROWSE_SERVLET).forward(req, resp);
    }

    private void doOk(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = null;
        try {
            user = getUserFromRequest(req);
        } catch (ValidationException e) {
            req.setAttribute(ATTR_ERROR, e.getMessage());
            showPage(req, resp);
            return;
        }
        try {
            processUser(user);
        } catch (DatabaseException e) {
            throw new ServletException("Could not update users in DB", e);
        }
        req.getRequestDispatcher(BROWSE_SERVLET).forward(req, resp);
    }

    protected void processUser(User user) throws DatabaseException {
        DaoFactory.getInstance().getUserDAO().update(user);
    }

    public User getUserFromRequest(HttpServletRequest req) throws ValidationException {
        User user = new User();
        String idString = req.getParameter("id");
        String firstName = req.getParameter("firstName");
        String lastName = req.getParameter("lastName");
        String dateOfBirthString = req.getParameter("dateOfBirth");

        if (firstName == null) {
            throw new ValidationException("First name is empty!");
        }

        if (lastName == null) {
            throw new ValidationException("Last name is empty!");
        }

        if (dateOfBirthString == null) {
            throw new ValidationException("Date of birth is empty!");
        }

        if (idString != null) {
            user.setId(new Long(idString));
        }
        user.setFirstName(firstName);
        user.setLastName(lastName);

        try {
            user.setDateOfBirth(DateFormat.getDateInstance().parse(dateOfBirthString));
        } catch (ParseException e) {
            throw new ValidationException("Date format is incorrect!", e);
        }

        return user;
    }
}
