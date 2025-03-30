package org.example.medcareappointmentmanager.presentation.controller;

import org.example.medcareappointmentmanager.business.dto.UserDTO;
import org.example.medcareappointmentmanager.presentation.WindowManager;
import org.example.medcareappointmentmanager.presentation.view.ReceptionistMainPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;

@Component
public class ReceptionstMainWindowController extends AbstractController {
    private ReceptionistMainPanel panel;

    @Autowired
    public ReceptionstMainWindowController(WindowManager windowManager) {
        super(windowManager);
    }

    @Override
    public void setUser(UserDTO user) {
        this.user = user;
        this.panel = new ReceptionistMainPanel(this, user);
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "registerAppointment" -> windowManager.showReceptionistAppointmentManagementWindow(user);
            case "viewAppointments" -> windowManager.showReceptionistAppointmentManagementWindow(user);
        }
    }
}
