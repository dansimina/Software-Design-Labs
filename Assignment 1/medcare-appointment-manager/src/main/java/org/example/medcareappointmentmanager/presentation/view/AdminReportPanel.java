package org.example.medcareappointmentmanager.presentation.view;

import org.example.medcareappointmentmanager.business.dto.AppointmentDTO;
import org.example.medcareappointmentmanager.business.dto.DoctorDTO;
import org.example.medcareappointmentmanager.business.dto.DoctorReportDTO;
import org.example.medcareappointmentmanager.business.dto.MedicalServiceReportDTO;
import org.example.medcareappointmentmanager.presentation.controller.AdminReportController;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Properties;

public class AdminReportPanel extends AbstractPanel {
    private final AdminReportController controller;
    private JTable appointmentsTable;
    private JTable doctorReportTable;
    private JTable serviceReportTable;
    private JDatePickerImpl startDatePicker;
    private JDatePickerImpl endDatePicker;

    public AdminReportPanel(AdminReportController controller) {
        this.controller = controller;
        buildUI();
    }

    private void buildUI() {
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        datePanel.setBackground(BACKGROUND_COLOR);

        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");

        SqlDateModel startModel = new SqlDateModel();
        SqlDateModel endModel = new SqlDateModel();
        JDatePanelImpl startDatePanel = new JDatePanelImpl(startModel, p);
        JDatePanelImpl endDatePanel = new JDatePanelImpl(endModel, p);

        startDatePicker = new JDatePickerImpl(startDatePanel, new DateComponentFormatter());
        endDatePicker = new JDatePickerImpl(endDatePanel, new DateComponentFormatter());

        datePanel.add(new JLabel("Start Date:"));
        datePanel.add(startDatePicker);
        datePanel.add(new JLabel("End Date:"));
        datePanel.add(endDatePicker);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        JButton generateButton = createStyledButton("Generate");
        JButton exportButton = createStyledButton("Export");
        JButton backButton = createStyledButton("Back");

        generateButton.setActionCommand("generate");
        exportButton.setActionCommand("export");
        backButton.setActionCommand("back");

        generateButton.addActionListener(controller);
        exportButton.addActionListener(controller);
        backButton.addActionListener(controller);

        buttonPanel.add(backButton);
        buttonPanel.add(generateButton);
        buttonPanel.add(exportButton);

        appointmentsTable = new JTable(new DefaultTableModel(
                new Object[][] {},
                new String[] {"ID", "Patient", "DoctorId", "Doctor", "ServiceId", "Service", "Date", "Time", "Status"}
        ));
        JScrollPane appointmentScrollPane = new JScrollPane(appointmentsTable);
        appointmentScrollPane.setPreferredSize(new Dimension(600, 300));

        doctorReportTable = new JTable(new DefaultTableModel(
                new Object[][] {},
                new String[] {"Doctor ID", "Doctor Name", "No. of Appointments"}
        ));
        serviceReportTable = new JTable(new DefaultTableModel(
                new Object[][] {},
                new String[] {"Service ID", "Service Name", "No. of Appointments"}
        ));

        JScrollPane doctorScrollPane = new JScrollPane(doctorReportTable);
        doctorScrollPane.setPreferredSize(new Dimension(300, 300));
        JScrollPane serviceScrollPane = new JScrollPane(serviceReportTable);
        serviceScrollPane.setPreferredSize(new Dimension(300, 300));

        JPanel reportPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        reportPanel.setBackground(BACKGROUND_COLOR);
        reportPanel.add(doctorScrollPane);
        reportPanel.add(serviceScrollPane);

        JPanel southWrapper = new JPanel(new BorderLayout(10, 10));
        southWrapper.setBackground(BACKGROUND_COLOR);
        southWrapper.add(reportPanel, BorderLayout.CENTER);
        southWrapper.add(buttonPanel, BorderLayout.SOUTH);

        add(datePanel, BorderLayout.NORTH);
        add(appointmentScrollPane, BorderLayout.CENTER);
        add(southWrapper, BorderLayout.SOUTH);
    }

    public void updateTableAppointments(List<AppointmentDTO> appointments) {
        DefaultTableModel model = (DefaultTableModel) appointmentsTable.getModel();
        model.setRowCount(0);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (AppointmentDTO appointment : appointments) {
            model.addRow(new Object[] {
                    appointment.id(),
                    appointment.patientName(),
                    appointment.doctor().id(),
                    appointment.doctor().name(),
                    appointment.medicalService().id(),
                    appointment.medicalService().name(),
                    appointment.date(),
                    appointment.time().toString(),
                    appointment.status().name().toLowerCase()
            });
        }
    }

    public void updateTableDoctors(List<DoctorReportDTO> doctors) {
        DefaultTableModel model = (DefaultTableModel) doctorReportTable.getModel();
        model.setRowCount(0);

        for (DoctorReportDTO doctor : doctors) {
            model.addRow(new Object[] {
                    doctor.id(),
                    doctor.name(),
                    doctor.noOfAppointments()
            });
        }
    }

    public void updateTableMedicalServices(List<MedicalServiceReportDTO> medicalServices) {
        DefaultTableModel model = (DefaultTableModel) serviceReportTable.getModel();
        model.setRowCount(0);

        for(MedicalServiceReportDTO medicalService : medicalServices) {
            model.addRow(new Object[] {
                    medicalService.id(),
                    medicalService.name(),
                    medicalService.noOfAppointments()
            });
        }
    }

    public LocalDate getStartDate() {
        if (!startDatePicker.getModel().isSelected()) {
            return null;
        }

        int year = startDatePicker.getModel().getYear();
        int month = startDatePicker.getModel().getMonth();  // zero-based!
        int day = startDatePicker.getModel().getDay();

        return LocalDate.of(year, month + 1, day);
    }

    public LocalDate getEndDate() {
        if (!endDatePicker.getModel().isSelected()) {
            return null;
        }

        int year = endDatePicker.getModel().getYear();
        int month = endDatePicker.getModel().getMonth();  // zero-based!
        int day = endDatePicker.getModel().getDay();

        return LocalDate.of(year, month + 1, day);
    }

}
