package com.msi.gestordocumental.entities;

import java.sql.Timestamp;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class ReaderPermission {

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
    private Long idReaderPermission;

    @ManyToOne()
    @JoinColumn(name = "departament_id")
    private Departament departament;

    @ManyToOne()
    @JoinColumn(name = "office_id", nullable = false)
    private Office office;

    @ManyToOne()
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @CreationTimestamp
    private Timestamp dateCreated;

    @UpdateTimestamp
    private Timestamp lastUpdated;

    public Long getId() {
        return idReaderPermission;
    }

    public void setId(final Long idReaderPermission) {
        this.idReaderPermission = idReaderPermission;
    }

    public Departament getDepartament() {
        return departament;
    }

    public void setDepartament(final Departament departament) {
        this.departament = departament;
    }

    public Office getOffice() {
        return office;
    }

    public void setOffice(final Office office) {
        this.office = office;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(final Unit unit) {
        this.unit = unit;
    }

    public User getUser() {
        return user;
    }

    public void setUser(final User user) {
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

}
