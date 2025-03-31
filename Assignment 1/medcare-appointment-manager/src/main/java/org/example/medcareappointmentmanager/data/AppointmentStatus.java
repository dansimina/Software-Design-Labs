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

    public static AppointmentStatus fromDisplayName(String status) {
        for (AppointmentStatus s : values()) {
            if (s.displayName.equalsIgnoreCase(status)) {
                return s;
            }
        }
        throw new IllegalArgumentException("Invalid status: " + status);
    }
}

