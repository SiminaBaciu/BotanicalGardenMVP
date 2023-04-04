import model.connection.ConnectionFactory;
import model.dao.PlantDAO;
import view.LoginView;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class Main {
    public static void main(String args[]) throws SQLException, IOException, ParserConfigurationException, TransformerException {
        Connection con = ConnectionFactory.getConnection();
        if (con != null) {
            System.out.println("Connected!");
        }
        LoginView login = new LoginView();
        PlantDAO plantDAO = new PlantDAO();
        plantDAO.sqlToCSV();
        plantDAO.sqlToJSON();
        plantDAO.sqlToTXT();
        plantDAO.sqlToXML();
    }
}
