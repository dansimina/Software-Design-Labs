package org.example.medcareappointmentmanager.presentation.controller;

import org.example.medcareappointmentmanager.business.dto.CreateUserDTO;
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
    private final UserService userService;

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
                try {
                    userService.save(new CreateUserDTO(panel.getNameField(), panel.getUsernameField(), panel.getPasswordField()), "receptionist");
                    this.panel.clearError();
                    this.panel.updateTable(userService.getByType("receptionist"));
                }
                catch(Exception ex) {
                    this.panel.setError(ex.getMessage());
                }
            }
        }
    }

    @Override
    public void setUser(UserDTO user) {
        this.user = user;
        this.panel = new AdminReceptionistManagementPanel(this);
        this.panel.updateTable(userService.getByType("receptionist"));
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
}
