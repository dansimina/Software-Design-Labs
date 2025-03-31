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
    private JLabel patientLabel;
    private JLabel doctorLabel;
    private JLabel serviceLabel;
    private JLabel dateLabel;
    private JLabel hourLabel;
    private JLabel statusLabel;

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

        // Combinăm statusPanel și scrollPane într-un topPanel
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(BACKGROUND_COLOR);
        topPanel.add(statusPanel, BorderLayout.NORTH);
        topPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel cu detalii rând selectat
        JPanel detailPanel = new JPanel(new GridLayout(2, 6, 10, 5));
        detailPanel.setBackground(BACKGROUND_COLOR);
        detailPanel.setBorder(BorderFactory.createTitledBorder("Selected Appointment Details"));

        JLabel patientTitle = new JLabel("Patient:");
        JLabel doctorTitle = new JLabel("Doctor:");
        JLabel serviceTitle = new JLabel("Service:");
        JLabel dateTitle = new JLabel("Date:");
        JLabel hourTitle = new JLabel("Hour:");
        JLabel statusTitle = new JLabel("Status:");

        patientLabel = new JLabel("-");
        doctorLabel = new JLabel("-");
        serviceLabel = new JLabel("-");
        dateLabel = new JLabel("-");
        hourLabel = new JLabel("-");
        statusLabel = new JLabel("-");

        JLabel[] titles = {patientTitle, doctorTitle, serviceTitle, dateTitle, hourTitle, statusTitle};
        JLabel[] values = {patientLabel, doctorLabel, serviceLabel, dateLabel, hourLabel, statusLabel};

        for (int i = 0; i < titles.length; i++) {
            titles[i].setFont(LABEL_FONT);
            values[i].setFont(LABEL_FONT);
            detailPanel.add(titles[i]);
            detailPanel.add(values[i]);
        }

        onSelecting();

        saveButton = createStyledButton("Save");
        backButton = createStyledButton("Back");

        saveButton.setActionCommand("save");
        backButton.setActionCommand("back");

        saveButton.addActionListener(controller);
        backButton.addActionListener(controller);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.add(backButton);
        buttonPanel.add(saveButton);

        add(topPanel, BorderLayout.NORTH);
        add(detailPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void onSelecting() {
        appointmentTable.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;
            int row = appointmentTable.getSelectedRow();
            if (row != -1) {
                patientLabel.setText(appointmentTable.getValueAt(row, 0).toString());
                doctorLabel.setText(appointmentTable.getValueAt(row, 1).toString());
                serviceLabel.setText(appointmentTable.getValueAt(row, 2).toString());
                dateLabel.setText(appointmentTable.getValueAt(row, 3).toString());
                hourLabel.setText(appointmentTable.getValueAt(row, 4).toString());
                statusLabel.setText(appointmentTable.getValueAt(row, 5).toString());
            } else {
                patientLabel.setText("-");
                doctorLabel.setText("-");
                serviceLabel.setText("-");
                dateLabel.setText("-");
                hourLabel.setText("-");
                statusLabel.setText("-");
            }
        });
    }


    public void updateTable(List<AppointmentDTO> appointments) {
        DefaultTableModel model = (DefaultTableModel) appointmentTable.getModel();
        model.setRowCount(0);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        for (AppointmentDTO a : appointments) {
            model.addRow(new Object[] {
                    a.patientName(),
                    a.doctor().name(),
                    a.medicalService().name(),
                    a.date(),
                    a.time().toString(),
                    a.status().name().toLowerCase()
            });
        }
    }



    public String getSelectedStatus() {
        return (String) statusComboBox.getSelectedItem();
    }

    public int getSelectedRowIndex() {
        return appointmentTable.getSelectedRow();
    }
}
