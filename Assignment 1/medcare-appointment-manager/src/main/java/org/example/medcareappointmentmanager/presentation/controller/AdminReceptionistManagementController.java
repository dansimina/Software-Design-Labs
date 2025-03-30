package org.example.medcareappointmentmanager.presentation.controller;

import org.example.medcareappointmentmanager.business.dto.UserDTO;
import org.example.medcareappointmentmanager.business.service.UserService;
import org.example.medcareappointmentmanager.presentation.WindowManager;
import org.example.medcareappointmentmanager.presentation.view.AdminReceptionistManagementPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.List;

@Component
public class AdminReceptionistManagementController extends AbstractController {
    @Autowired
    private UserService userService;

    private AdminReceptionistManagementPanel panel;

    @Autowired
    public AdminReceptionistManagementController(WindowManager windowManager, UserService userService) {
        super(windowManager);
        this.userService = userService;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "back" -> windowManager.showMainWindow(user);
            case "save" -> {
                UserDTO newUser = userService.register(panel.getNewUser(), "receptionist");
                if(newUser != null) {
                    panel.clearError();
                    updateTable();
                }
                else {
                    panel.setError("ceva");
                }
            }
        }
    }

    private void updateTable() {
        List<UserDTO> receptionist = userService.getUsersByType("receptionist");
        panel.updateTable(receptionist);
    }

    @Override
    public void setUser(UserDTO user) {
        this.user = user;
        this.panel = new AdminReceptionistManagementPanel(this);
        updateTable();
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
}
