package com.msi.gestordocumental.controllers;

import com.msi.gestordocumental.entities.Departament;
import com.msi.gestordocumental.services.DepartamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/api/v1/departament")
@RestController
public class DepartamentController {
        @Autowired
        private DepartamentService service;

        @PostMapping
        public Departament addDepartament(@RequestBody Departament Departament){
            return service.saveDepartament(Departament);
        }

        @GetMapping
        public List<Departament> findAllDepartaments(){
            return service.getAllDepartaments();
        }

        @GetMapping(path = "{id}")
        public Departament findDepartamentById(@PathVariable("id") int id) {
            return service.getDepartament(id);
        }
}
