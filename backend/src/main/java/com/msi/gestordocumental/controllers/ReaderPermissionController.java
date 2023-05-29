package com.msi.gestordocumental.controllers;

import com.msi.gestordocumental.entities.ReaderPermission;
import com.msi.gestordocumental.services.ReaderPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping(value = "/api/v1/reader-permission")
@RestController
public class ReaderPermissionController {
        @Autowired
        private ReaderPermissionService service;

        @PostMapping
        public void addReaderPermission(@RequestBody ReaderPermission ReaderPermission){
            
        }

        @GetMapping
        public List<ReaderPermission> findAllReaderPermissions(){
            return service.getAllReaderPermissions();
        }

        @GetMapping(path = "{id}")
        public ReaderPermission findReaderPermissionById(@PathVariable("id") long id) {
            return service.getReaderPermission(id);
        }
}
