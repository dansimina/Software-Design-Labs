package org.example.medcareappointmentmanager.presentation.controller;

import org.example.medcareappointmentmanager.business.dto.UserDTO;
import org.example.medcareappointmentmanager.business.service.AppointmentService;
import org.example.medcareappointmentmanager.presentation.WindowManager;
import org.example.medcareappointmentmanager.presentation.view.ReceptionistAppointmentManagementPanel;
import org.example.medcareappointmentmanager.presentation.view.ReceptionistAppointmentViewPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;

@Component
public class ReceptionistAppointmentViewController extends AbstractController {
    private final AppointmentService appointmentService;
    private final WindowManager windowManager;
    private ReceptionistAppointmentViewPanel panel;

    @Autowired
    public ReceptionistAppointmentViewController(WindowManager windowManager, AppointmentService appointmentService) {
        super(windowManager);
        this.appointmentService = appointmentService;
        this.windowManager = windowManager;
    }

    @Override
    public void setUser(UserDTO user) {
        this.user = user;
        this.panel = new ReceptionistAppointmentViewPanel(this);
        this.panel.updateTable(appointmentService.findAll());
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
        }
    }
}
