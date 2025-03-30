package org.example.medcareappointmentmanager.presentation.controller;


import org.example.medcareappointmentmanager.business.dto.UserDTO;
import org.example.medcareappointmentmanager.presentation.WindowManager;
import org.example.medcareappointmentmanager.presentation.view.ReceptionistAppointmentManagementPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;

@Component
public class ReceptionistAppointmentManagementController extends AbstractController {
    private ReceptionistAppointmentManagementPanel panel;

    @Autowired
    public ReceptionistAppointmentManagementController(WindowManager windowManager) {
        super(windowManager);
    }

    @Override
    public void setUser(UserDTO user) {
        this.user = user;
        this.panel = new ReceptionistAppointmentManagementPanel(this);
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
