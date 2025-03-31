package org.example.medcareappointmentmanager.presentation.controller;

import org.example.medcareappointmentmanager.business.dto.UserDTO;
import org.example.medcareappointmentmanager.presentation.WindowManager;
import org.example.medcareappointmentmanager.presentation.view.AdminMainWindowPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;

@Component
public class AdminMainWindowController extends AbstractController {
    private AdminMainWindowPanel panel;

    @Autowired
    public AdminMainWindowController(WindowManager windowManager) {
        super(windowManager);
    }

    @Override
    public void setUser(UserDTO user) {
        this.user = user;
        this.panel = new AdminMainWindowPanel(this, user);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "managereceptionist" -> windowManager.showAdminReceptionistManagementWindow(user);
            case "managedoctor" -> windowManager.showAdminDoctorManagementWindow(user);
            case "managemedicalservices" -> windowManager.showAdminMedicalServicesManagementWindow(user);
            case "generatereport" -> windowManager.showAdminReportManagementWindow(user);
            case "logout" -> windowManager.showAuthenticationWindow();
        }
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
}
