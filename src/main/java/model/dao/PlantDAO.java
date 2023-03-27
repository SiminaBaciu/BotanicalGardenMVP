package model.dao;

import model.Plant;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.lang.reflect.Field;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class PlantDAO extends AbstractDAO<Plant> {

    /**
     * Instantiates a new PlantDAO.
     */
    public PlantDAO() {
    }

    public List<Plant> getPlantsSortedByType() {
        List<Plant> plants = findAll();
        List<Plant> soretd = plants.stream().sorted(Comparator.comparing(Plant::getType)).collect(Collectors.toList());
        System.out.println(soretd.toString());
        return soretd;
    }

    public List<Plant> getPlantsSortedBySpecies() {
        List<Plant> plants = findAll();
        System.out.println("species");
        List<Plant> soretd = plants.stream().sorted(Comparator.comparing(Plant::getSpecies)).collect(Collectors.toList());
        System.out.println(soretd.toString());
        return soretd;
    }

    public List<Plant> filterPlants(String type, String species, String zone, String carnivore) {
        List<Plant> plants = findAll();
        System.out.println(type);
        System.out.println(species);
        System.out.println(zone);
        System.out.println(carnivore);
        List<Plant> filterd = plants;
        if (!type.equals("")) {
            filterd = filterd.stream().filter(plant -> plant.getType().equals(type)).collect(Collectors.toList());
        }
        if (!species.equals("")) {
            filterd = filterd.stream().filter(plant -> plant.getSpecies().equals(species)).collect(Collectors.toList());
        }
        if (!zone.equals("")) {
            filterd = filterd.stream().filter(plant -> plant.getZone().equals(zone)).collect(Collectors.toList());
        }
        if (!carnivore.equals("")) {
            filterd = filterd.stream().filter(plant -> plant.isIs_carnivorous() == Boolean.valueOf(carnivore)).collect(Collectors.toList());
        }

        System.out.println(filterd.toString());
        return filterd;
    }

    public JTable createFilteredTable(String type, String species, String zone, String carnivore) {
        List<Plant> objects = filterPlants(type, species, zone, carnivore);
        if (!objects.isEmpty()) {
            int tableSize = objects.get(0).getClass().getDeclaredFields().length;
            String[] columnNames = new String[tableSize];
            int columnIndex = 0;
            for (Field currentField : objects.get(0).getClass().getDeclaredFields()) {
                currentField.setAccessible(true);
                try {
                    columnNames[columnIndex] = currentField.getName();
                    columnIndex++;
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
            DefaultTableModel myModel = new DefaultTableModel();
            myModel.setColumnIdentifiers(columnNames);
            for (Object o : objects) {
                Object[] obj = new Object[tableSize];
                int col = 0;
                for (Field currentField : o.getClass().getDeclaredFields()) {
                    currentField.setAccessible(true);
                    try {
                        obj[col] = currentField.get(o);
                        col++;
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                myModel.addRow(obj);
            }
            JTable table = new JTable(myModel);
            table.setEnabled(true);
            table.setVisible(true);
            return table;
        }
        return null;
    }

    public JTable createSortedBySpeciesTable() {
        List<Plant> objects = getPlantsSortedBySpecies();
        if (!objects.isEmpty()) {
            int tableSize = objects.get(0).getClass().getDeclaredFields().length;
            String[] columnNames = new String[tableSize];
            int columnIndex = 0;
            for (Field currentField : objects.get(0).getClass().getDeclaredFields()) {
                currentField.setAccessible(true);
                try {
                    columnNames[columnIndex] = currentField.getName();
                    columnIndex++;
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
            DefaultTableModel myModel = new DefaultTableModel();
            myModel.setColumnIdentifiers(columnNames);
            for (Object o : objects) {
                Object[] obj = new Object[tableSize];
                int col = 0;
                for (Field currentField : o.getClass().getDeclaredFields()) {
                    currentField.setAccessible(true);
                    try {
                        obj[col] = currentField.get(o);
                        col++;
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                myModel.addRow(obj);
            }
            JTable table = new JTable(myModel);
            table.setEnabled(true);
            table.setVisible(true);
            return table;
        }
        return null;
    }

    public JTable createSortedByTypeTable() {
        List<Plant> objects = getPlantsSortedByType();
        if (!objects.isEmpty()) {
            int tableSize = objects.get(0).getClass().getDeclaredFields().length;
            String[] columnNames = new String[tableSize];
            int columnIndex = 0;
            for (Field currentField : objects.get(0).getClass().getDeclaredFields()) {
                currentField.setAccessible(true);
                try {
                    columnNames[columnIndex] = currentField.getName();
                    columnIndex++;
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }
            DefaultTableModel myModel = new DefaultTableModel();
            myModel.setColumnIdentifiers(columnNames);
            for (Object o : objects) {
                Object[] obj = new Object[tableSize];
                int col = 0;
                for (Field currentField : o.getClass().getDeclaredFields()) {
                    currentField.setAccessible(true);
                    try {
                        obj[col] = currentField.get(o);
                        col++;
                    } catch (IllegalArgumentException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
                myModel.addRow(obj);
            }
            JTable table = new JTable(myModel);
            table.setEnabled(true);
            table.setVisible(true);
            return table;
        }
        return null;
    }
}

