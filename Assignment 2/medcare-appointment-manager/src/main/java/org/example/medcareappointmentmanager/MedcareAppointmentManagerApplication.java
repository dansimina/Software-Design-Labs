package org.example.medcareappointmentmanager;

import org.example.medcareappointmentmanager.presentation.WindowManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@SpringBootApplication
public class MedcareAppointmentManagerApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(MedcareAppointmentManagerApplication.class, args);
        WindowManager windowManager = context.getBean(WindowManager.class);
        windowManager.showAuthenticationWindow();
    }

}
