package com.app.notesManagement.model;


import com.app.notesManagement.model.enums.ConstantsUtils;
import com.app.notesManagement.model.enums.Tag;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Document(collection = "notes")
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class Notes {
    @Id
    String id;

    String textContent;
    @JsonIgnore
    @NotNull(message = "Text Content should not be null")
    @DBRef
    private Texts text;

    @Field(name = "tag")
    Tag tag;
    @Field(name = "created_date")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd/MM/yyyy")
    LocalDate createdDate;
    @Field(name = "title")
    @NotNull(message = "Title should not be null")
    String title;

    String textUrl;
    @Field(name = "system_creation_date")
    LocalDate systemCreationDate;

    @JsonIgnore
    public LocalDate getSystemCreationDate() {
        return systemCreationDate;
    }

    public void setSystemCreationDate(LocalDate systemCreationDate) {
        this.systemCreationDate = systemCreationDate;
    }

    public String getTextUrl() {
        return id != null ? "/" + ConstantsUtils.NOTES_STR + "/" + id +  "/" + ConstantsUtils.TEXTS_STR  : null;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Tag getTag() {
        return tag;
    }

    public void setTag(Tag tag) {
        this.tag = tag;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public Texts getText() {
        return text;
    }

    public void setText(Texts text) {
        this.text = text;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }
}
