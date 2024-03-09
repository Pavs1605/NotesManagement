package com.app.notesManagement.repository;

import com.app.notesManagement.model.Notes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotesRepository extends MongoRepository<Notes, String> {

    Page<Notes> findAll(Pageable pageable);

    @Query(value = "{ 'title' : {$regex:?0,$options:'i'} }")
    List<Notes> findByTitle(String title);

    @Query(value = "{ 'tag' : {$regex:?0,$options:'i'} }")
    List<Notes> findByTag(String title);

    @Query(value = "{ $or: [ { 'title' : {$regex:?0,$options:'i'} }, { 'tag' : {$regex:?0,$options:'i'} } ] }")
    List<Notes> findByTitleOrTag(String searchTerm);




}
