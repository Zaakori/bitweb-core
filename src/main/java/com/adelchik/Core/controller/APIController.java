package com.adelchik.Core.controller;

import com.adelchik.Core.MyResponse;
import com.adelchik.Core.db.entities.TextEntity;
import com.adelchik.Core.services.FileExtractingService;
import com.adelchik.Core.services.FileInsertingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
@RequestMapping("/api/")
public class APIController {

    @Autowired
    private FileInsertingService insertingService;

    @Autowired
    private FileExtractingService extractingService;


    @PostMapping(value = "/upload")
    public ResponseEntity<String> createTextEntity(@RequestBody MultipartFile file) {

        String id = insertingService.processIncomingFile(file);

        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @GetMapping(value = "/extract")
    public ResponseEntity<MyResponse> extractProcessedText(@RequestParam("message") String message){

        MyResponse response = new MyResponse(extractingService.getStatus(message), extractingService.returnProcessedFile(message));

        return new ResponseEntity<>(response, HttpStatus.OK);

    }
}
