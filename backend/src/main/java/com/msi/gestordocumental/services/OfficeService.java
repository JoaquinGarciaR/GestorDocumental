package com.msi.gestordocumental.services;

import com.msi.gestordocumental.entities.Office;
import com.msi.gestordocumental.entities.User;
import com.msi.gestordocumental.repos.OfficeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.msi.gestordocumental.entities.FilterOffice;
import java.util.Collections;
import java.util.List;

@Service
public class OfficeService {
    @Autowired
    private OfficeRepository repository;

    public Office saveOffice(Office obj){
        return repository.save(obj);
    }

    public List<Office> getAllOffices(){
        return repository.findAll();
    }

    public Office getOffice(Integer id){
        return repository.findById(id).orElse(null);
    }

    public List<Office> getAllOfficeByUser(User user) {
        return repository.getAllOfficeByUser(user.getIdUser(), user.getDepartament().getIdDepartament(), user.getUnit().getIdUnit());
    }

    public List<Office> getAllMyOffice(String idUser) {
        return repository.getAllMyOffice(idUser);
    }

    public List<Office> getAllOfficeByDepartament(Integer id_departament) {
        return repository.getAllOfficeByDepartament(id_departament);
    }

    public List<Office> getAllOfficeByUnit(Integer id_unit) {
        return repository.getAllOfficeByUnit(id_unit);
    }
    public Integer getCount(String filename) {
        return repository.getVersionCount(filename);
    }

    public List<Office> getAllVersions(String filename) {
        return repository.getAllOfficeVersionsr(filename);
    }

    public String getOfficeAuthor(String filename) {
        return repository.getOfficeAuthor(filename);
    }

    public Integer getFirstVersionID(String filename) {
        return repository.getfirstVersionID(filename);
    }

    public Boolean deactivateOfficeById(Integer idOffice) {
        Office office = repository.findById(idOffice).orElse(null);
        System.out.println(office.getState());
        if(office != null && office.getState()){
            repository.deactivateOfficeById(idOffice);
            return true;
        }
        return false;
    }

    public List<Office> filterOfficeOrder(FilterOffice filterOffice) {
        List<Office> offices = repository.filterOfficeByDates(filterOffice.parseStartDateToDB(), filterOffice.parseEndDateToDB());
        if(!filterOffice.getOrder_ascendant()){
            Collections.reverse(offices);
        }
        return offices;
    }

    public Integer getLastVersionID(String name, Integer version){
        return repository.getID(name,version);
    }

}