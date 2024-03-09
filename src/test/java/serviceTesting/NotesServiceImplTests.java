package serviceTesting;

import com.app.notesManagement.model.Notes;
import com.app.notesManagement.model.Texts;
import com.app.notesManagement.model.Words;
import com.app.notesManagement.model.enums.Tag;
import com.app.notesManagement.repository.NotesRepository;
import com.app.notesManagement.service.NotesService;
import com.app.notesManagement.service.TextsService;
import com.app.notesManagement.service.WordsService;
import com.app.notesManagement.serviceImpl.NotesServiceImpl;
import com.app.notesManagement.serviceImpl.TextsServiceImpl;
import com.app.notesManagement.serviceImpl.WordsServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class NotesServiceImplTests {
    @Mock
    NotesRepository notesRepository;
    @Mock
    TextsServiceImpl textService;

    @Mock
    WordsServiceImpl wordsService;
    @InjectMocks
    NotesServiceImpl notesService;
    private Notes noteObj;
    private Notes notesAfterCreation;
    private Words wordsObj;
    private Texts textsObj;
    @BeforeEach
    void setUp() {
        noteObj = new Notes();
        noteObj.setId("note1");
        noteObj.setTextContent("personal personal personal note note this is ");
        noteObj.setTag(Tag.PERSONAL);
        noteObj.setTitle("Personal Note");
        noteObj.setCreatedDate(LocalDate.now());


        wordsObj = new Words();
        Map<String, Long> hm = new LinkedHashMap<>();
        hm.put("personal", 3L);
        hm.put("note", 2L);
        hm.put("this", 1L);
        hm.put("is", 1L);
        wordsObj.setWordsCount(hm);
        wordsObj.setId("word1");

        textsObj = new Texts();
        textsObj.setId("text1");
        textsObj.setTextContent("personal personal personal note note this is ");
        textsObj.setWords(wordsObj);


        notesAfterCreation = new Notes();
        notesAfterCreation.setId("note1");
        notesAfterCreation.setTag(Tag.PERSONAL);
        notesAfterCreation.setCreatedDate(LocalDate.now());
        notesAfterCreation.setText(textsObj);
        notesAfterCreation.setTitle("Personal Note");

    }
    @Test
    void testCreateNote() {
        when(notesRepository.save(any(Notes.class))).thenReturn(notesAfterCreation);

        Notes result = notesService.createNote(noteObj);

        assertNotNull(result);
        assertNull(result.getTextContent());
        assertEquals(result.getText().getTextContent(), (noteObj.getTextContent()));
    }

    @Test
    void testGetNote() {
        String noteId = "note1";
        when(notesRepository.findById(noteId)).thenReturn(Optional.of(notesAfterCreation));

        Notes result = notesService.getNote(noteId);

        assertNotNull(result);
        assertEquals(result.getTitle(), (noteObj.getTitle()));
    }

}
