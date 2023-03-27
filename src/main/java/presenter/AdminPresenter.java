package presenter;

import model.AppUser;
import model.dao.UserDAO;
import view.AdminInterface;

import javax.swing.*;

public class AdminPresenter {
    AdminInterface adminInterface;
    UserDAO userDAO = new UserDAO();

    public AdminPresenter(AdminInterface adminInterface) {
        this.adminInterface = adminInterface;
    }

    public JTable updateUserTable() {
        return userDAO.createTable();
    }

    public AppUser insertUser() {
        int id = adminInterface.getIdField();
        String username = adminInterface.getUserNameField();
        String password = adminInterface.getPasswordField();
        String type = adminInterface.getTypeField();
        AppUser appUser = new AppUser(id, username, password, type);
        return userDAO.insert(appUser);
    }

    public String deleteUser() {
        int id = adminInterface.getIdField();
        String username = adminInterface.getUserNameField();
        String password = adminInterface.getPasswordField();
        String type = adminInterface.getTypeField();
        AppUser appUser = new AppUser(id, username, password, type);
        return userDAO.delete(String.valueOf(id));
    }

    public void updateUser() {
        int id = adminInterface.getIdField();
        String username = adminInterface.getUserNameField();
        String password = adminInterface.getPasswordField();
        String type = adminInterface.getTypeField();
        AppUser appUser = new AppUser(id, username, password, type);
        userDAO.Update(appUser);
    }
}
