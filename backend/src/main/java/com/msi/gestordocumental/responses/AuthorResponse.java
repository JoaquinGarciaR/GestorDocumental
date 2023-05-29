package com.msi.gestordocumental.responses;

public class AuthorResponse {
    private String name;
    private String lastName1;
    private String lastName2;


    public AuthorResponse() {
    }

    public AuthorResponse(String name, String lastName1, String lastName2) {
        this.name = name;
        this.lastName1 = lastName1;
        this.lastName2 = lastName2;
    }


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName1() {
        return this.lastName1;
    }

    public void setLastName1(String lastName1) {
        this.lastName1 = lastName1;
    }

    public String getLastName2() {
        return this.lastName2;
    }

    public void setLastName2(String lastName2) {
        this.lastName2 = lastName2;
    }

}
