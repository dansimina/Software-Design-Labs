package org.example.medcareappointmentmanager.presentation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

@Component
public class ReceptionstMainWindowController extends AbstractController {

    @Autowired
    public ReceptionstMainWindowController(WindowManager windowManager) {
        super(windowManager);
    }

    protected void buildUI() {
        panel = new JPanel();
        Label infoLabel = new Label();

        assert user != null;
        infoLabel.setText(user.toString());

        panel.add(infoLabel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
