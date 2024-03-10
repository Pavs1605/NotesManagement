    package com.app.notesManagement.controller;

    import com.app.notesManagement.model.Notes;
    import com.app.notesManagement.model.Texts;
    import com.app.notesManagement.model.Words;
    import com.app.notesManagement.service.NotesService;
    import com.app.notesManagement.service.TextsService;
    import com.app.notesManagement.service.WordsService;
    import jakarta.servlet.http.HttpServletRequest;
    import jakarta.validation.Valid;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.data.domain.Page;
    import org.springframework.data.domain.PageRequest;
    import org.springframework.data.domain.Pageable;
    import org.springframework.data.domain.Sort;
    import org.springframework.data.repository.query.Param;
    import org.springframework.data.web.PageableDefault;
    import org.springframework.http.HttpStatus;
    import org.springframework.http.MediaType;
    import org.springframework.http.ResponseEntity;
    import org.springframework.validation.BindingResult;
    import org.springframework.web.bind.annotation.*;
    import org.springframework.web.bind.annotation.RequestBody;
    import org.springframework.web.bind.annotation.RequestMapping;
    import org.springframework.web.bind.annotation.RestController;

    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    @RestController
    @RequestMapping("/notes")
    public class NotesController {


        NotesService notesService;
        TextsService textsService;
        WordsService wordsService;


        @Autowired
        public NotesController(NotesService notesService, TextsService textsService, WordsService wordsService) {
            this.notesService = notesService;
            this.textsService = textsService;
            this.wordsService = wordsService;


        }

        @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<Map<String, Object>> getAllNotes(@RequestParam(defaultValue = "0") int page,
                                                               @RequestParam(defaultValue = "10") int size) {

            Sort sortByDateDesc = Sort.by(Sort.Direction.DESC, "systemCreationDate");
            PageRequest pageable = PageRequest.of(page, size, sortByDateDesc);
            Page<Notes> resultPage = notesService.getAllNotes(pageable);

            Map<String, Object> response = new HashMap<>();
            response.put("notes", resultPage.getContent());
            response.put("currentPage", resultPage.getPageable().getPageNumber());
            response.put("currentSize", resultPage.getSize());
            response.put("totalPages", resultPage.getTotalPages());
            response.put("totalElements", resultPage.getTotalElements());

            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        @GetMapping(value="/{noteId}", produces = MediaType.APPLICATION_JSON_VALUE)
        public Notes getNotes(@PathVariable("noteId") String noteId) {
            return notesService.getNote(noteId);
        }
        @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
        public ResponseEntity<?> createNotes(@Valid @RequestBody Notes noteObj, BindingResult bindingResult) {

            Notes savedNote = notesService.createNote(noteObj);
            return ResponseEntity.ok(savedNote);
        }
        @PutMapping(value="/{noteId}", produces = MediaType.APPLICATION_JSON_VALUE)
        public Notes updateNote(@PathVariable("noteId") String noteId, @RequestBody Notes noteObj) {
            return notesService.updateNote(noteId, noteObj);
        }


        @GetMapping("/search")
        public ResponseEntity<List<Notes>> searchNotesByTitleOrTag(@Param("searchTerm") String searchTerm) {
            //ToDo - Pagination for search
            List<Notes> matchingNotes = notesService.findByTitleOrTag(searchTerm);

            if (matchingNotes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(matchingNotes, HttpStatus.OK);
            }
        }

        @GetMapping("/search/")
        public ResponseEntity<List<Notes>> searchNotesByTag(@Param("tag") String tag) {
            //ToDo - Pagination for search
            List<Notes> matchingNotes = notesService.findByTag(tag);

            if (matchingNotes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(matchingNotes, HttpStatus.OK);
            }
        }

        @GetMapping("/search/")
        public ResponseEntity<List<Notes>> searchNotesByTitle(@Param("title") String title) {
            //ToDo - Pagination for search
            List<Notes> matchingNotes = notesService.findByTitle(title);

            if (matchingNotes.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>(matchingNotes, HttpStatus.OK);
            }
        }
        @DeleteMapping("{noteId}")
        public void deleteNote(@PathVariable("noteId") String noteId) {
            notesService.deleteNote(noteId);
        }

        @GetMapping("/{noteId}/texts")
        public Texts getTextsForNote(@PathVariable("noteId") String noteId) {
            // Fetch texts associated with the given noteId
            Texts texts = notesService.getTextsForNoteId(noteId);

           return texts;
        }

        @GetMapping("/{noteId}/texts/{textId}/words")
        public Words getWordsForTexts(@PathVariable("noteId") String noteId, @PathVariable("textId") String textId) {
            // Fetch texts associated with the given noteId
            Texts texts = notesService.getTextsForNoteId(noteId);
           return textsService.getText(textId).getWords();

        }

        @PutMapping("/{noteId}/texts/{textId}")
        public Texts updateTextsInNote(@PathVariable("noteId") String noteId, @PathVariable("textId") String textId, @RequestBody Texts textObj) {
            // Fetch texts associated with the given noteId

            return textsService.updateText(textId, textObj);

        }


    }
