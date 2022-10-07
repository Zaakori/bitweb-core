package com.adelchik.Core.services;

import com.adelchik.Core.db.entities.TextEntity;
import com.adelchik.Core.db.repository.TextRepository;
import com.adelchik.Core.mqComponents.RMQProducer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

@Service
public class RoutingAndProcessingService {

    @Autowired
    private TextRepository repo;

    private RMQProducer producer;

    private String onePartFile;

    private int totalChunkAmount;

    public RoutingAndProcessingService(RMQProducer producer) {
        this.producer = producer;
    }

    public void processIncomingFile(MultipartFile file) {

        ArrayList<String> list = new ArrayList<>();

        String id = generateID();

        try {
            list = splitTextIntoChunks(new String(file.getBytes()));
        } catch (IOException e) {
            e.printStackTrace();
        }

        TextEntity entity = new TextEntity(id, "IN TRANSIT", totalChunkAmount);
        repo.save(entity);

        sendChunksToRMQWithHeaders(id, onePartFile, list);

    }

    public void extractTheProcessedFile(String id){

        HashMap<String, Integer> map = convertStringToMap(repo.findByStringId(id).getProcessedtext());


    }

    private HashMap<String, Integer> convertStringToMap(String processedText){

        String[] wordAndFreqRaw = processedText.substring(2).split("}\\{");
        HashMap<String, Integer> map = new HashMap<>();

        for(String rawPair : wordAndFreqRaw){

            String[] separatedWordAndFreq = rawPair.split(" : ");
            map.put(separatedWordAndFreq[0], Integer.parseInt(separatedWordAndFreq[1]));

        }

        return map;
    }



    private void sendChunksToRMQWithHeaders(String id, String onePartFile, ArrayList<String> list){

        // here I add the id and the info whether it is a one-part file or file in multiple parts
        for(String chunk : list){
            sendMessageToRMQ(id + " " + onePartFile + " " + chunk);
        }

    }

    private ArrayList<String> splitTextIntoChunks(String originalText){

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

        totalChunkAmount = list.size();

        if(list.size() == 1){
            onePartFile = "yes";
        } else {
            onePartFile = "non";
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
