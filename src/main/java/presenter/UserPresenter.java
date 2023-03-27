package presenter;

import model.AppUser;
import model.dao.PlantDAO;
import view.UserInterface;

import javax.swing.*;

public class UserPresenter {
    UserInterface userInterface;
    AppUser appUser = null;
    PlantDAO plantDAO = new PlantDAO();

    public UserPresenter(UserInterface userInterface) {
        this.userInterface = userInterface;
    }

    public JTable createFilterTable() {
        return plantDAO.createFilteredTable(userInterface.getTypeField(), userInterface.getSpeciesField(), userInterface.getZone(), userInterface.getCarnivore());
    }

    public JTable createSortByTypeTable() {
        return plantDAO.createSortedByTypeTable();
    }

    public JTable createSortBySpeciesTable() {
        return plantDAO.createSortedBySpeciesTable();
    }

    public JTable updatePlantTable() {
        return plantDAO.createTable();
    }


}
