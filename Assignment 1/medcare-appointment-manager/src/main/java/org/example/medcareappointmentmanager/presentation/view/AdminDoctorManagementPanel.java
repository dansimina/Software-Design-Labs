package org.example.medcareappointmentmanager.presentation.view;

import org.example.medcareappointmentmanager.presentation.controller.AdminDoctorManagementController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminDoctorManagementPanel extends AbstractPanel {
    private JTable doctorTable;
    private JTextField nameField;
    private JTextField specField;
    private JTextField startField;
    private JTextField endField;
    private JLabel errorLabel;
    private final AdminDoctorManagementController controller;

    public AdminDoctorManagementPanel(AdminDoctorManagementController controller) {
        this.controller = controller;
        buildUI();
    }

    protected void buildUI() {
        DefaultTableModel model = new DefaultTableModel(new Object[][] {}, new String[] {"Name", "Specialization", "Start Hour", "End Hour"}) {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        doctorTable = new JTable(model);
        doctorTable.setRowHeight(24);
        doctorTable.setFont(LABEL_FONT);
        doctorTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(doctorTable);

        // === Form Setup ===
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBackground(BACKGROUND_COLOR);

        nameField = new JTextField();
        specField = new JTextField();
        startField = new JTextField();
        endField = new JTextField();

        formPanel.add(new JLabel("Name:", JLabel.RIGHT)); formPanel.add(nameField);
        formPanel.add(new JLabel("Specialization:", JLabel.RIGHT)); formPanel.add(specField);
        formPanel.add(new JLabel("Start Hour:", JLabel.RIGHT)); formPanel.add(startField);
        formPanel.add(new JLabel("End Hour:", JLabel.RIGHT)); formPanel.add(endField);

        // === Error Label ===
        errorLabel = new JLabel(" ");
        errorLabel.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);

        // === Button Panel ===
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton back = createStyledButton("Back");
        JButton save = createStyledButton("Save");
        back.setActionCommand("back"); back.addActionListener(controller);
        save.setActionCommand("save"); save.addActionListener(controller);
        buttonPanel.add(back); buttonPanel.add(save);

        // === Final Layout ===
        add(scrollPane, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
        add(errorLabel, BorderLayout.WEST);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    public void setError(String error) {
        errorLabel.setVisible(true);
        errorLabel.setText(error);
    }

    public void clearError() {
        errorLabel.setVisible(false);
    }

    public String getDoctorName() {
        return nameField.getText();
    }

    public String getSpecialization() {
        return specField.getText();
    }

    public String getStartHour() {
        return startField.getText();
    }

    public String getEndHour() {
        return endField.getText();
    }
}

