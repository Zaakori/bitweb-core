package com.adelchik.Core.controller;

import com.adelchik.Core.MyObject;
import com.adelchik.Core.services.FileExtractingService;
import com.adelchik.Core.services.FileInsertingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<MyObject[]> extractProcessedText(@RequestParam("message") String message){

        return new ResponseEntity<>(extractingService.returnProcessedFile(message), HttpStatus.OK);

    }
}
