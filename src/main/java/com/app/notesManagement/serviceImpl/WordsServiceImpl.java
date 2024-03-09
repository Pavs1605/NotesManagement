package com.app.notesManagement.serviceImpl;

import com.app.notesManagement.model.Words;
import com.app.notesManagement.repository.WordsRepository;
import com.app.notesManagement.service.WordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class WordsServiceImpl implements WordsService {

    WordsRepository wordsRepository;
@Autowired
    public WordsServiceImpl(WordsRepository wordsRepository) {
        this.wordsRepository = wordsRepository;
    }

    @Override
    public List<Words> getAllWords() {
        return wordsRepository.findAll();
    }

    @Override
    public Words getWord(String wordId) {
        Optional<Words> op = wordsRepository.findById(wordId);
                return op.orElse(null);
    }

    @Override
    public Words createWord(String textContent) {
        Map<String, Long> wordsCount = generateWords(textContent);
        Words word = new Words();
        word.setWordsCount(wordsCount);
        word.setSystemCreationDate(LocalDate.now());
        return wordsRepository.save(word);
    }

    @Override
    public Words updateWord(String wordId, String textContent) {
        Optional<Words> op = wordsRepository.findById(wordId);
        if(op.isPresent()){
            Words wordObj = op.get();
            Map<String, Long> wordsCount = generateWords(textContent);
            wordObj.setWordsCount(wordsCount);
            return wordsRepository.save(wordObj);
        }

        return null;

    }

    @Override
    public void deleteWord(String wordId) {
    wordsRepository.deleteById(wordId);
    }

    private Map<String, Long> generateWords(String textContent)
    {
        Map<String, Long> map = new HashMap<>();
        Pattern pattern = Pattern.compile("\\b\\w+\\b");
        Matcher matcher = pattern.matcher(textContent);

        while (matcher.find()) {
            String str = matcher.group();
            map.put(str, map.getOrDefault(str, 1L) + 1);
        }

        return sortHashMapByValues(map);

    }

    private  Map<String, Long> sortHashMapByValues(Map<String, Long> hashMap) {
        // Convert the HashMap to a list of Map.Entry objects
        List<Map.Entry<String, Long>> list = new ArrayList<>(hashMap.entrySet());

        // Sort the list based on the values
        list.sort(new Comparator<Map.Entry<String, Long>>() {
            @Override
            public int compare(Map.Entry<String, Long> o1, Map.Entry<String, Long> o2) {
                return o2.getValue().compareTo(o1.getValue());
            }
        });

        // Put the sorted entries into a new LinkedHashMap to preserve the order
        Map<String, Long> sortedMap = new LinkedHashMap<>();
        for (Map.Entry<String, Long> entry : list) {
            sortedMap.put(entry.getKey(), entry.getValue());
        }

        return sortedMap;
    }

}
