package presenter;

import model.AppUser;
import model.dao.UserDAO;
import view.LoginInterface;

import java.util.List;

public class LoginPresenter{
    private LoginInterface loginInterface;
    private UserDAO userDAO;

    public LoginPresenter(LoginInterface loginInterface) {
        this.loginInterface = loginInterface;
        this.userDAO = new UserDAO();
    }

    public String verifyUser(String username, String password) {
        List<AppUser> users = userDAO.findAll();
        for (AppUser user : users) {
            if (user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return user.getType();
            }
        }
        return null;
    }
}
