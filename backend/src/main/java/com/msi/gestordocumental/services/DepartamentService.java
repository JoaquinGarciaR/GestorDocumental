package com.msi.gestordocumental.services;

import com.msi.gestordocumental.entities.Departament;
import com.msi.gestordocumental.repos.DepartamentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartamentService {
    @Autowired
    private DepartamentRepository repository;

    public Departament saveDepartament(Departament obj){
        return repository.save(obj);
    }

    public List<Departament> getAllDepartaments(){
        return repository.findAll();
    }

    public Departament getDepartament(Integer id){
        return repository.findById(id).orElse(null);
    }
}
