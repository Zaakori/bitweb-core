package com.adelchik.Core.frontendConnectivity;

import com.adelchik.Core.mqComponents.RMQProducer;
import com.adelchik.Core.db.entities.TextEntity;
import com.adelchik.Core.db.repository.TextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class APIController {

    @Autowired
    private TextRepository repo;

    private RMQProducer producer;

    public APIController(RMQProducer producer) {
        this.producer = producer;
    }

    @GetMapping
    public ResponseEntity<List<TextEntity>> getAllTextEntities() {
        List<TextEntity> returnedTextEntities = repo.findAll();
        return new ResponseEntity<>(returnedTextEntities, HttpStatus.OK);
    }

    @GetMapping("{id}")
    public ResponseEntity<TextEntity> getTextEntityById(@PathVariable Long id) {
        if (repo.existsById(id)) {
            TextEntity textEntity = repo.findById(id).get();
            return new ResponseEntity<>(textEntity, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    public ResponseEntity<TextEntity> createTextEntity(@RequestBody TextEntity textEntity) {
        repo.save(textEntity);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    // for RabbitMQ
    // example: http://localhost:8080/publish?message=hello
    @GetMapping("/publish")
    public ResponseEntity<String> sendMessage(@RequestParam("message") String message){
        producer.sendMessage(message);
        return ResponseEntity.ok("Message sent to RabbitMQ ...");
    }
}
