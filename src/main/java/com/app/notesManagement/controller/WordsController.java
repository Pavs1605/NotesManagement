package com.app.notesManagement.controller;

import com.app.notesManagement.model.Words;
import com.app.notesManagement.service.WordsService;

import org.springframework.beans.factory.annotation.Autowired;
;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/words")
public class WordsController {
    WordsService wordsService;
    @Autowired
    public WordsController(WordsService wordsService) {
        this.wordsService = wordsService;
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getAllWords() {

        List<Words> resultPage = wordsService.getAllWords();
        Map<String, Object> response = new HashMap<>();
        response.put("words", resultPage);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping(value="/{wordId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Words getWords(@PathVariable("wordId") String wordId) {
        return wordsService.getWord(wordId);
    }


}
