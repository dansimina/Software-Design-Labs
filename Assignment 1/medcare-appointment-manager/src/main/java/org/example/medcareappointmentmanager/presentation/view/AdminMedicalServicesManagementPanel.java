
package org.example.medcareappointmentmanager.presentation.view;

import org.example.medcareappointmentmanager.business.dto.MedicalServiceDTO;
import org.example.medcareappointmentmanager.presentation.controller.AdminMedicalServicesManagementController;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class AdminMedicalServicesManagementPanel extends AbstractPanel {
    private JLabel idLabel;
    private JTable servicesTable;
    private JTextField nameField;
    private JTextField priceField;
    private JComboBox<Integer> durationBox;
    private JLabel errorLabel;
    private final AdminMedicalServicesManagementController controller;

    public AdminMedicalServicesManagementPanel(AdminMedicalServicesManagementController controller) {
        this.controller = controller;
        buildUI();
    }

    private void buildUI() {
        DefaultTableModel model = new DefaultTableModel(new Object[][] {}, new String[] {"Id", "Name", "Price", "Duration (min)"}) {
            public boolean isCellEditable(int row, int column) { return false; }
        };

        servicesTable = new JTable(model);
        servicesTable.setRowHeight(24);
        servicesTable.setFont(LABEL_FONT);
        servicesTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        onSelecting();

        JScrollPane scrollPane = new JScrollPane(servicesTable);
        scrollPane.setPreferredSize(new Dimension(600, 250));

        JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        idPanel.setBackground(BACKGROUND_COLOR);
        JLabel idTitleLabel = new JLabel("Service ID:");
        idTitleLabel.setFont(LABEL_FONT);
        idLabel = new JLabel("-");
        idLabel.setFont(LABEL_FONT);
        idPanel.add(idTitleLabel);
        idPanel.add(idLabel);

        errorLabel = new JLabel(" ");
        errorLabel.setFont(new Font("Segoe UI", Font.ITALIC, 13));
        errorLabel.setForeground(Color.RED);
        errorLabel.setVisible(false);

        // === Form Panel ===
        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBackground(BACKGROUND_COLOR);
        nameField = new JTextField();
        priceField = new JTextField();
        durationBox = new JComboBox<>(new Integer[] {30, 60, 90, 120, 150, 180});

        formPanel.add(new JLabel("Name:", JLabel.RIGHT)); formPanel.add(nameField);
        formPanel.add(new JLabel("Price:", JLabel.RIGHT)); formPanel.add(priceField);
        formPanel.add(new JLabel("Duration (minutes):", JLabel.RIGHT)); formPanel.add(durationBox);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBackground(BACKGROUND_COLOR);
        centerPanel.add(scrollPane);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(idPanel);
        centerPanel.add(Box.createVerticalStrut(5));
        centerPanel.add(errorLabel);
        centerPanel.add(Box.createVerticalStrut(10));
        centerPanel.add(formPanel);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton back = createStyledButton("Back");
        JButton save = createStyledButton("Save");
        JButton clear = createStyledButton("Clear Fields");
        JButton delete = createStyledButton("Delete");

        back.setActionCommand("back");
        save.setActionCommand("save");
        clear.setActionCommand("clear");
        delete.setActionCommand("delete");

        back.addActionListener(controller);
        save.addActionListener(controller);
        clear.addActionListener(controller);
        delete.addActionListener(controller);

        buttonPanel.add(back);
        buttonPanel.add(clear);
        buttonPanel.add(save);
        buttonPanel.add(delete);

        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void onSelecting() {
        servicesTable.getSelectionModel().addListSelectionListener(e -> {
            if (e.getValueIsAdjusting()) return;

            int row = servicesTable.getSelectedRow();
            if (row == -1) return;

            idLabel.setText(servicesTable.getValueAt(row, 0).toString());
            nameField.setText(servicesTable.getValueAt(row, 1).toString());
            priceField.setText(servicesTable.getValueAt(row, 2).toString());
            durationBox.setSelectedItem(Integer.parseInt(servicesTable.getValueAt(row, 3).toString()));
        });
    }

    public void updateTable(List<MedicalServiceDTO> services) {
        DefaultTableModel model = (DefaultTableModel) servicesTable.getModel();
        model.setRowCount(0);

        for (MedicalServiceDTO service : services) {
            model.addRow(new Object[]{
                    service.id(),
                    service.name(),
                    service.price(),
                    service.duration()
            });
        }
    }

    public void setError(String error) {
        errorLabel.setVisible(true);
        errorLabel.setText(error);
    }

    public void clearError() {
        errorLabel.setVisible(false);
    }

    public void clearFields() {
        idLabel.setText("-");
        nameField.setText("");
        priceField.setText("");
        durationBox.setSelectedIndex(0);
    }

    public Long getServiceId() {
        if (idLabel.getText().equals("-")) return null;
        return Long.parseLong(idLabel.getText());
    }

    public String getServiceName() {
        return nameField.getText();
    }

    public Integer getServicePrice() {
        try { return Integer.parseInt(priceField.getText()); } catch (NumberFormatException e) { return null; }
    }

    public Integer getServiceDuration() {
        return (Integer) durationBox.getSelectedItem();
    }
}
