package org.example.medcareappointmentmanager.presentation.controller;


import org.example.medcareappointmentmanager.business.dto.CreateAppointmentDTO;
import org.example.medcareappointmentmanager.business.dto.UserDTO;
import org.example.medcareappointmentmanager.business.service.AppointmentService;
import org.example.medcareappointmentmanager.business.service.DoctorService;
import org.example.medcareappointmentmanager.business.service.MedicalServiceService;
import org.example.medcareappointmentmanager.presentation.WindowManager;
import org.example.medcareappointmentmanager.presentation.view.ReceptionistAppointmentManagementPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;

@Component
public class ReceptionistAppointmentManagementController extends AbstractController {
    private DoctorService doctorService;
    private MedicalServiceService medicalServiceService;
    private AppointmentService appointmentService;
    private ReceptionistAppointmentManagementPanel panel;

    @Autowired
    public ReceptionistAppointmentManagementController(WindowManager windowManager, DoctorService doctorService, MedicalServiceService medicalServiceService, AppointmentService appointmentService) {
        super(windowManager);
        this.doctorService = doctorService;
        this.medicalServiceService = medicalServiceService;
        this.appointmentService = appointmentService;
    }

    @Override
    public void setUser(UserDTO user) {
        this.user = user;
        this.panel = new ReceptionistAppointmentManagementPanel(this);
        this.panel.updateTable(doctorService.findAll(), medicalServiceService.findAll());
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
                try{
                    appointmentService.save(new CreateAppointmentDTO(null, panel.getPatientName(), panel.getDoctorIdLabel(), panel.getServiceIdLabel(), panel.getSelectedDate(), panel.getSelectedHour(), panel.getSelectedStatus()));
                    this.panel.setError("Success");
                }
                catch (Exception ex){
                    this.panel.setError(ex.getMessage());
                }
            }
        }
    }
}
