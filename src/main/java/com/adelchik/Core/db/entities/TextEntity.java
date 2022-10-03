package com.adelchik.Core.db.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class TextEntity {

    @Id
    private int id;
    private String text;

    public TextEntity() {
    }

    public TextEntity(int id, String text) {
        this.id = id;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}