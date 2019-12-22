package ua.nure.kn17.nikolenko.usermanagement.web;
import ua.nure.kn17.nikolenko.usermanagement.User;
import ua.nure.kn17.nikolenko.usermanagement.db.DaoFactory;
import ua.nure.kn17.nikolenko.usermanagement.db.DatabaseException;
import ua.nure.kn17.nikolenko.usermanagement.db.UserDAO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;

public class BrowseServlet extends HttpServlet {

    private static final long serialVersionUID = -3694523138837520143L;
    private static final String ATTR_USERS = "users";
    private static final String ATTR_ERROR = "error";
    private static final String BROWSE_PAGE = "/browse.jsp";
    private static final String EDIT_SERVLET = "/edit";
    private static final String ADD_SERVLET = "/add";
    private static final String BROWSE_SERVLET = "/browse";
    private static final String DETAILS_SERVLET = "/details";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("addButton") != null) {
            add(req, resp);
        } else if (req.getParameter("editButton") != null) {
            edit(req, resp);
        } else if (req.getParameter("deleteButton") !=  null) {
            delete(req, resp);
        } else if (req.getParameter("detailsButton") != null) {
            details(req, resp);
        } else {
            browse(req, resp);
        }
    }

    private void browse(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Collection<User> collectionOfUsers;
        try {
            collectionOfUsers = DaoFactory.getInstance().getUserDAO().findAll();
            req.getSession(true).setAttribute(ATTR_USERS, collectionOfUsers);
            req.getRequestDispatcher(BROWSE_PAGE).forward(req, resp);
        } catch (DatabaseException e) {
            throw new ServletException("Can not get users from DB", e);
        }
    }

    private void add(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(ADD_SERVLET).forward(req, resp);
    }
    private void edit(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStrUser = req.getParameter("id");

        if (idStrUser == null || idStrUser.trim().isEmpty()) {
            req.setAttribute(ATTR_ERROR, "You must select a user");
            req.getRequestDispatcher(BROWSE_PAGE).forward(req, resp);
            return;
        }

        try {
            User foundUser = DaoFactory.getInstance().getUserDAO().find(Long.parseLong(idStrUser));
            req.getSession(true).setAttribute("user", foundUser);
        } catch (Exception e) {
            req.setAttribute(ATTR_ERROR, "ERROR"  + e.toString());
            req.getRequestDispatcher(BROWSE_PAGE).forward(req, resp);
            return;
        }
        req.getRequestDispatcher(EDIT_SERVLET).forward(req, resp);
    }
    private void delete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStrUser = req.getParameter("id");

        if (idStrUser == null || idStrUser.trim().isEmpty()) {
            req.setAttribute(ATTR_ERROR, "You must select a user");
            req.getRequestDispatcher(BROWSE_PAGE).forward(req, resp);
            return;
        }

        try {
            UserDAO userDao = DaoFactory.getInstance().getUserDAO();
            User deleteUser = userDao.find(Long.parseLong(idStrUser));
            userDao.delete(deleteUser);
        } catch (Exception e) {
            req.setAttribute(ATTR_ERROR, "ERROR"  + e.toString());
            req.getRequestDispatcher(BROWSE_PAGE).forward(req, resp);
            return;
        }
        resp.sendRedirect(BROWSE_SERVLET);
    }
    private void details(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String idStrUser = req.getParameter("id");

        if (idStrUser == null || idStrUser.trim().isEmpty()) {
            req.setAttribute(ATTR_ERROR, "You must select a user");
            req.getRequestDispatcher(BROWSE_PAGE).forward(req, resp);
            return;
        }

        try {
            User foundUser = DaoFactory.getInstance().getUserDAO().find(Long.parseLong(idStrUser));
            req.getSession(true).setAttribute("user", foundUser);
        } catch (Exception e) {
            req.setAttribute(ATTR_ERROR, "ERROR"  + e.toString());
            req.getRequestDispatcher(BROWSE_PAGE).forward(req, resp);
            return;
        }
        req.getRequestDispatcher(DETAILS_SERVLET).forward(req, resp);
    }


}
