package com.msi.gestordocumental.entities;

import java.io.Serializable;
import java.util.Objects;

public class CoAuthorPermissionId implements Serializable {
    private int iDOffice;
    private String iDUser;

    public CoAuthorPermissionId() {

    }

    public CoAuthorPermissionId(int iDOffice, String iDUser) {
        this.iDOffice = iDOffice;
        this.iDUser = iDUser;
    }

    public int getiDOffice() {
        return iDOffice;
    }

    public void setiDOffice(int iDOffice) {
        this.iDOffice = iDOffice;
    }

    public String getiDUser() {
        return iDUser;
    }

    public void setiDUser(String iDUser) {
        this.iDUser = iDUser;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CoAuthorPermissionId)) return false;
        CoAuthorPermissionId that = (CoAuthorPermissionId) o;
        return iDOffice == that.iDOffice && Objects.equals(iDUser, that.iDUser);
    }

    @Override
    public int hashCode() {
        return Objects.hash(iDOffice, iDUser);
    }
}

