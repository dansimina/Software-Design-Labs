package org.example.medcareappointmentmanager.presentation;

import org.example.medcareappointmentmanager.business.dto.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.swing.*;

@Component
public class WindowManager {

    private final ApplicationContext context;
    private final JFrame mainFrame;

    @Autowired
    public WindowManager(ApplicationContext context, JFrame mainFrame) {
        this.context = context;
        this.mainFrame = mainFrame;
    }

    public void showAuthenticationWindow() {
        AuthenticationController controller = context.getBean(AuthenticationController.class);
        mainFrame.setContentPane(controller.getPanel());
        refresh();
    }

    public void showAdminMainWindow(UserDTO user) {
        if (user != null && "admin".equals(user.type().type())) {
            AdminMainWindowController controller = context.getBean(AdminMainWindowController.class);
            controller.setUser(user);
            mainFrame.setContentPane(controller.getPanel());
            refresh();
        }
    }

    private void refresh() {
        mainFrame.revalidate();
        mainFrame.repaint();
        mainFrame.setVisible(true);
    }
}


