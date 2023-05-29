package com.msi.gestordocumental.responses;

import java.util.List;

public class accessResponse {
    private Boolean allDepartmentC;
    private Boolean allDepartmentL;
    private Boolean allUnitsC;
    private Boolean allUnitsL;
    private Integer file_id;
    private List<String> usersC;
    private List<String> usersL;
    
    public accessResponse() {
    }
    
    public accessResponse(Boolean allDepartmentC,Boolean allDepartmentL, Boolean allUnitsC, Boolean allUnitsL,Integer file_id, List<String> usersC, List<String> usersL) {
        this.allDepartmentC = allDepartmentC;
        this.allDepartmentL = allDepartmentL;
        this.allUnitsC = allUnitsC;
        this.allUnitsL = allUnitsL;
        this.file_id = file_id;
        this.usersC = usersC;
        this.usersL = usersL;
    }
    public List<String> getUsersC() {
        return usersC;
    }
    public void setUsersC(List<String> usersC) {
        this.usersC = usersC;
    }
    public List<String> getUsersL() {
        return usersL;
    }
    public void setUsersL(List<String> usersL) {
        this.usersL = usersL;
    }
    public Integer getFile_id() {
        return file_id;
    }
    public void setFile_id(Integer file_id) {
        this.file_id = file_id;
    }
    public Boolean getAllDepartmentC() {
        return allDepartmentC;
    }

    public void setAllDepartmentC(Boolean allDepartmentC) {
        this.allDepartmentC = allDepartmentC;
    }
    public Boolean getAllUnitsL() {
        return allUnitsL;
    }

    public void setAllUnitsL(Boolean allUnitsL) {
        this.allUnitsL = allUnitsL;
    }

    public Boolean getAllDepartmentL() {
        return allDepartmentL;
    }

    public void setAllDepartmentL(Boolean allDepartmentL) {
        this.allDepartmentL = allDepartmentL;
    }
    public Boolean getAllUnitsC() {
        return allUnitsC;
    }

    public void setAllUnitsC(Boolean allUnitsC) {
        this.allUnitsC = allUnitsC;
    }


}
