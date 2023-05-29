package com.msi.gestordocumental.controllers;

import com.msi.gestordocumental.entities.*;
import com.msi.gestordocumental.services.OfficeService;
import com.msi.gestordocumental.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RequestMapping(value = "/api/v1/office")
@RestController
public class OfficeController {
        @Autowired
        private OfficeService service;
        @Autowired
        private UserService userService;

        @PostMapping
        public Office addOffice(@RequestBody Office office){
            return service.saveOffice(office);
        }

        @GetMapping
        public List<Office> findAllOffices(){
            return service.getAllOffices();
        }

        @GetMapping(path = "{id}")
        public Office findOfficeById(@PathVariable("id") int id) {
            return service.getOffice(id);
        }

        @PostMapping(path = "/properties")
        public OfficeProperties findOfficeById(@RequestBody OfficeProperties officeProperties) {
            User currentUser = userService.getUser(officeProperties.getUser().getIdUser());
            List<Office> offices = service.getAllOfficeByUser(currentUser);
            for(int i = 0; i < offices.size(); i++){
                if(offices.get(i).getIdOffice() == officeProperties.getOffice().getIdOffice()){
                    return new OfficeProperties(offices.get(i).getAuthor().getIdUser() == currentUser.getIdUser() ? "autor" : "editor", offices.get(i), currentUser);
                }
            }
            throw new ResourceNotFoundException(); // El oficio no existe o no tiene permisos sobre este oficio
        }

        @PostMapping(path = "search")
        public List<Office> getAllOfficeByUser(@RequestBody Office office) {
            return service.getAllOffices();
        }


    @PostMapping(path = "user")
        public List<Office> getAllOfficeByUser(@RequestBody User user) {
            return service.getAllOfficeByUser(user);
        }

        @PostMapping(path = "author-user")
        public List<Office> getAllMyOffice(@RequestBody User user) {
            return service.getAllMyOffice(user.getIdUser());
        }

        @PostMapping(path = "departament")
        public List<Office> getAllOfficeByDepartament(@RequestBody Departament departament) {
            return service.getAllOfficeByDepartament(departament.getIdDepartament());
        }

        @PostMapping(path = "unit")
        public List<Office> getAllOfficeByUnit(@RequestBody Unit unit) {
            return service.getAllOfficeByUnit(unit.getIdUnit());
        }
        @PostMapping(path = "/desactivate")
        public OfficeProperties deactivateOfficeById(@RequestBody OfficeProperties officeProperties) {
            User currentUser = userService.getUser(officeProperties.getUser().getIdUser());
            List<Office> offices = service.getAllOfficeByUser(currentUser);
            for(int i = 0; i < offices.size(); i++){ // Validar que el usuario tiene los permisos sobre el documento
                if(offices.get(i).getIdOffice() == officeProperties.getOffice().getIdOffice()){
                    if(service.deactivateOfficeById(offices.get(i).getIdOffice())) {
                        offices.get(i).setState(false);
                        return new OfficeProperties(offices.get(i).getAuthor().getIdUser() == currentUser.getIdUser() ? "autor" : "editor", offices.get(i), currentUser);
                    }
                }
            }
            throw new ResourceNotFoundException(); // El oficio no existe o no tiene permisos sobre este oficio
        }

    @GetMapping(path = "/desactivatexx/{idDoc}/{idUser}")
    public OfficeProperties deactivateOfficeByIdxx(@PathVariable("idDoc") Integer idDoc, @PathVariable("idUser") Integer idUser) {
        User currentUser = userService.getUser(String.valueOf(idUser));
        List<Office> offices = service.getAllOfficeByUser(currentUser);
        for(int i = 0; i < offices.size(); i++){ // Validar que el usuario tiene los permisos sobre el documento
                if(service.deactivateOfficeById(idDoc)) {
                    offices.get(i).setState(false);
                    return new OfficeProperties(offices.get(i).getAuthor().getIdUser() == currentUser.getIdUser() ? "autor" : "editor", offices.get(i), currentUser);
                }
        }
        throw new ResourceNotFoundException(); // El oficio no existe o no tiene permisos sobre este oficio
    }



        @PostMapping(path = "filter")
        public List<Office> filterOffice(@RequestBody FilterOffice filterOffice) {
            return service.filterOfficeOrder(filterOffice);
        }

}
