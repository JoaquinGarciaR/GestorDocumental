package com.msi.gestordocumental.services;

import com.msi.gestordocumental.entities.CoAuthorPermission;
import com.msi.gestordocumental.repos.CoAuthorPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CoAuthorPermissionService {
    @Autowired
    private CoAuthorPermissionRepository repository;

    public void saveCoAuthorPermission(CoAuthorPermission obj){
        repository.save(obj);
    }

    public List<CoAuthorPermission> getAllCoAuthorPermissions(){
        return repository.findAll();
    }

    public List<CoAuthorPermission> getAllCoAuthorPermissionsbyOffice(Integer id){
        return repository.findAllbyOffice(id);
    }

    public CoAuthorPermission getCoAuthorPermission(Long id){
        return repository.findById(id).orElse(null);
    }

    public List<String> getCoAuthorList(Integer id){
        return repository.findCoAuthorsDocID(id);
    }

    public void deleteRegisters(Integer id){
        repository.deleteRowsByDocumentID(id);
    }
    public Integer getCount(Integer id){
        return repository.countRowsDocumentID(id);
    }

    public Integer getAllDepartments(Integer id){
        return repository.getDepartmentID(id);
    }
    public Integer getUnits(Integer id){
        return repository.getUnitID(id);
    }

    public Boolean editorPermission(Integer docu, String idUser){
        if(repository.countPermisson(docu, idUser) == 1){ // Si encuentra un select entonces si es
            return true;
        }
        return false;
    }
}
