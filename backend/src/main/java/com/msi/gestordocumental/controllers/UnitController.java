package com.msi.gestordocumental.controllers;

import com.msi.gestordocumental.entities.Unit;
import com.msi.gestordocumental.services.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/api/v1/unit")
@RestController
public class UnitController {
        @Autowired
        private UnitService service;

        @PostMapping
        public Unit addUnit(@RequestBody Unit Unit){
            return service.saveUnit(Unit);
        }

        @GetMapping
        public List<Unit> findAllUnits(){
            return service.getAllUnits();
        }

        @GetMapping(path = "{id}")
        public Unit findUnitById(@PathVariable("id") int id) {
            return service.getUnit(id);
        }

        @GetMapping(path = "Units/{departament_id}")
        public List<Unit> findDepartmentUnits(@PathVariable("departament_id") Integer id){
            return service.getDepartmentUnits(id);
        }
}
