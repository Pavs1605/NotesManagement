package com.app.notesManagement.repository;

import com.app.notesManagement.model.Notes;
import com.app.notesManagement.model.Texts;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TextsRepository extends MongoRepository<Texts, String> {
    Page<Texts> findAll(Pageable pageable);

}
