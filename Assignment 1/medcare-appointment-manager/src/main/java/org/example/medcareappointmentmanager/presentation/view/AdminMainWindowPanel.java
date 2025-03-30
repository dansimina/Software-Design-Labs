package org.example.medcareappointmentmanager.presentation.view;

import org.example.medcareappointmentmanager.business.dto.UserDTO;
import org.example.medcareappointmentmanager.presentation.controller.AdminMainWindowController;

import javax.swing.*;
import java.awt.*;

public class AdminMainWindowPanel extends AbstractPanel {
    private final AdminMainWindowController controller;
    private final UserDTO user;

    public AdminMainWindowPanel(AdminMainWindowController controller, UserDTO user) {
        this.controller = controller;
        this.user = user;
        buildUI();
    }

    protected void buildUI() {
        JLabel greetingLabel = new JLabel("👋 Hello, " + user.name() + "!");
        greetingLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        greetingLabel.setForeground(new Color(60, 63, 65));

        JButton logoutButton = createStyledButton("Logout");
        logoutButton.setActionCommand("logout");
        logoutButton.addActionListener(controller);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BACKGROUND_COLOR);
        topPanel.add(greetingLabel, BorderLayout.WEST);
        topPanel.add(logoutButton, BorderLayout.EAST);

        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        for (String text : new String[]{"Manage Receptionist", "Manage Doctor", "Manage Medical Services", "Generate Report"}) {
            JButton btn = createStyledButton(text);
            btn.setActionCommand(text.toLowerCase().replace(" ", ""));
            btn.addActionListener(controller);
            buttonPanel.add(btn);
        }

        add(topPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }
}