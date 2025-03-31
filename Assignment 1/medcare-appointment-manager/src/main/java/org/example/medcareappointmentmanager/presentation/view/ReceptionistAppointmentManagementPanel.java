package org.example.medcareappointmentmanager.presentation.view;

import org.example.medcareappointmentmanager.business.dto.DoctorDTO;
import org.example.medcareappointmentmanager.business.dto.MedicalServiceDTO;
import org.example.medcareappointmentmanager.presentation.controller.ReceptionistAppointmentManagementController;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.sql.Time;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Properties;

public class ReceptionistAppointmentManagementPanel extends AbstractPanel {
    private final ReceptionistAppointmentManagementController controller;
    private JTable doctorTable;
    private JTable serviceTable;
    private JTextField patientNameField;
    private JComboBox<String> hourComboBox;
    private JComboBox<String> statusComboBox;
    private JDatePickerImpl datePicker;
    private JLabel doctorIdLabel;
    private JLabel doctorNameLabel;
    private JLabel doctorSpecLabel;
    private JLabel doctorStartLabel;
    private JLabel doctorEndLabel;
    private JLabel serviceIdLabel;
    private JLabel serviceNameLabel;
    private JLabel serviceDurationLabel;
    private JLabel servicePriceLabel;
    private JLabel errorLabel;

    public ReceptionistAppointmentManagementPanel(ReceptionistAppointmentManagementController controller) {
        this.controller = controller;
        buildUI();
    }

    private void buildUI() {
        doctorTable = new JTable(new DefaultTableModel(new Object[][] {}, new String[] {"Id", "Doctor Name", "Specialization", "Start Hour", "End Hour"}) {
            public boolean isCellEditable(int row, int column) { return false; }
        });

        serviceTable = new JTable(new DefaultTableModel(new Object[][] {}, new String[] {"Id", "Service Name", "Duration", "Price"}) {
            public boolean isCellEditable(int row, int column) { return false; }
        });

        JScrollPane doctorScroll = new JScrollPane(doctorTable);
        doctorScroll.setPreferredSize(new Dimension(300, 350));
        JScrollPane serviceScroll = new JScrollPane(serviceTable);
        serviceScroll.setPreferredSize(new Dimension(300, 350));

        JPanel tablesPanel = new JPanel(new GridLayout(1, 2, 20, 0));
        tablesPanel.setBackground(BACKGROUND_COLOR);
        tablesPanel.add(doctorScroll);
        tablesPanel.add(serviceScroll);

        JPanel infoPanel = new JPanel(new GridLayout(5, 2, 10, 10));
        infoPanel.setBackground(BACKGROUND_COLOR);

        doctorIdLabel = new JLabel("Doctor ID: -");
        doctorNameLabel = new JLabel("Name: -");
        doctorSpecLabel = new JLabel("Specialization: -");
        doctorStartLabel = new JLabel("Start Hour: -");
        doctorEndLabel = new JLabel("End Hour: -");

        serviceIdLabel = new JLabel("Service ID: -");
        serviceNameLabel = new JLabel("Service: -");
        serviceDurationLabel = new JLabel("Duration: -");
        servicePriceLabel = new JLabel("Price: -");

        JLabel[] allLabels = {
                doctorIdLabel, doctorNameLabel,
                doctorSpecLabel, doctorStartLabel,
                doctorEndLabel, serviceIdLabel,
                serviceNameLabel, serviceDurationLabel,
                servicePriceLabel
        };

        for (JLabel label : allLabels) {
            label.setFont(LABEL_FONT);
            infoPanel.add(label);
        }

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBackground(BACKGROUND_COLOR);

        patientNameField = new JTextField();
        hourComboBox = new JComboBox<>();
        for (int i = 8; i <= 18; i++) {
            hourComboBox.addItem(String.format("%02d:00", i));
            hourComboBox.addItem(String.format("%02d:30", i));
        }

        statusComboBox = new JComboBox<>(new String[] {"new", "ongoing", "completed"});

        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());

        formPanel.add(new JLabel("Patient Name:", JLabel.RIGHT)); formPanel.add(patientNameField);
        formPanel.add(new JLabel("Appointment Hour:", JLabel.RIGHT)); formPanel.add(hourComboBox);
        formPanel.add(new JLabel("Appointment Date:", JLabel.RIGHT)); formPanel.add(datePicker);
        formPanel.add(new JLabel("Appointment Status:", JLabel.RIGHT)); formPanel.add(statusComboBox);

        errorLabel = new JLabel(" ");
        errorLabel.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);

        JPanel centerPanel = new JPanel(new BorderLayout(20, 0));
        centerPanel.setBackground(BACKGROUND_COLOR);
        centerPanel.add(infoPanel, BorderLayout.WEST);
        centerPanel.add(formPanel, BorderLayout.CENTER);

        JPanel errorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        errorPanel.setBackground(BACKGROUND_COLOR);
        errorPanel.add(errorLabel);

        centerPanel.add(errorPanel, BorderLayout.SOUTH);

        JButton back = createStyledButton("Back");
        JButton save = createStyledButton("Save");
        back.setActionCommand("back");
        save.setActionCommand("save");
        back.addActionListener(controller);
        save.addActionListener(controller);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.add(back);
        buttonPanel.add(save);

        add(tablesPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        onSelectingDoctor();
        onSelectingService();
    }


    private void onSelectingDoctor() {
        doctorTable.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;

            int row = doctorTable.getSelectedRow();
            if (row == -1) {
                doctorIdLabel.setText("Doctor ID: -");
                doctorNameLabel.setText("Name: -");
                doctorSpecLabel.setText("Specialization: -");
                doctorStartLabel.setText("Start Hour: -");
                doctorEndLabel.setText("End Hour: -");
                return;
            }

            doctorIdLabel.setText("Doctor ID: " + doctorTable.getValueAt(row, 0).toString());
            doctorNameLabel.setText("Name: " + doctorTable.getValueAt(row, 1).toString());
            doctorSpecLabel.setText("Specialization: " + doctorTable.getValueAt(row, 2).toString());
            doctorStartLabel.setText("Start Hour: " + doctorTable.getValueAt(row, 3).toString());
            doctorEndLabel.setText("End Hour: " + doctorTable.getValueAt(row, 4).toString());
        });
    }

    private void onSelectingService() {
        serviceTable.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;

            int row = serviceTable.getSelectedRow();
            if (row == -1) {
                serviceIdLabel.setText("Service ID: -");
                serviceNameLabel.setText("Service: -");
                serviceDurationLabel.setText("Duration: -");
                servicePriceLabel.setText("Price: -");
                return;
            }

            serviceIdLabel.setText("Service ID: " + serviceTable.getValueAt(row, 0).toString());
            serviceNameLabel.setText("Service: " + serviceTable.getValueAt(row, 1).toString());
            serviceDurationLabel.setText("Duration: " + serviceTable.getValueAt(row, 2).toString());
            servicePriceLabel.setText("Price: " + serviceTable.getValueAt(row, 3).toString());
        });
    }

    public void updateTable(List<DoctorDTO> doctors, List<MedicalServiceDTO> services) {
        updateDoctorsTable(doctors);
        updateMedicalServiceTable(services);
    }

    private void updateDoctorsTable(List<DoctorDTO> doctors) {
        DefaultTableModel model = (DefaultTableModel) doctorTable.getModel();
        model.setRowCount(0);

        for(DoctorDTO doctor : doctors) {
            model.addRow(new Object[]{
                    doctor.id(),
                    doctor.name(),
                    doctor.specialization(),
                    doctor.startOfProgram().toString(),
                    doctor.endOfProgram().toString()
            });
        }
    }

    private void updateMedicalServiceTable(List<MedicalServiceDTO> services) {
        DefaultTableModel model = (DefaultTableModel) serviceTable.getModel();
        model.setRowCount(0);

        for (MedicalServiceDTO service : services) {
            model.addRow(new Object[]{
                    service.id(),
                    service.name(),
                    service.price(),
                    service.duration()
            });
        }
    }

    public void setError(String error) {
        errorLabel.setVisible(true);
        errorLabel.setText(error);
    }

    public void clearError() {
        errorLabel.setVisible(false);
    }

    public String getPatientName() {
        return patientNameField.getText();
    }

    public Time getSelectedHour() {
        return Time.valueOf(LocalTime.parse((String) Objects.requireNonNull(hourComboBox.getSelectedItem())));
    }

    public Date getSelectedDate() {
        return (Date) datePicker.getModel().getValue();
    }

    public String getSelectedStatus() {
        return (String) statusComboBox.getSelectedItem();
    }

    public Long getDoctorIdLabel() {
        return Long.parseLong(doctorIdLabel.getText());
    }

    public Long getServiceIdLabel() {
        return Long.parseLong(serviceIdLabel.getText());
    }
}
