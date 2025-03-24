package org.example.medcareappointmentmanager.data;

public enum AppointmentStatus {
    New("new"),
    ONGOING("ongoing"),
    COMPLETED("completed");

    private final String displayName;

    AppointmentStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
