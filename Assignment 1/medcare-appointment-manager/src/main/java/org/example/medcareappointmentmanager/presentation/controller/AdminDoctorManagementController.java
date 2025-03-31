package org.example.medcareappointmentmanager.presentation.controller;

import org.example.medcareappointmentmanager.business.dto.DoctorDTO;
import org.example.medcareappointmentmanager.business.dto.UserDTO;
import org.example.medcareappointmentmanager.business.service.DoctorService;
import org.example.medcareappointmentmanager.presentation.WindowManager;
import org.example.medcareappointmentmanager.presentation.view.AdminDoctorManagementPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;

@Component
public class AdminDoctorManagementController extends AbstractController {
    DoctorService doctorService;
    AdminDoctorManagementPanel panel;

    @Autowired
    public AdminDoctorManagementController(WindowManager windowManager, DoctorService doctorService) {
        super(windowManager);
        this.doctorService = doctorService;
    }

    @Override
    public void setUser(UserDTO user) {
        this.user = user;
        this.panel = new AdminDoctorManagementPanel(this);
        this.panel.updateTable(doctorService.findAll());
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
                try {
                    doctorService.save(new DoctorDTO(panel.getIdLabel(), panel.getDoctorName(), panel.getSpecialization(), panel.getStartHour(), panel.getEndHour()));
                    this.panel.clearError();
                    this.panel.clearFields();
                    this.panel.updateTable(doctorService.findAll());
                }
                catch (Exception ex) {
                    panel.setError(ex.getMessage());
                }
            }
            case "delete" -> {
                try {
                    doctorService.delete(new DoctorDTO(panel.getIdLabel(), panel.getDoctorName(), panel.getSpecialization(), panel.getStartHour(), panel.getEndHour()));
                    this.panel.clearError();
                    this.panel.clearFields();
                    this.panel.updateTable(doctorService.findAll());
                }
                catch (Exception ex) {
                    panel.setError(ex.getMessage());
                }
            }
            case "clear" -> this.panel.clearFields();
        }
    }
}
