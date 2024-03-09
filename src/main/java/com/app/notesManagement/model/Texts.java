package com.app.notesManagement.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.cglib.core.Local;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Document(collection = "text")
public class Texts {
    @Id
    String id;

    @NotNull(message = "Text Content should not be null")
    @Field(name = "text_content")
    String textContent;



     @DBRef
     private Words words;

    String wordsUrl;

    LocalDate systemCreationDate;

    @JsonIgnore
    public LocalDate getSystemCreationDate() {
        return systemCreationDate;
    }

    public void setSystemCreationDate(LocalDate systemCreationDate) {
        this.systemCreationDate = systemCreationDate;
    }



    public String getWordsUrl() {
        return id != null ? "/text/" + id + "/words" : null;
    }
    public String getId() {
        return id;
    }



    public Words getWords() {
        return words;
    }

    public void setWords(Words words) {
        this.words = words;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }



}
