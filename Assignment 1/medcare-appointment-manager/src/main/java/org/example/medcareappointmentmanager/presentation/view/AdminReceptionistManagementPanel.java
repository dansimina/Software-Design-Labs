package org.example.medcareappointmentmanager.presentation.view;

import org.example.medcareappointmentmanager.business.dto.CreateUserDTO;
import org.example.medcareappointmentmanager.business.dto.UserDTO;
import org.example.medcareappointmentmanager.presentation.PopulateTable;
import org.example.medcareappointmentmanager.presentation.controller.AdminReceptionistManagementController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminReceptionistManagementPanel extends AbstractPanel {
    private final AdminReceptionistManagementController controller;
    private JTable receptionistTable;
    private JTextField nameField, usernameField;
    private JPasswordField passwordField;
    private JLabel errorLabel;

    public AdminReceptionistManagementPanel(AdminReceptionistManagementController controller) {
        this.controller = controller;
        buildUI();
    }

    protected void buildUI() {
        DefaultTableModel model = new DefaultTableModel(new Object[][] {}, new String[] {"Name", "Username"}) {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        receptionistTable = new JTable(model);
        receptionistTable.setRowHeight(24);
        receptionistTable.setFont(LABEL_FONT);
        receptionistTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));

        JScrollPane scrollPane = new JScrollPane(receptionistTable);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBackground(BACKGROUND_COLOR);
        nameField = new JTextField();
        usernameField = new JTextField();
        passwordField = new JPasswordField();

        formPanel.add(new JLabel("Name:", JLabel.RIGHT)); formPanel.add(nameField);
        formPanel.add(new JLabel("Username:", JLabel.RIGHT)); formPanel.add(usernameField);
        formPanel.add(new JLabel("Password:", JLabel.RIGHT)); formPanel.add(passwordField);

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

    public void updateTable(List<UserDTO> receptionist) {
        PopulateTable.populateTable(receptionist, receptionistTable);
    }

    public void setError(String error) {
        errorLabel.setVisible(true);
        errorLabel.setText(error);
    }

    public void clearError() {
        errorLabel.setVisible(false);
    }

    public CreateUserDTO getNewUser() {
        return new CreateUserDTO(nameField.getText(), usernameField.getText(), passwordField.getText());
    }
}
