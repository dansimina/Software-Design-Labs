package org.example.medcareappointmentmanager.presentation.view;

import javax.swing.*;
import java.awt.*;

abstract class AbstractPanel extends JPanel {
    protected static final Color BACKGROUND_COLOR = new Color(245, 245, 245);
    protected static final Font LABEL_FONT = new Font("Segoe UI", Font.PLAIN, 14);
    protected static final Font BUTTON_FONT = new Font("Segoe UI", Font.BOLD, 14);

    public AbstractPanel() {
        super(new BorderLayout(20, 20));
        setBackground(BACKGROUND_COLOR);
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    protected JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setFont(BUTTON_FONT);
        button.setBackground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(200, 200, 200)),
                BorderFactory.createEmptyBorder(10, 20, 10, 20)
        ));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        return button;
    }
}
