package model.dao;

import model.Plant;
import model.connection.ConnectionFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Field;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.json.JSONArray;
import org.json.JSONObject;

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

    public List<Plant> searchPlants(String name) {
        List<Plant> plants = findAll();
        List<Plant> searched = plants;
        if (!name.equals("")) {
            searched = searched.stream().filter(plant -> plant.getName().equals(name)).collect(Collectors.toList());
        }
        System.out.println(searched.toString());
        return searched;
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

    public JTable createSearchedByNameTable(String name) {
        List<Plant> objects = searchPlants(name);
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

    public void sqlToCSV() {
        String filename = "C:\\Facultate\\ThirdYear\\Second_Sem\\SD\\databaseToCSV.csv";
        try {
            FileWriter fw = new FileWriter(filename);
            // Class.forName("com.mysql.jdbc.Driver");
            Connection conn = ConnectionFactory.getConnection();
            String query = "select * from plant";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                fw.append(rs.getString(1));
                fw.append(',');
                fw.append(rs.getString(2));
                fw.append(',');
                fw.append(rs.getString(3));
                fw.append(',');
                fw.append(rs.getString(4));
                fw.append(',');
                fw.append(rs.getString(5));
                fw.append(',');
                fw.append(rs.getString(6));
                fw.append('\n');
            }
            fw.flush();
            fw.close();
            conn.close();
            System.out.println("CSV File is created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sqlToTXT() {
        String filename = "C:\\Facultate\\ThirdYear\\Second_Sem\\SD\\databaseToTXT.txt";
        try {
            FileWriter fw = new FileWriter(filename);
            // Class.forName("com.mysql.jdbc.Driver");
            Connection conn = ConnectionFactory.getConnection();
            String query = "select * from plant";
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                fw.append(rs.getString(1));
                fw.append(',');
                fw.append(rs.getString(2));
                fw.append(',');
                fw.append(rs.getString(3));
                fw.append(',');
                fw.append(rs.getString(4));
                fw.append(',');
                fw.append(rs.getString(5));
                fw.append(',');
                fw.append(rs.getString(6));
                fw.append('\n');
            }
            fw.flush();
            fw.close();
            conn.close();
            System.out.println("TXT File is created successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sqlToXML() throws SQLException, IOException, TransformerException, ParserConfigurationException {

        Connection conn = ConnectionFactory.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT * FROM plant");

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.newDocument();

        Element root = doc.createElement("plants");
        doc.appendChild(root);

        while (rs.next()) {
            Element plantElement = doc.createElement("plant");
            root.appendChild(plantElement);

            Element idElement = doc.createElement("id");
            idElement.appendChild(doc.createTextNode(rs.getString("id")));
            plantElement.appendChild(idElement);

            Element nameElement = doc.createElement("name");
            nameElement.appendChild(doc.createTextNode(rs.getString("name")));
            plantElement.appendChild(nameElement);

            Element descriptionElement = doc.createElement("type");
            descriptionElement.appendChild(doc.createTextNode(rs.getString("type")));
            plantElement.appendChild(descriptionElement);

            Element lightElement = doc.createElement("species");
            lightElement.appendChild(doc.createTextNode(rs.getString("species")));
            plantElement.appendChild(lightElement);

            Element priceElement = doc.createElement("zone");
            priceElement.appendChild(doc.createTextNode(rs.getString("zone")));
            plantElement.appendChild(priceElement);

            Element difficultyElement = doc.createElement("is_carnivorous");
            difficultyElement.appendChild(doc.createTextNode(rs.getString("is_carnivorous")));
            plantElement.appendChild(difficultyElement);

        }

        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("C:\\Facultate\\ThirdYear\\Second_Sem\\SD\\databaseToXML.xml"));
        transformer.transform(source, result);

        stmt.close();
        conn.close();
    }

    public void sqlToJSON() {
        try {
            Connection conn = ConnectionFactory.getConnection();
            Statement stmt = conn.createStatement();
            String sql = "SELECT * FROM plant";
            ResultSet rs = stmt.executeQuery(sql);

            JSONArray plantList = new JSONArray();

            while (rs.next()) {
                JSONObject plant = new JSONObject();
                plant.put("id", rs.getInt("id"));
                plant.put("name", rs.getString("name"));
                plant.put("type", rs.getString("type"));
                plant.put("species", rs.getString("species"));
                plant.put("zone", rs.getString("zone"));
                plant.put("is_carnivorous", rs.getString("is_carnivorous"));
                plantList.put(plant);
            }

            FileWriter file = new FileWriter("C:\\Facultate\\ThirdYear\\Second_Sem\\SD\\databaseToJSON.json");
            file.write(plantList.toString());
            file.flush();
            file.close();
            System.out.println("JSON file has been created successfully.");

            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}



