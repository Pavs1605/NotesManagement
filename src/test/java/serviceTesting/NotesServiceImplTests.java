package serviceTesting;

import com.app.notesManagement.exception.TitleAndTextContentRequiredException;
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
    private Notes notesAfterUpdate;
    private Notes note2;
    @BeforeEach
    void setUp() {
        noteObj = new Notes();
        noteObj.setId("note1");
        noteObj.setTextContent("personal personal personal note note this is ");
        noteObj.setTag(Tag.PERSONAL);
        noteObj.setTitle("Personal Note");
        noteObj.setCreatedDate(LocalDate.now());

        note2 = new Notes();
        note2.setId("note1");
        note2.setTextContent("personal personal personal note note this is ");
        note2.setTag(Tag.PERSONAL);
        note2.setCreatedDate(LocalDate.now());


        Words wordsObj = new Words();
        Map<String, Long> hm = new LinkedHashMap<>();
        hm.put("personal", 3L);
        hm.put("note", 2L);
        hm.put("this", 1L);
        hm.put("is", 1L);
        wordsObj.setWordsCount(hm);
        wordsObj.setId("word1");

        Texts textsObj = new Texts();
        textsObj.setId("text1");
        textsObj.setTextContent("personal personal personal note note this is ");
        textsObj.setWords(wordsObj);


        notesAfterCreation = new Notes();
        notesAfterCreation.setId("note1");
        notesAfterCreation.setTag(Tag.PERSONAL);
        notesAfterCreation.setCreatedDate(LocalDate.now());
        notesAfterCreation.setText(textsObj);
        notesAfterCreation.setTitle("Personal Note");

        Words words1 = new Words();
        Map<String, Long> map = new LinkedHashMap<>();
        map.put("important",2L);
        map.put("note", 2L);
        map.put("this", 1L);
        map.put("is", 1L);
        words1.setWordsCount(map);
        words1.setId("word1");

        Texts texts1 = new Texts();
        texts1.setId("text1");
        texts1.setTextContent("important important  note note this is ");
        texts1.setWords(words1);


        notesAfterUpdate = new Notes();
        notesAfterUpdate.setId("note1");
        notesAfterUpdate.setTag(Tag.IMPORTANT);
        notesAfterUpdate.setCreatedDate(LocalDate.now());
        notesAfterUpdate.setText(texts1);
        notesAfterUpdate.setTitle("Important Note");

    }
    @Test
    void testCreateNote() {
        when(notesRepository.save(any(Notes.class))).thenReturn(notesAfterCreation);

        Notes result = notesService.createNote(noteObj);

        assertNotNull(result);
        assertNull(result.getTextContent());
        assertEquals(result.getText().getTextContent(), (noteObj.getTextContent()));
        assertEquals(result.getText().getWords().getWordsCount(), notesAfterCreation.getText().getWords().getWordsCount());
    }

    @Test
    void testUpdateNote() {
        when(notesRepository.findById("note1")).thenReturn(Optional.ofNullable(notesAfterCreation));
        when(notesRepository.save(any(Notes.class))).thenReturn(notesAfterUpdate);

        Notes result = notesService.updateNote("note1",notesAfterCreation);

        assertEquals(result.getText().getTextContent(), (notesAfterUpdate.getText().getTextContent()));
        assertEquals(result.getText().getWords().getWordsCount(), notesAfterUpdate.getText().getWords().getWordsCount());
    }

    @Test
    void textRequiredFieldsException() {

        assertThrows(TitleAndTextContentRequiredException.class, () -> notesService.createNote(note2));

    }

    @Test
    void testDeleteNote() {
        when(notesRepository.findById("note1")).thenReturn(Optional.ofNullable(notesAfterCreation));

        notesService.deleteNote("note1");
        when(notesRepository.findById("note1")).thenReturn(null);

        assertNull(notesRepository.findById("note1"));
    }



}
