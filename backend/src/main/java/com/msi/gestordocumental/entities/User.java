package com.msi.gestordocumental.entities;

import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name = "\"user\"")
@EntityListeners(AuditingEntityListener.class)
public class User {

    @Id
    @Column(nullable = false, updatable = false, length = 20)
    private String idUser;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false, length = 50)
    private String lastName1;

    @Column(nullable = false, length = 50)
    private String lastName2;

    @Column(nullable = false)
    private Boolean boss;

    @Column(nullable = false)
    private Integer role;

    @Column(nullable = false, length = 50)
    private String telephone;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false, length = 100)
    private String passwordEncrypt;

    @Column(nullable = false)
    private Boolean state;

    @ManyToOne()
    @JoinColumn(name = "departament_id", nullable = false)
    private Departament departament;

    @ManyToOne()
    @JoinColumn(name = "unit_id", nullable = false)
    private Unit unit;

    @CreationTimestamp
    private Timestamp dateCreated;

    @UpdateTimestamp
    private Timestamp lastUpdated;

    public String getIdUser() {
        return idUser;
    }

    public void setIdUser(final String idUser) {
        this.idUser = idUser;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getLastName1() {
        return lastName1;
    }

    public void setLastName1(final String lastName1) {
        this.lastName1 = lastName1;
    }

    public String getLastName2() {
        return lastName2;
    }

    public void setLastName2(final String lastName2) {
        this.lastName2 = lastName2;
    }

    public Boolean getBoss() {
        return boss;
    }

    public void setBoss(final Boolean boss) {
        this.boss = boss;
    }

    public Integer getRole() {
        return role;
    }

    public void setRole(final Integer role) {
        this.role = role;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(final String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public String getPasswordEncrypt() {
        return passwordEncrypt;
    }

    public void setPasswordEncrypt(final String passwordEncrypt) {
        this.passwordEncrypt = passwordEncrypt;
    }

    public Boolean getState() {
        return state;
    }

    public void setState(final Boolean state) {
        this.state = state;
    }

    public Departament getDepartament() {
        return departament;
    }

    public void setDepartament(final Departament departament) {
        this.departament = departament;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(final Unit unit) {
        this.unit = unit;
    }

    public Timestamp getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(final Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Timestamp getLastUpdated() {
        return lastUpdated;
    }

    public void setLastUpdated(final Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

}
