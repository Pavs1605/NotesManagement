package com.app.notesManagement.service;

import com.app.notesManagement.model.Notes;
import com.app.notesManagement.model.Texts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TextsService {
    public Page<Texts> getAllTexts(Pageable pageable);

    public Texts getText(String textId);

    public Texts createText(Texts textObj);

    public Texts updateText(String textId, Texts textObj);

    public void deleteText(String textId);
}
