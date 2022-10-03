package com.adelchik.Core.frontendConnectivity;

import com.adelchik.Core.db.entities.TextEntity;
import com.adelchik.Core.db.repository.TextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Controller
public class FileUploadController {

    @Autowired
    private TextRepository repo;

    private static String UPLOADED_FOLDER = "D:\\ВСЯКОЕ\\PROGRAMMING\\job test-assignments\\BitWeb\\project\\Core\\tempStorage\\";

    // the actual method that saves uploaded file to the DB
    @PostMapping(value = "/api/upload")
    public ResponseEntity<TextEntity> createTextEntity(@RequestBody MultipartFile file) {

        try{

            int tempID = (int) (Math.random() * 100);
            String text = new String(file.getBytes());
            TextEntity textEntity = new TextEntity(tempID, text);

            repo.save(textEntity);

        } catch (IOException e){
            System.out.println("Some problemo in actual upload");
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    //this one is not saving anything to DB, just to some temp folder
    @PostMapping(value = "/api/tempupload")
    public ResponseEntity<TextEntity> submit(@RequestParam("file") MultipartFile file) {
        System.out.println(file.getName());
        System.out.println(file.getSize());

        try{
            // Get the file and save it somewhere
            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

        } catch (IOException e){
            System.out.println("There was a problemo!");
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
