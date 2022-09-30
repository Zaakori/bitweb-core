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

    public TextEntity(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}