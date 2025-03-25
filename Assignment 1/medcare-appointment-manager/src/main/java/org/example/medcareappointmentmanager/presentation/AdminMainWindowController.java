package org.example.medcareappointmentmanager.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

@Component
public class AdminMainWindowController extends AbstractController {

    @Autowired
    public AdminMainWindowController(WindowManager windowManager) {
        super(windowManager);
    }

    protected void buildUI() {
        panel = new JPanel();
        panel.setBackground(new Color(245, 245, 245)); // Soft light gray
        panel.setLayout(new BorderLayout(20, 20));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30)); // Padding around edges

        // === Top panel: greeting + logout ===
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(245, 245, 245));

        JLabel greetingLabel = new JLabel("👋 Hello, " + user.name() + "!");
        greetingLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        greetingLabel.setForeground(new Color(60, 63, 65));

        // Left side: greeting
        JPanel greetingPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        greetingPanel.setBackground(new Color(245, 245, 245));
        greetingPanel.add(greetingLabel);

        // Right side: logout button
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutPanel.setBackground(new Color(245, 245, 245));
        Font logoutFont = new Font("Segoe UI", Font.BOLD, 14);
        JButton logoutButton = createStyledButton("Logout", logoutFont, Color.WHITE);
        logoutButton.setActionCommand("logout");
        logoutButton.addActionListener(this);
        logoutPanel.add(logoutButton);

        topPanel.add(greetingPanel, BorderLayout.WEST);
        topPanel.add(logoutPanel, BorderLayout.EAST);

        // === Center panel with buttons ===
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 20, 20));
        buttonPanel.setBackground(new Color(245, 245, 245));

        Font buttonFont = new Font("Segoe UI", Font.BOLD, 16);
        Color buttonColor = Color.WHITE;

        JButton receptionistButton = createStyledButton("Manage Receptionist", buttonFont, buttonColor);
        JButton doctorButton = createStyledButton("Manage Doctor", buttonFont, buttonColor);
        JButton servicesButton = createStyledButton("Manage Medical Services", buttonFont, buttonColor);
        JButton reportButton = createStyledButton("Generate Report", buttonFont, buttonColor);

        // Action listeners
        receptionistButton.addActionListener(this);
        receptionistButton.setActionCommand("receptionist");

        doctorButton.addActionListener(this);
        doctorButton.setActionCommand("doctor");

        servicesButton.addActionListener(this);
        servicesButton.setActionCommand("services");

        reportButton.addActionListener(this);
        reportButton.setActionCommand("report");

        buttonPanel.add(receptionistButton);
        buttonPanel.add(doctorButton);
        buttonPanel.add(servicesButton);
        buttonPanel.add(reportButton);

        // === Assemble all ===
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()) {
            case "receptionist" -> windowManager.showAdminManageReceptionistWindow(user);
            case "doctor" -> windowManager.showMainWindow(user);
            case "services" -> windowManager.showMainWindow(user);
            case "report" -> windowManager.showMainWindow(user);
            case "logout" -> windowManager.showAuthenticationWindow();
        }
    }
}
