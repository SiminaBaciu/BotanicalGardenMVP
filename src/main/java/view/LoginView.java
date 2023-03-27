package view;

import presenter.LoginPresenter;

import java.awt.*;
import java.awt.event.*;
import java.util.Objects;
import javax.swing.*;

public class LoginView extends JFrame implements LoginInterface {

    Container container = getContentPane();

    private JLabel usernameLabel = new JLabel("Username:");
    private JTextField username = new JTextField(5);
    private JLabel passwordLabel = new JLabel("Password:");
    private JPasswordField password = new JPasswordField(5);
    private JButton login = new JButton("Login");
    private JButton client = new JButton("Client");
    LoginPresenter loginPresenter;

    public LoginView() {

        //adjust size and set layout
        setPreferredSize(new Dimension(500, 300));
        setLayoutManag();
        setLocAndSize();
        addComponent();

        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(true);
        loginPresenter = new LoginPresenter(this);
    }

    public void setLayoutManag() {
        container.setLayout(null);
    }

    public void setLocAndSize() {

        username.setBounds(170, 20, 150, 30);
        usernameLabel.setBounds(80, 20, 100, 30);

        password.setBounds(170, 60, 150, 30);
        passwordLabel.setBounds(80, 60, 150, 30);

        login.setBounds(70, 150, 150, 30);
        client.setBounds(270, 150, 150, 30);

    }

    public void addComponent() {
        add(usernameLabel);
        add(username);
        add(passwordLabel);
        add(password);
        add(login);
        add(client);

        login.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                verifyUser();
            }
        });
        client.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserView uv = new UserView();
                uv.setVisible(true);
            }
        });

    }


    public void displayMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }

    public String getUsername() {
        return username.getText();
    }

    public char[] getPassword() {
        return password.getPassword();
    }

    public void verifyUser() {
        String username = getUsername();
        String password = String.valueOf(getPassword());

        String type = loginPresenter.verifyUser(username, password);
        if (type == null) {
            JOptionPane.showMessageDialog(this, "Wrong username or password!");
        } else {
            switch (Objects.requireNonNull(type)) {
                case "EMPLOYEE" -> {
                    EmployeeView employeeView = new EmployeeView();
                    employeeView.setVisible(true);
                }
                case "ADMIN" -> {
                    AdminView adminView = new AdminView();
                    adminView.setVisible(true);
                }
            }
        }
    }

}

