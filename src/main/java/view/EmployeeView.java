package view;

import presenter.EmployeePresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EmployeeView extends JFrame implements EmployeeInterface {

    Container container = getContentPane();

    private JLabel idLabel = new JLabel("ID: ");
    private JTextField idField = new JTextField();
    private JLabel nameLabel = new JLabel("Name: ");
    private JTextField nameField = new JTextField();
    private JLabel typeLabel = new JLabel("Type: ");
    private JTextField typeField = new JTextField();
    private JLabel speciesLabel = new JLabel("Species: ");
    private JTextField speciesField = new JTextField();
    private JButton back = new JButton("Back");
    private JButton insert = new JButton("Insert");
    private JButton delete = new JButton("Delete");
    private JButton update = new JButton("Update");
    private JButton plants = new JButton("View plants");
    private JScrollPane scrollPane = new JScrollPane();
    private EmployeePresenter employeePresenter = null;

    public EmployeeView() {

        setLayoutManag();
        setLocAndSize();
        addComponent();
        this.setTitle("Employee");
        //   this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(500, 150, 900, 700);

        employeePresenter = new EmployeePresenter(this);
        updatePlants();
    }

    /**
     * Sets the layout manager
     */
    public void setLayoutManag() {
        container.setLayout(null);
    }


    public void setLocAndSize() {
        idLabel.setBounds(50, 30, 100, 30);
        idField.setBounds(80, 30, 75, 30);

        nameLabel.setBounds(170, 30, 200, 30);
        nameField.setBounds(240, 30, 125, 30);

        typeLabel.setBounds(370, 30, 100, 30);
        typeField.setBounds(440, 30, 125, 30);

        speciesLabel.setBounds(580, 30, 100, 30);
        speciesField.setBounds(630, 30, 125, 30);

        plants.setBounds(100, 520, 100, 50);
        insert.setBounds(200, 520, 100, 50);
        delete.setBounds(330, 520, 100, 50);
        update.setBounds(460, 520, 100, 50);
        back.setBounds(590, 520, 100, 50);
        back.addActionListener(e -> {
            setVisible(false);
            //parentInterface.setVisible(true);

        });

        scrollPane.setBounds(140, 90, 600, 400);
    }

    /**
     * Adds all view parts into the container
     */
    public void addComponent() {
        container.add(idField);
        container.add(idLabel);
        container.add(nameField);
        container.add(typeField);
        container.add(nameLabel);
        container.add(typeLabel);
        container.add(speciesField);
        container.add(speciesLabel);
        container.add(delete);
        container.add(update);
        container.add(insert);
        container.add(plants);
        container.add(back);
        container.add(scrollPane);

        insert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                employeePresenter.insertPlant();
                updatePlants();
            }
        });
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                employeePresenter.deletePlant();
                updatePlants();
            }
        });
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                employeePresenter.updatePlant();
                updatePlants();
            }
        });

        plants.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserView userView = new UserView();
                userView.setVisible(true);
            }
        });
    }

    public void updatePlants() {
        JTable table = employeePresenter.updatePlantTable();
        getScrollPane().setViewportView(table);
        setVisible(true);
    }


    public int getIdField() {
        return Integer.parseInt(idField.getText());
    }

    public String getNameField() {
        return nameField.getText();
    }

    public String getTypeField() {
        return typeField.getText();
    }

    public String getSpeciesField() {
        return speciesField.getText();
    }

    public JScrollPane getScrollPane() {
        return this.scrollPane;
    }

    public void insertListener(ActionListener a) {
        insert.addActionListener(a);
    }

    public void deleteListener(ActionListener a) {
        delete.addActionListener(a);
    }

    public void updateListener(ActionListener a) {
        update.addActionListener(a);
    }

    public void plantListener(ActionListener a) {
        plants.addActionListener(a);
    }

}