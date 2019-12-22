package ua.nure.kn17.nikolenko.usermanagement.web;
import ua.nure.kn17.nikolenko.usermanagement.User;
import ua.nure.kn17.nikolenko.usermanagement.db.DaoFactory;
import ua.nure.kn17.nikolenko.usermanagement.db.DatabaseException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AddServlet extends EditServlet {
    private static final long serialVersionUID = -5700145837261979436L;
    private static final String ADD_PAGE = "/add.jsp";

    
    
    @Override
    protected void showPage(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(ADD_PAGE).forward(req, resp);
        return;
    }

    
    
    
    @Override
    protected void processUser(User user) throws DatabaseException {
        DaoFactory.getInstance().getUserDAO().create(user);
    }
}
