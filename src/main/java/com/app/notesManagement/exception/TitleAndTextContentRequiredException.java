package com.app.notesManagement.exception;

import com.app.notesManagement.model.Notes;

public class TitleAndTextContentRequiredException  extends RuntimeException{
    public TitleAndTextContentRequiredException() {
                super("Text content and Title are required attributes");
        }

}
