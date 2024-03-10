package com.app.notesManagement.exception;

public class WordsNotFoundException extends RuntimeException{
    public WordsNotFoundException(String wordId) {
        super("Words not found with id: " + wordId);
    }
    public WordsNotFoundException() {
        super("Words not found for the text");
    }
}
