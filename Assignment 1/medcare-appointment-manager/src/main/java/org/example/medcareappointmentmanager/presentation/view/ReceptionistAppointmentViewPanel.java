package org.example.medcareappointmentmanager.presentation.view;

import org.example.medcareappointmentmanager.business.dto.AppointmentDTO;
import org.example.medcareappointmentmanager.presentation.controller.ReceptionistAppointmentViewController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ReceptionistAppointmentViewPanel extends AbstractPanel {
    private final ReceptionistAppointmentViewController controller;
    private JTable appointmentTable;
    private JComboBox<String> statusComboBox;
    private JButton saveButton;
    private JButton backButton;

    public ReceptionistAppointmentViewPanel(ReceptionistAppointmentViewController controller) {
        this.controller = controller;
        buildUI();
    }

    protected void buildUI() {
        appointmentTable = new JTable(new DefaultTableModel(new Object[][] {}, new String[] {
                "Patient", "Doctor", "Service", "Date", "Hour", "Status"
        }) {
            public boolean isCellEditable(int row, int column) { return false; }
        });

        appointmentTable.setRowHeight(24);
        appointmentTable.setFont(LABEL_FONT);
        appointmentTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(appointmentTable);

        statusComboBox = new JComboBox<>(new String[] { "new", "ongoing", "completed" });

        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBackground(BACKGROUND_COLOR);
        statusPanel.add(new JLabel("Update Status:"));
        statusPanel.add(statusComboBox);

        saveButton = createStyledButton("Save");
        backButton = createStyledButton("Back");

        saveButton.setActionCommand("saveStatus");
        backButton.setActionCommand("back");

        saveButton.addActionListener(controller);
        backButton.addActionListener(controller);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.add(backButton);
        buttonPanel.add(saveButton);

        add(scrollPane, BorderLayout.CENTER);
        add(statusPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public JTable getAppointmentTable() {
        return appointmentTable;
    }

    public String getSelectedStatus() {
        return (String) statusComboBox.getSelectedItem();
    }

    public int getSelectedRowIndex() {
        return appointmentTable.getSelectedRow();
    }

    public void populateTable(List<AppointmentDTO> appointments) {
//        DefaultTableModel model = (DefaultTableModel) appointmentTable.getModel();
//        model.setRowCount(0); // clear table
//
//        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
//
//        for (AppointmentDTO a : appointments) {
//            model.addRow(new Object[] {
//                    a.patientName(),
//                    a.doctor().name(), // assuming DoctorDTO has name()
//                    a.medicalService().name(), // assuming MedicalServiceDTO has name()
//                    dateFormat.format(a.date()),
//                    a.time().toString(),
//                    a.status().name().toLowerCase()
//            });
//        }
    }
}
