package com.adelchik.Core.services;

import com.adelchik.Core.MyObject;
import com.adelchik.Core.db.entities.TextEntity;
import com.adelchik.Core.db.repository.TextRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class FileExtractingService {

    @Autowired
    private TextRepository repo;

    public MyObject[] returnProcessedFile(String id){

        TextEntity entity = repo.findByStringId(id);

        if(entity.getStatus().equals("READY")){
            return convertStringToArray(entity.getProcessedtext());
        }

        return null;
    }

    public String getStatus(String id){
        return repo.findByStringId(id).getStatus();
    }

    private MyObject[] convertStringToArray(String processedText){

        String[] wordAndFreqRaw = processedText.substring(2).split("}\\{");
        MyObject[] myObjectArray = new MyObject[wordAndFreqRaw.length];
        int counter = 0;

        for(String rawPair : wordAndFreqRaw){

            String[] separatedWordAndFreq = rawPair.split(" : ");
            MyObject myObject = new MyObject(separatedWordAndFreq[0], Integer.parseInt(separatedWordAndFreq[1]));

            myObjectArray[counter] = myObject;

            counter++;
        }

        return myObjectArray;
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

    private HashMap<String, Integer> orderWordsByFrequency(HashMap<String, Integer> originalMap){

        List<Map.Entry<String, Integer>> list = new LinkedList<>(originalMap.entrySet());

        list.sort(new Comparator<Map.Entry<String, Integer>>() {
            @Override
            public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
                return (o2.getValue()).compareTo(o1.getValue());
            }
        });

        HashMap<String, Integer> sortedMap = new LinkedHashMap<>();

        for(Map.Entry<String, Integer> entry : list){
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }



}
