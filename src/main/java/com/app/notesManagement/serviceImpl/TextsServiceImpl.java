package com.app.notesManagement.serviceImpl;

import com.app.notesManagement.exception.TextsNotFoundException;
import com.app.notesManagement.exception.WordsNotFoundException;
import com.app.notesManagement.model.Texts;
import com.app.notesManagement.model.Words;
import com.app.notesManagement.repository.TextsRepository;
import com.app.notesManagement.service.TextsService;
import com.app.notesManagement.service.WordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class TextsServiceImpl implements TextsService {
    TextsRepository textsRepository;
    WordsService wordsService;
    @Autowired
    public TextsServiceImpl(TextsRepository textsRepository, WordsService wordsService) {
        this.textsRepository = textsRepository;
        this.wordsService = wordsService;
    }

    @Override
    public Page<Texts> getAllTexts(Pageable pageable) {
        return textsRepository.findAll(pageable);
    }

    @Override
    public Texts getText(String textId) {
        Optional<Texts> op = textsRepository.findById(textId);
         if(op.isPresent())
             return op.get();
         else {
             //todo add log statements
             throw new TextsNotFoundException(textId);
         }
    }

    @Override
    public Texts createText(Texts textObj) {
        //todo add log statements
        // Create a new Words object
        Words savedWords = wordsService.createWord(textObj.getTextContent());

        // Set the reference to Words in Texts
        // Create a new Text object
        textObj.setWords(savedWords);
        textObj.setSystemCreationDate(LocalDate.now());

        // Save the Texts object
       return textsRepository.save(textObj);
    }

    @Override
    public Texts updateText(String textId, Texts textObj) {
        //todo add log statements
        Optional<Texts> op = textsRepository.findById(textId);
        if(op.isPresent()){

            Words words= op.get().getWords();
            if(words != null) {
                String wordId = words.getId();
                //Todo- have a checksum to check if texts has changed and then regenerate the words count
                Words savedWords = wordsService.updateWord(wordId, textObj.getTextContent());
                textObj.setWords(savedWords);
                return textsRepository.save(textObj);
            }
            else{
                //todo add log statements
                throw new WordsNotFoundException();
            }
        }
        else {
            //todo add log statements
            throw new TextsNotFoundException(textId);
        }



    }

    @Override
    public void deleteText(String textId) {
        //ToDo - add log statements
        Optional<Texts> op = textsRepository.findById(textId);
        if(op.isPresent()){
            Words wordObj = op.get().getWords();
            wordsService.deleteWord(wordObj.getId());
            textsRepository.deleteById(textId);
        }
        else {
            //ToDo - add log statements
            throw new TextsNotFoundException(textId);
        }

    }


}
