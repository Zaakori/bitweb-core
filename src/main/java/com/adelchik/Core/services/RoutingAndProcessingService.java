package com.adelchik.Core.services;

import com.adelchik.Core.db.repository.TextRepository;
import com.adelchik.Core.mqComponents.RMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.UUID;

@Service
public class RoutingAndProcessingService {

    @Autowired
    private TextRepository repo;

    private RMQProducer producer;

    public RoutingAndProcessingService(RMQProducer producer) {
        this.producer = producer;
    }

    public void processIncomingFile(MultipartFile file) {

        ArrayList<String> list = new ArrayList<>();

        try {
            list = splitTextIntoChunks(new String(file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        String ID = generateID();

        for(String chunk : list){
            sendMessageToRMQ(chunk);
        }

    }

    private static ArrayList<String> splitTextIntoChunks(String originalText){

        int maxLengthInKiloBytes = 500;
        int maxLength = maxLengthInKiloBytes * 1000;
        ArrayList<String> list = new ArrayList<>();
        byte[] byteArray = originalText.getBytes(StandardCharsets.UTF_8);
        int originalTextLength = originalText.length();
        int currentCharIndex = 0;


        while(true){

            if(currentCharIndex + maxLength >= originalTextLength){

                int lastChunkLength = originalTextLength - currentCharIndex;

                String chunk = new String(byteArray, currentCharIndex, lastChunkLength, Charset.defaultCharset());
                list.add(chunk);

                break;
            }

            int offset = 0;

            while(true){

                char lastChar = originalText.charAt(currentCharIndex + maxLength + offset);

                if((lastChar >= 65 && lastChar <= 90) || (lastChar >= 97 && lastChar <= 122) || (lastChar == 39) || (lastChar == 45)){
                    offset++;
                } else {
                    break;
                }
            }

            String chunk = new String(byteArray, currentCharIndex, maxLength + offset, Charset.defaultCharset());
            list.add(chunk);

            currentCharIndex = currentCharIndex + maxLength + offset;
        }

        return list;
    }

    private String generateID(){
        return UUID.randomUUID().toString();
    }

    private void sendMessageToRMQ(String message){
        producer.sendMessage(message);
    }
}
