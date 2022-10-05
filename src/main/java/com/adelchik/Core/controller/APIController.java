package com.adelchik.Core.controller;

import com.adelchik.Core.db.entities.TextEntity;
import com.adelchik.Core.services.RoutingAndProcessingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/")
public class APIController {

    @Autowired
    private RoutingAndProcessingService service;


    @PostMapping(value = "/upload")
    public ResponseEntity<TextEntity> createTextEntity(@RequestBody MultipartFile file) {

        service.processIncomingFile(file);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
