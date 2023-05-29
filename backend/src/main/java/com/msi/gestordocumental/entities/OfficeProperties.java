package com.msi.gestordocumental.entities;

public class OfficeProperties {
    private String permissions;
    private User user;
    private Office office;

    public OfficeProperties() {
        this.permissions = "";
        this.office = null;
        this.user = null;
    }

    public OfficeProperties(String permissions, Office office, User user) {
        this.permissions = permissions;
        this.office = office;
        this.user = user;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(Office office) {
        this.office = office;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
