package com.app.notesManagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;
import java.util.Map;
@Document(collection = "words")
public class Words {
    @Id
    String id;


    @DBRef
    private Texts text;

    private String textId;
    @Field(name = "words_count")
    Map wordsCount;

    LocalDate systemCreationDate;

    @JsonIgnore
    public LocalDate getSystemCreationDate() {
        return systemCreationDate;
    }

    public void setSystemCreationDate(LocalDate systemCreationDate) {
        this.systemCreationDate = systemCreationDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Texts getText() {
        return text;
    }

    public void setText(Texts text) {
        this.text = text;
    }

    public Map getWordsCount() {
        return wordsCount;
    }

    public void setWordsCount(Map wordsCount) {
        this.wordsCount = wordsCount;
    }

}
