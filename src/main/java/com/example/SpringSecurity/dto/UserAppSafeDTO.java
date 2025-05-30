package com.example.SpringSecurity.dto;

import java.util.Objects;

public class UserAppSafeDTO {

    private String role;
    private String permission;

    public UserAppSafeDTO(String role, String permission) {
        this.role = role;
        this.permission = permission;
    }

    public String getRole() {
        return role;
    }

    public String getPermission() {
        return permission;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof UserAppSafeDTO)) return false;
        UserAppSafeDTO that = (UserAppSafeDTO) o;
        return Objects.equals(role, that.role) &&
                Objects.equals(permission, that.permission);
    }

    @Override
    public int hashCode() {
        return Objects.hash(role, permission);
    }
}
