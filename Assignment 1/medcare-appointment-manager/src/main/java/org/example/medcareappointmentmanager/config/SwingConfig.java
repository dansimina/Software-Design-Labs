package org.example.medcareappointmentmanager.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

import javax.swing.*;
import java.awt.*;

@Configuration
public class SwingConfig {
    @Bean
    @Lazy
    @ConditionalOnProperty(name = "headless", havingValue = "false", matchIfMissing = true)
    public JFrame mainFrame() {
        JFrame frame = new JFrame("MedCare Appointment Manager");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1280, 720);
        frame.setLocationRelativeTo(null);
        return frame;
    }
}
