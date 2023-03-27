import model.connection.ConnectionFactory;
import view.LoginView;

import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String args[]) throws SQLException {
        Connection con = ConnectionFactory.getConnection();
        if (con != null) {
            System.out.println("Connected!");
        }
        LoginView login = new LoginView();

    }

}
