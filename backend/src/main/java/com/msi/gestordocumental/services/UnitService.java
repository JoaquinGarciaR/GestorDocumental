package com.msi.gestordocumental.services;

import com.msi.gestordocumental.entities.Unit;
import com.msi.gestordocumental.repos.UnitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnitService {
    @Autowired
    private UnitRepository repository;

    public Unit saveUnit(Unit obj){
        return repository.save(obj);
    }

    public List<Unit> getAllUnits(){
        return repository.findAll();
    }

    public Unit getUnit(int id){
        return repository.findById(id).orElse(null);
    }

    public List<Unit> getDepartmentUnits(Integer id){
        return repository.findByDepartament(id);
    }
}
