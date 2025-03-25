package org.example.medcareappointmentmanager.presentation;

import org.example.medcareappointmentmanager.business.dto.CreateUserDTO;
import org.example.medcareappointmentmanager.business.dto.UserDTO;
import org.example.medcareappointmentmanager.business.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.List;

@Component
public class AdminManageReceptionistController extends AbstractController {
    @Autowired
    private UserService userService;

    private PopulateTable populateTable;

    private JTable receptionistTable;
    private JTextField nameField;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel errorLabel;

    @Autowired
    public AdminManageReceptionistController(WindowManager windowManager, UserService userService) {
        super(windowManager);
        this.userService = userService;
    }

    protected void buildUI() {
        panel = new JPanel();
        panel.setLayout(new BorderLayout(20, 20));
        panel.setBackground(new Color(250, 250, 250));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTable receptionistTable = new JTable(); // Setat ulterior prin reflection
        receptionistTable.setFillsViewportHeight(true);
        receptionistTable.setRowHeight(24);
        receptionistTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        receptionistTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        JScrollPane tableScrollPane = new JScrollPane(receptionistTable);

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBackground(new Color(250, 250, 250));

        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        nameLabel.setFont(labelFont);
        usernameLabel.setFont(labelFont);
        passwordLabel.setFont(labelFont);

        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(usernameLabel);
        formPanel.add(usernameField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        JLabel errorLabel = new JLabel(" ");
        errorLabel.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        errorLabel.setForeground(new Color(200, 0, 0));
        errorLabel.setVisible(false);

        JPanel errorPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        errorPanel.setBackground(new Color(250, 250, 250));
        errorPanel.add(errorLabel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        buttonPanel.setBackground(new Color(250, 250, 250));

        Font buttonFont = new Font("Segoe UI", Font.BOLD, 14);
        Color buttonColor = Color.WHITE;

        JButton backButton = createStyledButton("Back", buttonFont, buttonColor);
        JButton saveButton = createStyledButton("Save", buttonFont, buttonColor);

        backButton.addActionListener(this);
        backButton.setActionCommand("back");

        saveButton.addActionListener(this);
        saveButton.setActionCommand("save");

        buttonPanel.add(backButton);
        buttonPanel.add(saveButton);

        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(new Color(250, 250, 250));
        centerPanel.add(formPanel, BorderLayout.CENTER);
        centerPanel.add(errorPanel, BorderLayout.SOUTH);

        panel.add(tableScrollPane, BorderLayout.NORTH);
        panel.add(centerPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        this.receptionistTable = receptionistTable;
        this.nameField = nameField;
        this.usernameField = usernameField;
        this.passwordField = passwordField;
        this.errorLabel = errorLabel;

        updateTable();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "back" -> windowManager.showMainWindow(user);
            case "save" -> {
                UserDTO newUser = userService.register(new CreateUserDTO(nameField.getText(), usernameField.getText(), passwordField.getText()), "receptionist");
                if(newUser != null) {
                    errorLabel.setVisible(false);
                    updateTable();
                }
                else {
                    errorLabel.setVisible(true);
                    errorLabel.setText("Error");
                }
            }
        }
    }

    private void updateTable() {
        List<UserDTO> receptionist = userService.getUsersByType("receptionist");
        if(receptionist != null) {
            PopulateTable.populateTable(receptionist, receptionistTable);
        }
    }
}
