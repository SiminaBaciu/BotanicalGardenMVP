package view;

import presenter.UserPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Class ClientView creates the view for the clients
 */
public class UserView extends JFrame implements UserInterface {

    Container container = getContentPane();
    private JLabel carnivoreLabel = new JLabel("Carnivore: ");
    private JTextField carnivoreField = new JTextField();
    private JLabel zoneLabel = new JLabel("Zone: ");
    private JTextField zoneField = new JTextField();
    private JLabel typeLabel = new JLabel("Type: ");
    private JTextField typeField = new JTextField();
    private JLabel speciesLabel = new JLabel("Species: ");
    private JTextField speciesField = new JTextField();

    private JButton back = new JButton("Back");
    private JButton sortByType = new JButton("Sort Type");
    private JButton sortBySpecies = new JButton("Sort Species");
    private JButton filter = new JButton("Filter");
    private JScrollPane scrollPane = new JScrollPane();

    UserPresenter userPresenter = null;

    /*
     * Constructor of the clientView class
     * @param parentInterface
     */
    public UserView() {

        setLayoutManag();
        setLocAndSize();
        addComponent();
        this.setTitle("Clients");
        //this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(500, 150, 900, 700);

        userPresenter = new UserPresenter(this);
        updatePlants();
    }

    /**
     * Sets the layout manager
     */
    public void setLayoutManag() {
        container.setLayout(null);
    }


    public void setLocAndSize() {

        carnivoreLabel.setBounds(50, 30, 100, 30);
        carnivoreField.setBounds(80, 30, 75, 30);

        zoneLabel.setBounds(170, 30, 200, 30);
        zoneField.setBounds(240, 30, 125, 30);

        typeLabel.setBounds(370, 30, 100, 30);
        typeField.setBounds(440, 30, 125, 30);

        speciesLabel.setBounds(580, 30, 100, 30);
        speciesField.setBounds(630, 30, 125, 30);

        sortByType.setBounds(200, 520, 100, 50);
        sortBySpecies.setBounds(330, 520, 100, 50);
        filter.setBounds(460, 520, 100, 50);
        back.setBounds(590, 520, 100, 50);
        back.addActionListener(e -> {
            setVisible(false);

        });

        scrollPane.setBounds(140, 90, 600, 400);
    }

    /**
     * Adds all view parts into the container
     */
    public void addComponent() {
        container.add(carnivoreField);
        container.add(carnivoreLabel);
        container.add(zoneField);
        container.add(typeField);
        container.add(zoneLabel);
        container.add(typeLabel);
        container.add(speciesField);
        container.add(speciesLabel);
        container.add(sortByType);
        container.add(sortBySpecies);
        container.add(filter);
        container.add(back);
        container.add(scrollPane);


        sortBySpecies.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable table = userPresenter.createSortBySpeciesTable();
                getScrollPane().setViewportView(table);
                setVisible(true);
            }
        });

        sortByType.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable table = userPresenter.createSortByTypeTable();
                getScrollPane().setViewportView(table);
                setVisible(true);
            }
        });

        filter.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JTable table = userPresenter.createFilterTable();
                getScrollPane().setViewportView(table);
                setVisible(true);
            }
        });


    }

    public void updatePlants() {
        JTable table = userPresenter.updatePlantTable();
        getScrollPane().setViewportView(table);
        setVisible(true);

    }

    public void addFilterListener(ActionListener a) {
        filter.addActionListener(a);
    }

    public String getZone() {
        return zoneField.getText();
    }

    public String getCarnivore() {
        return carnivoreField.getText();
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


}
