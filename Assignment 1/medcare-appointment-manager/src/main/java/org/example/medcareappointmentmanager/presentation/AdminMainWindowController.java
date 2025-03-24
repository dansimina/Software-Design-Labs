package org.example.medcareappointmentmanager.presentation;

import org.example.medcareappointmentmanager.business.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

@Component
public class AdminMainWindowController implements ActionListener {
    public final WindowManager windowManager;

    UserDTO user;

    private JPanel panel;

    @Autowired
    public AdminMainWindowController(WindowManager windowManager) {
        this.windowManager = windowManager;
    }

    private void buildUI() {
        panel = new JPanel();
        Label infoLabel = new Label();

        assert user != null;
        infoLabel.setText(user.toString());

        panel.add(infoLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    public JPanel getPanel() {
        if(panel == null) {
            buildUI();
        }
        return panel;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
