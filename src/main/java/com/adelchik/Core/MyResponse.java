package com.adelchik.Core;

import java.util.HashMap;

public class MyResponse {

    private String status;
    private HashMap<String, Integer> map;

    public MyResponse(String status, HashMap<String, Integer> map) {
        this.status = status;
        this.map = map;
    }

    public String getStatus() {
        return status;
    }

    public HashMap<String, Integer> getMap() {
        return map;
    }
}
