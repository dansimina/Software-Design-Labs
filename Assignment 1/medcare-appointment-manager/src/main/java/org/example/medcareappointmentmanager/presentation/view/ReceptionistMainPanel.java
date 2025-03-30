package org.example.medcareappointmentmanager.presentation.view;

import org.example.medcareappointmentmanager.business.dto.UserDTO;
import org.example.medcareappointmentmanager.presentation.controller.ReceptionstMainWindowController;

import javax.swing.*;
import java.awt.*;

public class ReceptionistMainPanel extends AbstractPanel {
    private final ReceptionstMainWindowController controller;
    private final UserDTO user;

    public ReceptionistMainPanel(ReceptionstMainWindowController controller, UserDTO user) {
        this.controller = controller;
        this.user = user;
        buildUI();
    }

    protected void buildUI() {
        JLabel greetingLabel = new JLabel("👋 Welcome, " + user.name() + "!");
        greetingLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        greetingLabel.setForeground(new Color(60, 63, 65));

        JButton logoutButton = createStyledButton("Logout");
        logoutButton.setActionCommand("logout");
        logoutButton.addActionListener(controller);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(BACKGROUND_COLOR);
        topPanel.add(greetingLabel, BorderLayout.WEST);
        topPanel.add(logoutButton, BorderLayout.EAST);

        JButton registerAppointmentButton = createStyledButton("Register Appointment");
        JButton viewAppointmentsButton = createStyledButton("View Appointments");

        registerAppointmentButton.setActionCommand("registerAppointment");
        viewAppointmentsButton.setActionCommand("viewAppointments");

        registerAppointmentButton.addActionListener(controller);
        viewAppointmentsButton.addActionListener(controller);

        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 20, 20));
        buttonPanel.setBackground(BACKGROUND_COLOR);
        buttonPanel.add(registerAppointmentButton);
        buttonPanel.add(viewAppointmentsButton);

        add(topPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
    }
}

