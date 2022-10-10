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

    public MyObject[] returnProcessedFileForTable(String id){

        TextEntity entity = repo.findByStringId(id);

        if(entity.getStatus().equals("READY")){
            return convertStringToArray(entity.getProcessedtext(), Integer.MAX_VALUE);
        }

        return null;
    }

    public MyObject[] returnProcessedFileForWordCloud(String id){

        TextEntity entity = repo.findByStringId(id);

        if(entity.getStatus().equals("READY")){
            return convertStringToArray(entity.getProcessedtext(), 100);
        }

        return null;
    }

    public String getStatus(String id){
        return repo.findByStringId(id).getStatus();
    }

    private MyObject[] convertStringToArray(String processedText, int maxAmountOfWords){

        String[] wordAndFreqRaw = processedText.substring(2).split("}\\{");
        MyObject[] myObjectArray;
        int counter = 0;

        if((maxAmountOfWords == Integer.MAX_VALUE) || (wordAndFreqRaw.length <= maxAmountOfWords)){
            myObjectArray = new MyObject[wordAndFreqRaw.length];
        } else {
            myObjectArray = new MyObject[maxAmountOfWords];
        }

        for(String rawPair : wordAndFreqRaw){

            String[] separatedWordAndFreq = rawPair.split(" : ");
            MyObject myObject = new MyObject(separatedWordAndFreq[0], Integer.parseInt(separatedWordAndFreq[1]));

            myObjectArray[counter] = myObject;

            counter++;

            if(counter >= maxAmountOfWords){
                break;
            }
        }

        return myObjectArray;
    }

}
