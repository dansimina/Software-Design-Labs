package org.example.medcareappointmentmanager.presentation;

import org.example.medcareappointmentmanager.business.dto.UserDTO;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

@Component
public abstract class AbstractController implements ActionListener {
    WindowManager windowManager;

    UserDTO user;

    JPanel panel;

    public AbstractController(WindowManager windowManager) {
        this.windowManager = windowManager;
    }

    protected abstract void buildUI();

    protected JButton createStyledButton(String text, Font font, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(font);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }

    public JPanel getPanel() {
        if(panel == null) {
            buildUI();
        }
        return panel;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
