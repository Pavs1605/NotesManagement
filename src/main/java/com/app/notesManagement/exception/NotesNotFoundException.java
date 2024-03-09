package com.app.notesManagement.exception;

public class NotesNotFoundException extends RuntimeException{
    public NotesNotFoundException(String noteId) {
        super("Note not found with id: " + noteId);
    }
}
