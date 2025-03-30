package org.example.medcareappointmentmanager.presentation.view;

import org.example.medcareappointmentmanager.business.dto.LoginDTO;
import org.example.medcareappointmentmanager.presentation.controller.AuthenticationController;

import javax.swing.*;
import java.awt.*;

public class AuthenticationPanel extends AbstractPanel {
    private final AuthenticationController controller;
    private JTextField usernameField;
    private JPasswordField passwordField;

    public AuthenticationPanel(AuthenticationController controller) {
        this.controller = controller;
        buildUI();
    }

    private void buildUI() {
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBackground(BACKGROUND_COLOR);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Login to MedCare");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setFont(LABEL_FONT);
        usernameField = new JTextField(20);

        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setFont(LABEL_FONT);
        passwordField = new JPasswordField(20);

        JButton loginButton = createStyledButton("Login");
        loginButton.setActionCommand("login");
        loginButton.addActionListener(controller);

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        centerPanel.add(titleLabel, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        centerPanel.add(usernameLabel, gbc);

        gbc.gridx = 1;
        centerPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        centerPanel.add(passwordLabel, gbc);

        gbc.gridx = 1;
        centerPanel.add(passwordField, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        centerPanel.add(loginButton, gbc);

        add(centerPanel, BorderLayout.CENTER);
    }

    public LoginDTO login() {
        return new LoginDTO(usernameField.getText(), String.valueOf(passwordField.getPassword()));
    }
}
