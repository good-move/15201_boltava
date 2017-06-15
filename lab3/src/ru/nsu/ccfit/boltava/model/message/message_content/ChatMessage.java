package ru.nsu.ccfit.boltava.model.message.message_content;

import ru.nsu.ccfit.boltava.model.client.IChatMessageHandler;
import ru.nsu.ccfit.boltava.view.IChatMessageRenderer;

import java.io.Serializable;

// intended to have ancestors like Sticker, Text, Audio, Video, File
public abstract class ChatMessage implements Serializable {

    private final String author;

    ChatMessage(String author) {
        this.author = author;
    }

    public String getAuthor() {
        return author;
    }

    public abstract void handle(IChatMessageHandler handler);

    public abstract void render(IChatMessageRenderer renderer);

}
