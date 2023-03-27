package presenter;


import model.AppUser;
import model.Plant;
import model.dao.PlantDAO;
import view.EmployeeInterface;

import javax.swing.*;

public class EmployeePresenter {
    EmployeeInterface employeeInterface;
    AppUser appUser = null;
    PlantDAO plantDAO = new PlantDAO();

    public EmployeePresenter(EmployeeInterface employeeInterface) {
        this.employeeInterface = employeeInterface;
    }

    public JTable updatePlantTable() {
        return plantDAO.createTable();
    }

    public Plant insertPlant() {
        int id = employeeInterface.getIdField();
        String name = employeeInterface.getNameField();
        String type = employeeInterface.getTypeField();
        String species = employeeInterface.getSpeciesField();
        Plant p = new Plant(id, name, type, species, "NW", true);
        return plantDAO.insert(p);
    }

    public String deletePlant() {
        int id = employeeInterface.getIdField();
        String name = employeeInterface.getNameField();
        String type = employeeInterface.getTypeField();
        String species = employeeInterface.getSpeciesField();
        Plant p = new Plant(id, name, type, species, "NW", true);
        return plantDAO.delete(String.valueOf(id));
    }

    public void updatePlant() {
        int id = employeeInterface.getIdField();
        String name = employeeInterface.getNameField();
        String type = employeeInterface.getTypeField();
        String species = employeeInterface.getSpeciesField();
        Plant p = new Plant(id, name, type, species, "NW", true);
        plantDAO.Update(p);
    }

}
