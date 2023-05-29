package com.msi.gestordocumental.entities;

import java.sql.Timestamp;
import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@EntityListeners(AuditingEntityListener.class)
public class Office {

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
    private Integer idOffice;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(nullable = false)
    private Integer version;

    @Column(nullable = false, length = 50)
    private String type;

    @Column(nullable = false)
    private Boolean state;

    @ManyToOne()
    @JoinColumn(name = "author_id")
    private User author;

    @ManyToOne()
    @JoinColumn(name = "last_modifier_id")
    private User lastModifier;

    @ManyToOne()
    @JoinColumn(name = "departament_id", nullable = false)
    private Departament departament;

    @ManyToOne()
    @JoinColumn(name = "unit_id")
    private Unit unit;

    @CreationTimestamp
    private Timestamp dateCreated;

    @UpdateTimestamp
    private Timestamp lastUpdated;

    @Column(nullable = true)
    private Boolean valid;

    @Lob
    @Column(nullable = true)
    private byte[] data;


    public Boolean getValid() {
        return valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public Integer getIdOffice() {
        return idOffice;
    }

    public void setIdOffice(final Integer idOffice) {
        this.idOffice = idOffice;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(final Integer version) {
        this.version = version;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public User getLastModifier() {
        return lastModifier;
    }

    public void setLastModifier(User lastModifier) {
        this.lastModifier = lastModifier;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
