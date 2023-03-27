package view;

import presenter.AdminPresenter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminView extends JFrame implements AdminInterface {
    Container container = getContentPane();

    private JLabel idLabel = new JLabel("ID: ");
    private JTextField idField = new JTextField();
    private JLabel usernameLabel = new JLabel("Username: ");
    private JTextField userNameField = new JTextField();
    private JLabel passwordLabel = new JLabel("Password: ");
    private JTextField passwordField = new JTextField();
    private JLabel typeLabel = new JLabel("Type: ");
    private JTextField typeField = new JTextField();
    private JButton back = new JButton("Back");
    private JButton insert = new JButton("Insert");
    private JButton delete = new JButton("Delete");
    private JButton update = new JButton("Update");
    private JButton plants = new JButton("View plants");
    private JScrollPane scrollPane = new JScrollPane();

    private AdminPresenter adminPresenter = null;
    //LoginView login = new LoginView();

    public AdminView() {

        setLayoutManag();
        setLocAndSize();
        addComponent();
        this.setTitle("Admin");
        // this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setBounds(500, 150, 900, 700);
        adminPresenter = new AdminPresenter(this);
        updateUsers();
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

        usernameLabel.setBounds(170, 30, 200, 30);
        userNameField.setBounds(240, 30, 125, 30);

        passwordLabel.setBounds(370, 30, 100, 30);
        passwordField.setBounds(440, 30, 125, 30);

        typeLabel.setBounds(580, 30, 100, 30);
        typeField.setBounds(630, 30, 125, 30);

        plants.setBounds(100, 520, 100, 50);
        insert.setBounds(200, 520, 100, 50);
        delete.setBounds(330, 520, 100, 50);
        update.setBounds(460, 520, 100, 50);
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
        container.add(idField);
        container.add(idLabel);
        container.add(userNameField);
        container.add(passwordField);
        container.add(usernameLabel);
        container.add(passwordLabel);
        container.add(typeField);
        container.add(typeLabel);
        container.add(delete);
        container.add(update);
        container.add(insert);
        container.add(back);
        container.add(plants);
        container.add(scrollPane);


        insert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminPresenter.insertUser();
                updateUsers();
            }
        });
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminPresenter.deleteUser();
                updateUsers();
            }
        });
        update.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adminPresenter.updateUser();
                updateUsers();
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

    public void updateUsers() {
        JTable table = adminPresenter.updateUserTable();
        getScrollPane().setViewportView(table);
        setVisible(true);
    }

    public int getIdField() {
        return Integer.parseInt(idField.getText());
    }

    public String getUserNameField() {
        return userNameField.getText();
    }

    public String getTypeField() {
        return typeField.getText();
    }

    public String getPasswordField() {
        return passwordField.getText();
    }

    public JScrollPane getScrollPane() {
        return this.scrollPane;
    }

}
