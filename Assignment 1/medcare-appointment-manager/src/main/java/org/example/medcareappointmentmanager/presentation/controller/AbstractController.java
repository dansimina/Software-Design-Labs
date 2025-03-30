package org.example.medcareappointmentmanager.presentation.controller;

import org.example.medcareappointmentmanager.business.dto.UserDTO;
import org.example.medcareappointmentmanager.presentation.WindowManager;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.event.ActionListener;

@Component
public abstract class AbstractController implements ActionListener {
    protected WindowManager windowManager;
    protected UserDTO user;

    public AbstractController(WindowManager windowManager) {
        this.windowManager = windowManager;
    }

    public abstract void setUser(UserDTO user);

    public abstract JPanel getPanel();
}
