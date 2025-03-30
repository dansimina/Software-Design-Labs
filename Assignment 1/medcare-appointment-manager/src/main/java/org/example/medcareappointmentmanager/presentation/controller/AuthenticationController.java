package org.example.medcareappointmentmanager.presentation.controller;

import org.example.medcareappointmentmanager.business.dto.UserDTO;
import org.example.medcareappointmentmanager.business.service.UserService;
import org.example.medcareappointmentmanager.presentation.WindowManager;
import org.example.medcareappointmentmanager.presentation.view.AuthenticationPanel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionEvent;

@Component
public class AuthenticationController extends AbstractController {
    private final WindowManager windowManager;
    private final UserService userService;
    private final AuthenticationPanel authenticationPanel;

    @Autowired
    public AuthenticationController(WindowManager windowManager, UserService userService) {
        super(windowManager);
        this.userService = userService;
        this.windowManager = windowManager;
        this.authenticationPanel = new AuthenticationPanel(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("login")) {
            UserDTO user = userService.login(authenticationPanel.login());

            if(user != null) {
                windowManager.showMainWindow(user);
            }
        }
    }

    @Override
    public void setUser(UserDTO user) {

    }

    @Override
    public JPanel getPanel() {
        return authenticationPanel;
    }
}
