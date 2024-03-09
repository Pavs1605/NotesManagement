package com.app.notesManagement.service;

import com.app.notesManagement.model.Notes;
import com.app.notesManagement.model.Texts;
import com.app.notesManagement.repository.NotesRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;

public interface NotesService {

    public Page<Notes> getAllNotes(Pageable pageable);

    public Notes getNote(String noteId);

    public Notes createNote(Notes noteObj);

    public Notes updateNote(String noteId, Notes noteObj);

    public void deleteNote(String noteId);
    public Texts getTextsForNoteId(String noteId);

    public List<Notes> findByTitleOrTag(String searchTerm);

    public List<Notes> findByTitle(String title);

    public List<Notes> findByTag(String tag);

}
