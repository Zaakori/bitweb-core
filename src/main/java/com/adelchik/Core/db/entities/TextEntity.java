package com.adelchik.Core.db.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TEXT")
public class TextEntity {

    @Id
    private int id;
    private String status;
    private String originaltext;
    private String processedtext;

    public TextEntity() {
    }

    public TextEntity(int id, String originaltext) {
        this.id = id;
        this.originaltext = originaltext;
    }

    public TextEntity(int id, String status, String originaltext, String processedtext) {
        this.id = id;
        this.status = status;
        this.originaltext = originaltext;
        this.processedtext = processedtext;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getOriginaltext() {
        return originaltext;
    }

    public void setOriginaltext(String originaltext) {
        this.originaltext = originaltext;
    }

    public String getProcessedtext() {
        return processedtext;
    }

    public void setProcessedtext(String processedtext) {
        this.processedtext = processedtext;
    }
}