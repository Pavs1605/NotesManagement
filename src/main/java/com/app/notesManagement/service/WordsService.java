package com.app.notesManagement.service;

import com.app.notesManagement.model.Words;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface WordsService {

    public List<Words> getAllWords();

    public Words getWord(String wordId);

    public Words createWord(String textContent);

    public Words updateWord(String wordId, String textContent);

    public void deleteWord(String wordId);
}
