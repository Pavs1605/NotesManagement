package com.app.notesManagement.repository;

import com.app.notesManagement.model.Words;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WordsRepository extends MongoRepository<Words, String> {
    List<Words> findAll();


}
