package org.example.medcareappointmentmanager.presentation;

import org.example.medcareappointmentmanager.business.dto.LoginDTO;
import org.example.medcareappointmentmanager.business.dto.UserDTO;
import org.example.medcareappointmentmanager.business.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

@Component
public class AuthenticationController extends AbstractController {
    private final UserService userService;

    private JTextField usernameField;
    private JPasswordField passwordField;

    @Autowired
    public AuthenticationController(UserService userService, WindowManager windowManager) {
        super(windowManager);
        this.userService = userService;
    }

    protected void buildUI() {
        panel = new JPanel(new GridBagLayout());
        panel.setBackground(Color.CYAN);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new java.awt.Insets(10, 10, 10, 10);

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");

        usernameField = new JTextField(20);
        passwordField = new JPasswordField(20);
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(Color.WHITE);

        loginButton.addActionListener(this);
        loginButton.setActionCommand("login");

        // Username label
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel.add(usernameLabel, gbc);

        // Username field
        gbc.gridx = 1;
        gbc.gridy = 0;
        panel.add(usernameField, gbc);

        // Password label
        gbc.gridx = 0;
        gbc.gridy = 1;
        panel.add(passwordLabel, gbc);

        // Password field
        gbc.gridx = 1;
        gbc.gridy = 1;
        panel.add(passwordField, gbc);

        // Login button
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        panel.add(loginButton, gbc);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("login")) {
            LoginDTO loginDTO = new LoginDTO(usernameField.getText(), String.valueOf(passwordField.getPassword()));
            UserDTO user = userService.login(loginDTO);

            if(user != null) {
                windowManager.showMainWindow(user);
            }
        }
    }
}
