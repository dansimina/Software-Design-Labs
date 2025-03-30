package org.example.medcareappointmentmanager.presentation.view;

import org.example.medcareappointmentmanager.presentation.controller.AdminMedicalServicesManagementController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminMedicalServicesManagementPanel extends AbstractPanel {
    private JTable servicesTable;
    private JTextField nameField;
    private JTextField priceField;
    private JTextField durationField;
    private JLabel errorLabel;
    private final AdminMedicalServicesManagementController controller;

    public AdminMedicalServicesManagementPanel(AdminMedicalServicesManagementController controller) {
        this.controller = controller;
        buildUI();
    }

    protected void buildUI() {
        DefaultTableModel model = new DefaultTableModel(new Object[][] {}, new String[] {"Name", "Price", "Duration (min)"}) {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        servicesTable = new JTable(model);
        servicesTable.setRowHeight(24);
        servicesTable.setFont(LABEL_FONT);
        servicesTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(servicesTable);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBackground(BACKGROUND_COLOR);

        nameField = new JTextField();
        priceField = new JTextField();
        durationField = new JTextField();

        formPanel.add(new JLabel("Name:", JLabel.RIGHT)); formPanel.add(nameField);
        formPanel.add(new JLabel("Price:", JLabel.RIGHT)); formPanel.add(priceField);
        formPanel.add(new JLabel("Duration (minutes):", JLabel.RIGHT)); formPanel.add(durationField);

        errorLabel = new JLabel(" ");
        errorLabel.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton back = createStyledButton("Back");
        JButton save = createStyledButton("Save");
        back.setActionCommand("back"); back.addActionListener(controller);
        save.setActionCommand("save"); save.addActionListener(controller);
        buttonPanel.add(back); buttonPanel.add(save);

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

    public String getServiceName() {
        return nameField.getText();
    }

    public Integer getServicePrice() {
        try { return Integer.parseInt(priceField.getText()); } catch (NumberFormatException e) { return null; }
    }

    public Integer getServiceDuration() {
        try { return Integer.parseInt(durationField.getText()); } catch (NumberFormatException e) { return null; }
    }
}
