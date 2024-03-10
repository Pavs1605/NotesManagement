package com.app.notesManagement.controller;

import com.app.notesManagement.model.Texts;
import com.app.notesManagement.service.TextsService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/texts")
public class TextsController {
    TextsService textsService;
    @Autowired
    public TextsController(TextsService textsService) {
        this.textsService = textsService;
    }
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String, Object>> getAllTexts(@RequestParam(defaultValue = "0") int page,
                                                           @RequestParam(defaultValue = "10") int size) {
        Sort sortByDateDesc = Sort.by(Sort.Direction.DESC, "systemCreationDate");
        PageRequest pageable = PageRequest.of(page, size, sortByDateDesc);
        Page<Texts> resultPage = textsService.getAllTexts(pageable);
        Map<String, Object> response = new HashMap<>();
        response.put("texts", resultPage.getContent());
        response.put("currentPage", resultPage.getPageable().getPageNumber());
        response.put("currentSize", resultPage.getSize());
        response.put("totalPages", resultPage.getTotalPages());
        response.put("remainingElements", resultPage.getTotalElements() - (long) resultPage.getNumber() * resultPage.getSize());
        response.put("totalElements", resultPage.getTotalElements());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    @GetMapping(value="/{textId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Texts getTexts(@PathVariable("textId") String textId) {
        return textsService.getText(textId);
    }
    @PutMapping(value="/{textId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Texts updateText(@PathVariable("textId") String textId, @RequestBody Texts textObj) {
        return textsService.updateText(textId, textObj);
    }

}
