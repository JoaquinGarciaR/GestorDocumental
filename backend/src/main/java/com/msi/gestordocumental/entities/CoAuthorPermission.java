package com.msi.gestordocumental.entities;

import java.sql.Timestamp;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class CoAuthorPermission {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 100
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long idCoAuthorPermission;
   
    @ManyToOne()
    @JoinColumn(name = "departament_id", nullable = true)
    private Departament departament;

    @ManyToOne()
    @JoinColumn(name = "unit_id", nullable = true)
    private Unit unit;

    @ManyToOne()
    @JoinColumn(name = "office_id", nullable = false)
    private Office office;

    @ManyToOne()
    @JoinColumn(name = "user_id", nullable = true)
    private User user;

    @CreationTimestamp
    private Timestamp dateCreated;

    @UpdateTimestamp
    private Timestamp lastUpdated;

    public Long getIdCoAuthorPermission() {
        return idCoAuthorPermission;
    }

    public void setIdCoAuthorPermission(final Long idCoAuthorPermission) {
        this.idCoAuthorPermission = idCoAuthorPermission;
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
    
    public Departament getDepartament() {
        return departament;
    }

    public void setDepartament(Departament departament) {
        this.departament = departament;
    }
    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }


}
