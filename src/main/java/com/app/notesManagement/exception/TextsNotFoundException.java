package com.app.notesManagement.exception;

public class TextsNotFoundException extends RuntimeException{
    public TextsNotFoundException(String textId) {
        super("Text not found with id: " + textId);
    }

    public TextsNotFoundException() {
        super("Text not found or is empty");
    }

}
