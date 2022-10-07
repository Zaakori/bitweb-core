package com.adelchik.Core.db.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TEXT")
public class TextEntity {

    @Id
    private String id;
    private String status;
    private String processedtext;

    public TextEntity() {
    }

    public TextEntity(String id, String status) {
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getProcessedtext() {
        return processedtext;
    }

    public void setProcessedtext(String processedtext) {
        this.processedtext = processedtext;
    }
}