package ua.nure.kn17.nikolenko.usermanagement.web;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class DetailsServlet extends HttpServlet {

    private static final String ATTR_USER = "user";
    private static final String BROWSE_SERVLET = "/browse";
    private static final String DETAILS_PAGE = "/details.jsp";

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("backButton") != null) {
            req.getSession(true).removeAttribute(ATTR_USER);
            req.getRequestDispatcher(BROWSE_SERVLET).forward(req, resp);
        } else {
            req.getRequestDispatcher(DETAILS_PAGE).forward(req, resp);
        }
    }
}
