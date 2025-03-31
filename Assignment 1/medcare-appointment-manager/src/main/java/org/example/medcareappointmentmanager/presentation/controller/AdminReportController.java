package org.example.medcareappointmentmanager.presentation.controller;

import org.example.medcareappointmentmanager.business.dto.UserDTO;
import org.example.medcareappointmentmanager.business.service.ReportService;
import org.example.medcareappointmentmanager.presentation.WindowManager;
import org.example.medcareappointmentmanager.presentation.view.AdminReportPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import java.awt.event.ActionEvent;

@Controller
public class AdminReportController extends AbstractController {
    @Autowired
    private ReportService reportService;

    private AdminReportPanel panel;

    public AdminReportController(WindowManager windowManager, ReportService reportService) {
        super(windowManager);
        this.reportService = reportService;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "back" -> windowManager.showMainWindow(user);
            case "generate" -> {
                panel.updateTableAppointments(reportService.getAppointmentsReport(panel.getStartDate(), panel.getEndDate()));
                panel.updateTableDoctors(reportService.getDoctorsReport(panel.getStartDate(), panel.getEndDate()));
                panel.updateTableMedicalServices(reportService.getMedicalServicesReport(panel.getStartDate(), panel.getEndDate()));
            }
            case "export" -> {}
        }
    }

    @Override
    public void setUser(UserDTO user) {
        this.user = user;
        this.panel = new AdminReportPanel(this);
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }
}
