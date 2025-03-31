package org.example.medcareappointmentmanager.presentation.view;

import org.example.medcareappointmentmanager.presentation.controller.AdminReportController;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;


import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
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
                new String[] {"ID", "Patient", "Doctor", "Service", "Date", "Time", "Status"}
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

    public LocalDate getStartDate() {
        Date selectedDate = (Date) startDatePicker.getModel().getValue();

        if (selectedDate == null) {
            return null;
        }

        return selectedDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    public LocalDate getEndDate() {
        Date selectedDate = (Date) endDatePicker.getModel().getValue();

        if (selectedDate == null) {
            return null;
        }

        return selectedDate.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

}
