package org.example.medcareappointmentmanager.data;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class User extends AbstractEntity {
    @NotBlank(message = "Name is required!")
    @Size(min = 1, max = 50, message = "Name must be between 3 and 50 characters")
    private String name;

    @NotBlank(message = "Userame is required!")
    @Size(min = 3, max = 50, message = "Name must be between 3 and 50 characters")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    @ManyToOne
    private UserType type;

    public User() {
    }

    public User(String name, String username, String password, UserType type) {
        this.name = name;
        this.username = username;
        this.password = password;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserType getType() {
        return type;
    }

    public void setType(UserType type) {
        this.type = type;
    }
}
