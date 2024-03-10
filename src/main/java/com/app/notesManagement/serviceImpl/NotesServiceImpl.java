package com.app.notesManagement.serviceImpl;

import com.app.notesManagement.exception.NotesNotFoundException;
import com.app.notesManagement.exception.TextsNotFoundException;
import com.app.notesManagement.exception.TitleAndTextContentRequiredException;
import com.app.notesManagement.model.Notes;
import com.app.notesManagement.model.Texts;
import com.app.notesManagement.model.Words;
import com.app.notesManagement.repository.NotesRepository;
import com.app.notesManagement.repository.TextsRepository;
import com.app.notesManagement.repository.WordsRepository;
import com.app.notesManagement.service.NotesService;
import com.app.notesManagement.service.TextsService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class NotesServiceImpl implements NotesService {
    NotesRepository notesRepository;

    TextsService  textsService;


    @Autowired
    public NotesServiceImpl(NotesRepository notesRepository, TextsService textsService) {
        this.notesRepository = notesRepository;
        this.textsService = textsService;

    }

    @Override
    public Page<Notes> getAllNotes(Pageable pageable ) {
        return notesRepository.findAll(pageable);


    }

    @Override
    public Notes getNote(String noteId) {

        Optional<Notes> op =  notesRepository.findById(noteId);
        if(op.isPresent())
            return op.get();
        else
            throw new NotesNotFoundException(noteId);
    }

    @Override
    @Transactional
    public Notes createNote(Notes noteObj) {
        //Todo Add log statements
        if(noteObj == null)
            throw new TitleAndTextContentRequiredException();

        String textC = noteObj.getTextContent();
        String title = noteObj.getTitle();

        if( textC == null || textC.isEmpty() || title == null || title.isEmpty())
            throw new TitleAndTextContentRequiredException();

        //Create a new text object which will create the child words object
        Texts textObj = new Texts();
        textObj.setTextContent(noteObj.getTextContent());
        Texts savedText = textsService.createText(textObj);

        // Create a new Notes object and set the references
        Notes note = new Notes();
        note.setTag(noteObj.getTag());
        note.setTitle(noteObj.getTitle());
        note.setCreatedDate(noteObj.getCreatedDate());
        note.setSystemCreationDate(LocalDate.now());
        note.setText(savedText); // Set the text reference

        // Save the Notes object
        return notesRepository.save(note);

    }

    @Override
    public Notes updateNote(String noteId, Notes noteObj) {
        //Todo Add log statements
        Optional<Notes> note = notesRepository.findById(noteId);
       if(note.isPresent()) {
           Notes note1 = note.get();
           Optional.ofNullable(noteObj.getCreatedDate()).ifPresent(note1::setCreatedDate);
           Optional.ofNullable(noteObj.getTitle()).ifPresent(note1::setTitle);
           Optional.ofNullable(noteObj.getTag()).ifPresent(note1::setTag);
           if(noteObj.getText() != null){
               Texts textObj = noteObj.getText();
             Texts savedObj =  textsService.updateText(noteObj.getText().getId(),textObj );
             noteObj.setText(savedObj);
            }

           return notesRepository.save(note1);
       }
       else {
           //Todo Add log statements
           throw new NotesNotFoundException(noteId);
       }


    }

    @Override
    public void deleteNote(String noteId) {
        //Todo Add log statements
        Optional<Notes> op = notesRepository.findById(noteId);
        if(op.isPresent()) {
            Texts text = op.get().getText();
            if(text != null) {
                textsService.deleteText(text.getId());
                notesRepository.deleteById(noteId);
            }
            else
                throw new TextsNotFoundException();
        }            //Todo Add log statements
        else
            throw new NotesNotFoundException(noteId);


    }

    @Override
    public Texts getTextsForNoteId(String noteId)
    {
        Optional<Notes> op = notesRepository.findById(noteId);
        if(op.isPresent())
            return op.get().getText();
        else
            throw new NotesNotFoundException(noteId);

    }
    @Override
    public List<Notes> findByTitleOrTag(String searchTerm)
    {
        return notesRepository.findByTitleOrTag(searchTerm);
    }

    @Override
    public List<Notes> findByTitle(String title)
    {
        return notesRepository.findByTitle(title);
    }


    @Override
    public List<Notes> findByTag(String tag)
    {
        return notesRepository.findByTag(tag);
    }



}
