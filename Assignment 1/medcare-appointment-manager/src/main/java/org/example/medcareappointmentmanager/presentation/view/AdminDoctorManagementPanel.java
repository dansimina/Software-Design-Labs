package org.example.medcareappointmentmanager.presentation.view;

import org.example.medcareappointmentmanager.business.dto.DoctorDTO;
import org.example.medcareappointmentmanager.presentation.controller.AdminDoctorManagementController;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Time;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

public class AdminDoctorManagementPanel extends AbstractPanel {
    private JLabel idLabel;
    private JTable doctorTable;
    private JTextField nameField;
    private JTextField specField;
    private JComboBox<String> startHourBox;
    private JComboBox<String> endHourBox;
    private JLabel errorLabel;
    private final AdminDoctorManagementController controller;

    public AdminDoctorManagementPanel(AdminDoctorManagementController controller) {
        this.controller = controller;
        buildUI();
    }

    private void buildUI() {
        DefaultTableModel model = new DefaultTableModel(new Object[][] {}, new String[] {"Id", "Name", "Specialization", "Start Hour", "End Hour"}) {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        doctorTable = new JTable(model);
        doctorTable.setRowHeight(24);
        doctorTable.setFont(LABEL_FONT);
        doctorTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        onSelecting();

        JScrollPane scrollPane = new JScrollPane(doctorTable);
        scrollPane.setPreferredSize(new Dimension(600, 250));

        JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        idPanel.setBackground(BACKGROUND_COLOR);
        JLabel idTitleLabel = new JLabel("Doctor ID:");
        idTitleLabel.setFont(LABEL_FONT);
        idLabel = new JLabel("-");
        idLabel.setFont(LABEL_FONT);
        idPanel.add(idTitleLabel);
        idPanel.add(idLabel);

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBackground(BACKGROUND_COLOR);

        nameField = new JTextField();
        specField = new JTextField();

        String[] hours = new String[14];
        for (int i = 0; i < 14; i++) {
            hours[i] = String.format("%02d:00", i + 7);
        }
        startHourBox = new JComboBox<>(hours);
        endHourBox = new JComboBox<>(hours);

        formPanel.add(new JLabel("Name:", JLabel.RIGHT)); formPanel.add(nameField);
        formPanel.add(new JLabel("Specialization:", JLabel.RIGHT)); formPanel.add(specField);
        formPanel.add(new JLabel("Start Hour:", JLabel.RIGHT)); formPanel.add(startHourBox);
        formPanel.add(new JLabel("End Hour:", JLabel.RIGHT)); formPanel.add(endHourBox);

        errorLabel = new JLabel(" ");
        errorLabel.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(BACKGROUND_COLOR);
        centerPanel.add(scrollPane);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(idPanel);
        centerPanel.add(Box.createVerticalStrut(5));
        centerPanel.add(errorLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(formPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton back = createStyledButton("Back");
        JButton save = createStyledButton("Save");
        JButton clear = createStyledButton("Clear Fields");
        JButton delete = createStyledButton("Delete");

        back.setActionCommand("back");
        save.setActionCommand("save");
        clear.setActionCommand("clear");
        delete.setActionCommand("delete");

        back.addActionListener(controller);
        save.addActionListener(controller);
        clear.addActionListener(controller);
        delete.addActionListener(controller);

        buttonPanel.add(back);
        buttonPanel.add(clear);
        buttonPanel.add(save);
        buttonPanel.add(delete);

        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }


    private void onSelecting() {
        doctorTable.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;

            int row = doctorTable.getSelectedRow();
            if (row == -1) return;

            idLabel.setText(doctorTable.getValueAt(row, 0).toString());
            nameField.setText(doctorTable.getValueAt(row, 1).toString());
            specField.setText(doctorTable.getValueAt(row, 2).toString());
            startHourBox.setSelectedItem(doctorTable.getValueAt(row, 3).toString());
            endHourBox.setSelectedItem(doctorTable.getValueAt(row, 4).toString());
        });
    }

    public void updateTable(List<DoctorDTO> doctors) {
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

    public void setError(String error) {
        errorLabel.setVisible(true);
        errorLabel.setText(error);
    }

    public void clearError() {
        errorLabel.setVisible(false);
    }

    public void clearFields () {
        idLabel.setText("-");
        nameField.setText("");
        specField.setText("");
        startHourBox.setSelectedIndex(0);
        endHourBox.setSelectedIndex(0);
    }

    public Long getIdLabel() {
        if(idLabel.getText().equals("-")) return null;
        return Long.parseLong(idLabel.getText());
    }

    public String getDoctorName() {
        return nameField.getText();
    }

    public String getSpecialization() {
        return specField.getText();
    }

    public Time getStartHour() {
        return Time.valueOf(LocalTime.parse((String) Objects.requireNonNull(startHourBox.getSelectedItem())));
    }

    public Time getEndHour() {
        return Time.valueOf(LocalTime.parse((String) Objects.requireNonNull(endHourBox.getSelectedItem())));
    }
}
