package org.example.medcareappointmentmanager.presentation.controller;

import org.example.medcareappointmentmanager.business.dto.UserDTO;
import org.example.medcareappointmentmanager.business.service.MedicalServiceService;
import org.example.medcareappointmentmanager.presentation.WindowManager;
import org.example.medcareappointmentmanager.presentation.view.AdminMedicalServicesManagementPanel;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;

@Component
public class AdminMedicalServicesManagementController extends AbstractController {
    private MedicalServiceService medicalServiceService;
    private AdminMedicalServicesManagementPanel panel;

    public AdminMedicalServicesManagementController(WindowManager windowManager, MedicalServiceService medicalServiceService) {
        super(windowManager);
        this.medicalServiceService = medicalServiceService;
    }

    @Override
    public void setUser(UserDTO user) {
        this.user = user;
        this.panel = new AdminMedicalServicesManagementPanel(this);
        this.panel.updateTable(medicalServiceService.findAll());
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
                    medicalServiceService.save(new MedicalServiceDTO(panel.getServiceId(), panel.getServiceName(), panel.getServicePrice(), panel.getServiceDuration()));
                    this.panel.clearError();
                    this.panel.clearFields();
                    this.panel.updateTable(medicalServiceService.findAll());
                }
                catch (Exception ex) {
                    this.panel.setError(ex.getMessage());
                }
            }
            case "clear" -> {
                this.panel.clearFields();
            }
            case "delete" -> {
                try {
                    medicalServiceService.delete(new MedicalServiceDTO(panel.getServiceId(), panel.getServiceName(), panel.getServicePrice(), panel.getServiceDuration()));
                    this.panel.clearError();
                    this.panel.clearFields();
                    this.panel.updateTable(medicalServiceService.findAll());
                }
                catch (Exception ex) {
                    this.panel.setError(ex.getMessage());
                }
            }
        }
    }
}
