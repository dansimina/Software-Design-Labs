package org.example.medcareappointmentmanager.presentation.controller;

import org.example.medcareappointmentmanager.business.dto.UserDTO;
import org.example.medcareappointmentmanager.presentation.WindowManager;
import org.example.medcareappointmentmanager.presentation.view.AdminMedicalServicesManagementPanel;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;

@Component
public class AdminMedicalServicesManagementController extends AbstractController {
    private AdminMedicalServicesManagementPanel panel;

    public AdminMedicalServicesManagementController(WindowManager windowManager) {
        super(windowManager);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }

    @Override
    public void setUser(UserDTO user) {
        this.user = user;
        this.panel = new AdminMedicalServicesManagementPanel(this);
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
}
