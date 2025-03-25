package org.example.medcareappointmentmanager.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

@Component
public class AdminManageDoctorController extends AbstractController {

    @Autowired
    public AdminManageDoctorController(WindowManager windowManager) {
        super(windowManager);
    }

    protected void buildUI() {
        panel = new JPanel();
        panel.setLayout(new BorderLayout(20, 20));
        panel.setBackground(new Color(250, 250, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Table to display doctors
        String[] columnNames = {"Name", "Specialization", "Start Time", "End Time"};
        Object[][] data = {}; // You can load this from your model
        JTable doctorTable = new JTable(data, columnNames);
        JScrollPane tableScrollPane = new JScrollPane(doctorTable);
        doctorTable.setFillsViewportHeight(true);
        doctorTable.setRowHeight(24);
        doctorTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        doctorTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        // Form fields
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBackground(new Color(250, 250, 250));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();

        JLabel specLabel = new JLabel("Specialization:");
        JTextField specField = new JTextField();

        JLabel startLabel = new JLabel("Start of Program:");
        JTextField startField = new JTextField("HH:mm");

        JLabel endLabel = new JLabel("End of Program:");
        JTextField endField = new JTextField("HH:mm");

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        nameLabel.setFont(labelFont);
        specLabel.setFont(labelFont);
        startLabel.setFont(labelFont);
        endLabel.setFont(labelFont);

        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(specLabel);
        formPanel.add(specField);
        formPanel.add(startLabel);
        formPanel.add(startField);
        formPanel.add(endLabel);
        formPanel.add(endField);

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        buttonPanel.setBackground(new Color(250, 250, 250));

        JButton backButton = new JButton("Back");
        JButton saveButton = new JButton("Save");
        JButton updateButton = new JButton("Update");

        // Add action listeners
        backButton.addActionListener(this);
        backButton.setActionCommand("back");
        saveButton.addActionListener(this);
        saveButton.setActionCommand("save");
        updateButton.addActionListener(this);
        updateButton.setActionCommand("update");

        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);
        backButton.setFont(buttonFont);
        saveButton.setFont(buttonFont);
        updateButton.setFont(buttonFont);

        backButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        saveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        updateButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

        buttonPanel.add(backButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(updateButton);

        // Add components to main panel
        panel.add(tableScrollPane, BorderLayout.NORTH);
        panel.add(formPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "back" -> windowManager.showMainWindow(user);
            case "save" -> {
            }
            case "update" -> {
            }
        }
    }
}
