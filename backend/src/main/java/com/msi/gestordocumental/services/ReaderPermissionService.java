package com.msi.gestordocumental.services;

import com.msi.gestordocumental.entities.ReaderPermission;
import com.msi.gestordocumental.repos.ReaderPermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReaderPermissionService {
    @Autowired
    private ReaderPermissionRepository repository;

    public void saveReaderPermission(ReaderPermission obj){
        repository.save(obj);
    }

    public List<ReaderPermission> getAllReaderPermissions(){
        return repository.findAll();
    }

    public ReaderPermission getReaderPermission(Long id){
        return repository.findById(id).orElse(null);
    }

    public List<String> getReadersList(Integer id){
        return repository.findReadersDocID(id);
    }

    public Integer getAllDepartments(Integer id){
        return repository.getDepartmentID(id);
    }
    public Integer getUnits(Integer id){
        return repository.getUnitID(id);
    }

    public void deleteRegisters(Integer id){
       repository.deleteRowsByDocumentID(id);
    }

    public Integer getCount(Integer id){
        return repository.countRowsDocumentID(id);
    }
    public List<ReaderPermission> getAllReadersPermissionsbyOffice(Integer id){
        return repository.findAllbyOffice(id);
    }
}
