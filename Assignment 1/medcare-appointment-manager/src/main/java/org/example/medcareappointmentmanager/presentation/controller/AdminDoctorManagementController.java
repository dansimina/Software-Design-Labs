package org.example.medcareappointmentmanager.presentation.controller;

import org.example.medcareappointmentmanager.business.dto.UserDTO;
import org.example.medcareappointmentmanager.presentation.WindowManager;
import org.example.medcareappointmentmanager.presentation.view.AdminDoctorManagementPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;

@Component
public class AdminDoctorManagementController extends AbstractController {
    AdminDoctorManagementPanel panel;

    @Autowired
    public AdminDoctorManagementController(WindowManager windowManager) {
        super(windowManager);
    }

    @Override
    public void setUser(UserDTO user) {
        this.user = user;
        this.panel = new AdminDoctorManagementPanel(this);
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "back" -> windowManager.showMainWindow(user);
            case "save" -> {
            }
            case "update" -> {
            }
        }
    }
}
