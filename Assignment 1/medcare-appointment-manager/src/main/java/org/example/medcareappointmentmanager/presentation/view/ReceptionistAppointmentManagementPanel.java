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
    private JLabel doctorIdValueLabel;
    private JLabel doctorNameValueLabel;
    private JLabel doctorSpecValueLabel;
    private JLabel doctorStartValueLabel;
    private JLabel doctorEndValueLabel;
    private JLabel serviceIdValueLabel;
    private JLabel serviceNameValueLabel;
    private JLabel serviceDurationValueLabel;
    private JLabel servicePriceValueLabel;
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

        JPanel infoPanel = new JPanel(new GridLayout(5, 4, 10, 5));
        infoPanel.setBackground(BACKGROUND_COLOR);

        JLabel doctorIdTitle = new JLabel("Doctor ID:"); doctorIdValueLabel = new JLabel("-");
        JLabel serviceIdTitle = new JLabel("Service ID:"); serviceIdValueLabel = new JLabel("-");
        JLabel doctorNameTitle = new JLabel("Name:"); doctorNameValueLabel = new JLabel("-");
        JLabel serviceNameTitle = new JLabel("Service:"); serviceNameValueLabel = new JLabel("-");
        JLabel doctorSpecTitle = new JLabel("Specialization:"); doctorSpecValueLabel = new JLabel("-");
        JLabel serviceDurationTitle = new JLabel("Duration:"); serviceDurationValueLabel = new JLabel("-");
        JLabel doctorStartTitle = new JLabel("Start Hour:"); doctorStartValueLabel = new JLabel("-");
        JLabel servicePriceTitle = new JLabel("Price:"); servicePriceValueLabel = new JLabel("-");
        JLabel doctorEndTitle = new JLabel("End Hour:"); doctorEndValueLabel = new JLabel("-");

        JLabel[] labels = {
                doctorIdTitle, doctorIdValueLabel, serviceIdTitle, serviceIdValueLabel,
                doctorNameTitle, doctorNameValueLabel, serviceNameTitle, serviceNameValueLabel,
                doctorSpecTitle, doctorSpecValueLabel, serviceDurationTitle, serviceDurationValueLabel,
                doctorStartTitle, doctorStartValueLabel, servicePriceTitle, servicePriceValueLabel,
                doctorEndTitle, doctorEndValueLabel
        };

        for (JLabel label : labels) {
            label.setFont(LABEL_FONT);
            infoPanel.add(label);
        }

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;

        errorLabel = new JLabel(" ");
        errorLabel.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);
        formPanel.add(errorLabel, gbc);

        gbc.gridwidth = 1;
        gbc.gridy++;
        formPanel.add(new JLabel("Patient Name:", JLabel.RIGHT), gbc);
        gbc.gridx = 1;
        patientNameField = new JTextField();
        formPanel.add(patientNameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Appointment Hour:", JLabel.RIGHT), gbc);
        gbc.gridx = 1;
        hourComboBox = new JComboBox<>();
        for (int i = 8; i <= 18; i++) {
            hourComboBox.addItem(String.format("%02d:00", i));
            hourComboBox.addItem(String.format("%02d:30", i));
        }
        formPanel.add(hourComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Appointment Date:", JLabel.RIGHT), gbc);
        gbc.gridx = 1;
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        datePicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());
        formPanel.add(datePicker, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        formPanel.add(new JLabel("Appointment Status:", JLabel.RIGHT), gbc);
        gbc.gridx = 1;
        statusComboBox = new JComboBox<>(new String[] {"new", "ongoing", "completed"});
        formPanel.add(statusComboBox, gbc);

        JPanel centerPanel = new JPanel(new BorderLayout(20, 0));
        centerPanel.setBackground(BACKGROUND_COLOR);
        centerPanel.add(infoPanel, BorderLayout.WEST);
        centerPanel.add(formPanel, BorderLayout.CENTER);

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
                doctorIdValueLabel.setText("-");
                doctorNameValueLabel.setText("-");
                doctorSpecValueLabel.setText("-");
                doctorStartValueLabel.setText("-");
                doctorEndValueLabel.setText("-");
                return;
            }
            doctorIdValueLabel.setText(doctorTable.getValueAt(row, 0).toString());
            doctorNameValueLabel.setText(doctorTable.getValueAt(row, 1).toString());
            doctorSpecValueLabel.setText(doctorTable.getValueAt(row, 2).toString());
            doctorStartValueLabel.setText(doctorTable.getValueAt(row, 3).toString());
            doctorEndValueLabel.setText(doctorTable.getValueAt(row, 4).toString());
        });
    }

    private void onSelectingService() {
        serviceTable.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            int row = serviceTable.getSelectedRow();
            if (row == -1) {
                serviceIdValueLabel.setText("-");
                serviceNameValueLabel.setText("-");
                serviceDurationValueLabel.setText("-");
                servicePriceValueLabel.setText("-");
                return;
            }
            serviceIdValueLabel.setText(serviceTable.getValueAt(row, 0).toString());
            serviceNameValueLabel.setText(serviceTable.getValueAt(row, 1).toString());
            serviceDurationValueLabel.setText(serviceTable.getValueAt(row, 2).toString());
            servicePriceValueLabel.setText(serviceTable.getValueAt(row, 3).toString());
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
                    doctor.id(), doctor.name(), doctor.specialization(),
                    doctor.startOfProgram().toString(), doctor.endOfProgram().toString()
            });
        }
    }

    private void updateMedicalServiceTable(List<MedicalServiceDTO> services) {
        DefaultTableModel model = (DefaultTableModel) serviceTable.getModel();
        model.setRowCount(0);
        for (MedicalServiceDTO service : services) {
            model.addRow(new Object[]{
                    service.id(), service.name(), service.price(), service.duration()
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
        return Long.parseLong(doctorIdValueLabel.getText());
    }

    public Long getServiceIdLabel() {
        return Long.parseLong(serviceIdValueLabel.getText());
    }
}
