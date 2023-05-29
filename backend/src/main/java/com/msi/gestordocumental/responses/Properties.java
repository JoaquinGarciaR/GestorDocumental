package com.msi.gestordocumental.responses;
import java.sql.Timestamp;

public class Properties {
    private String name;
    private String type;
    private AuthorResponse author;
    private Timestamp lastUpdated;
    private Timestamp dateCreated;
    private DepartamentName departament;
    private UnitName unit;
    private Boolean valid;
    private Integer version;
    private String permissions;
    private Boolean state;

    public Properties() {
        author = new AuthorResponse();
        departament = new DepartamentName();
        unit = new UnitName();
        this.state = false;
    }

    public Properties(String name, String permissions,String type, Boolean valid, AuthorResponse author, Timestamp lastUpdated, Timestamp dateCreated, DepartamentName departament, UnitName unit, Integer version, Boolean state) {
        this.name = name;
        this.type = type;
        this.author = author;
        this.lastUpdated = lastUpdated;
        this.dateCreated = dateCreated;
        this.departament = departament;
        this.unit = unit;
        this.version = version;
        this.valid = valid;
        this.permissions = permissions;
        this.state = state;
    }

    public void setPermissions(String p){
        this.permissions = p;
    }
    public String getPermissions(){
        return this.permissions;
    }
    public Boolean getValid() {
        return this.valid;
    }

    public void setValid(Boolean valid) {
        this.valid = valid;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public AuthorResponse getAuthor() {
        return this.author;
    }

    public void setAuthor(AuthorResponse author) {
        this.author = author;
    }

    public Timestamp getLastUpdated() {
        return this.lastUpdated;
    }

    public void setLastUpdated(Timestamp lastUpdated) {
        this.lastUpdated = lastUpdated;
    }

    public Timestamp getDateCreated() {
        return this.dateCreated;
    }

    public void setDateCreated(Timestamp dateCreated) {
        this.dateCreated = dateCreated;
    }

    public DepartamentName getDepartament() {
        return this.departament;
    }

    public void setDepartament(DepartamentName departament) {
        this.departament = departament;
    }

    public UnitName getUnit() {
        return this.unit;
    }

    public void setUnit(UnitName unit) {
        this.unit = unit;
    }

    public Integer getVersion() {
        return this.version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Boolean getState() {
        return state;
    }
    public void setState(Boolean state) {
        this.state = state;
    }
}
