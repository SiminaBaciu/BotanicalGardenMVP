package view;

import javax.swing.*;
import java.awt.event.ActionListener;

public interface UserInterface {
    public void addFilterListener(ActionListener a);

    public String getZone();

    public String getCarnivore();

    public String getTypeField();

    public String getSpeciesField();

    public JScrollPane getScrollPane();

    public String getNameField();
}