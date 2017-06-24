package ru.nsu.ccfit.boltava.model.message;


import ru.nsu.ccfit.boltava.view.IChatMessageRenderer;

import java.security.Timestamp;

public class TextMessage {

    private String author;
    private Timestamp timestamp;
    private String message;

    public TextMessage() {}

    public TextMessage(String author, String message) {
        this.author = author;
        this.message = message;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void render(IChatMessageRenderer renderer) {
        renderer.render(this);
    }

}
