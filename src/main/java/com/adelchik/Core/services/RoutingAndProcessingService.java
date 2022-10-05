package com.adelchik.Core.services;

import com.adelchik.Core.db.entities.TextEntity;
import com.adelchik.Core.db.repository.TextRepository;
import com.adelchik.Core.mqComponents.RMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class RoutingAndProcessingService {

    @Autowired
    private TextRepository repo;

    private RMQProducer producer;

    public RoutingAndProcessingService(RMQProducer producer) {
        this.producer = producer;
    }

    public void processIncomingFile(MultipartFile file){
        Long tempId = uploadFile(file);
        sendMessageToRMQ(Long.toString(tempId));
    }

    private Long uploadFile(MultipartFile file){

        Long tempId = 0L;

        try{

            tempId = (long) (Math.random() * 100);
            String text = new String(file.getBytes());
            TextEntity textEntity = new TextEntity(tempId, text);

            repo.save(textEntity);

        } catch (IOException e){
            System.out.println("Some problemo in actual upload");
            e.printStackTrace();
        }

        return tempId;
    }

    private void sendMessageToRMQ(String fileId){
        producer.sendMessage(fileId);
    }










}
