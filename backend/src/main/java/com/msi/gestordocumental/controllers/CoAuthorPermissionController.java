package com.msi.gestordocumental.controllers;

import com.msi.gestordocumental.entities.CoAuthorPermission;
import com.msi.gestordocumental.services.CoAuthorPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/api/v1/co-author-permission")
@RestController
public class CoAuthorPermissionController {
        @Autowired
        private CoAuthorPermissionService service;

        @PostMapping
        public void addCoAuthorPermission(@RequestBody CoAuthorPermission CoAuthorPermission){
             service.saveCoAuthorPermission(CoAuthorPermission);
        }

        @GetMapping
        public List<CoAuthorPermission> findAllCoAuthorPermissions(){
            return service.getAllCoAuthorPermissions();
        }

        @GetMapping(path = "{id}")
        public CoAuthorPermission findCoAuthorPermissionById(@PathVariable("id") Long id) {
            return service.getCoAuthorPermission(id);
        }
}
