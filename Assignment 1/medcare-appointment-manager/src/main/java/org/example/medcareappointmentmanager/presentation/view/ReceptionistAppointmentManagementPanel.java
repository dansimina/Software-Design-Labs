package org.example.medcareappointmentmanager.presentation.view;

import org.example.medcareappointmentmanager.presentation.controller.ReceptionistAppointmentManagementController;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Properties;

public class ReceptionistAppointmentManagementPanel extends AbstractPanel {
    private final ReceptionistAppointmentManagementController controller;
    private JTable doctorTable;
    private JTable serviceTable;
    private JTextField patientNameField;
    private JComboBox<String> hourComboBox;
    private JComboBox<String> statusComboBox;
    private JDatePickerImpl datePicker;

    public ReceptionistAppointmentManagementPanel(ReceptionistAppointmentManagementController controller) {
        this.controller = controller;
        buildUI();
    }

    protected void buildUI() {
        doctorTable = new JTable(new DefaultTableModel(new Object[][] {}, new String[] {"Doctor Name", "Specialization"}) {
            public boolean isCellEditable(int row, int column) { return false; }
        });

        serviceTable = new JTable(new DefaultTableModel(new Object[][] {}, new String[] {"Service Name", "Duration", "Price"}) {
            public boolean isCellEditable(int row, int column) { return false; }
        });

        JScrollPane doctorScroll = new JScrollPane(doctorTable);
        JScrollPane serviceScroll = new JScrollPane(serviceTable);

        JPanel tablesPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        tablesPanel.setBackground(BACKGROUND_COLOR);
        tablesPanel.add(doctorScroll);
        tablesPanel.add(serviceScroll);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10)); // was 3 rows, now 4
        formPanel.setBackground(BACKGROUND_COLOR);

        patientNameField = new JTextField();

        hourComboBox = new JComboBox<>();
        for (int i = 8; i <= 18; i++) {
            hourComboBox.addItem(String.format("%02d:00", i));
            hourComboBox.addItem(String.format("%02d:30", i));
        }

        statusComboBox = new JComboBox<>(new String[] {
                "new", "ongoing", "completed"
        });

        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());

        formPanel.add(new JLabel("Patient Name:", JLabel.RIGHT));
        formPanel.add(patientNameField);
        formPanel.add(new JLabel("Appointment Hour:", JLabel.RIGHT));
        formPanel.add(hourComboBox);
        formPanel.add(new JLabel("Appointment Date:", JLabel.RIGHT));
        formPanel.add(datePicker);
        formPanel.add(new JLabel("Appointment Status:", JLabel.RIGHT));
        formPanel.add(statusComboBox);

        JButton back = createStyledButton("Back");
        JButton save = createStyledButton("Save");

        back.setActionCommand("back");
        save.setActionCommand("saveAppointment");

        back.addActionListener(controller);
        save.addActionListener(controller);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.add(back);
        buttonPanel.add(save);

        add(tablesPanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public JTable getDoctorTable() {
        return doctorTable;
    }

    public JTable getServiceTable() {
        return serviceTable;
    }

    public String getPatientName() {
        return patientNameField.getText();
    }

    public String getSelectedHour() {
        return (String) hourComboBox.getSelectedItem();
    }

    public java.util.Date getSelectedDate() {
        return (java.util.Date) datePicker.getModel().getValue();
    }

    public String getSelectedStatus() {
        return (String) statusComboBox.getSelectedItem();
    }
}
