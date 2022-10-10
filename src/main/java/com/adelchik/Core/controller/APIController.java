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

    @GetMapping(value = "/extractTable")
    public ResponseEntity<MyObject[]> extractProcessedTextForTable(@RequestParam("message") String message){

        return new ResponseEntity<>(extractingService.returnProcessedFileForTable(message), HttpStatus.OK);

    }

    @GetMapping(value = "/extractWordCloud")
    public ResponseEntity<MyObject[]> extractProcessedTextForWordCloud(@RequestParam("message") String message){

        return new ResponseEntity<>(extractingService.returnProcessedFileForWordCloud(message), HttpStatus.OK);

    }


}
