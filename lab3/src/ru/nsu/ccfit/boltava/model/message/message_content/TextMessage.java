package ru.nsu.ccfit.boltava.model.message.message_content;

import ru.nsu.ccfit.boltava.view.IChatMessageRenderer;

public class TextMessage extends ChatMessage {

    private String messageText;

    public TextMessage(String author, String text) {
        super(author);
        messageText = text;
    }

    public String getMessageText() {
        return messageText;
    }

    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }

    @Override
    public void render(IChatMessageRenderer renderer) {
        renderer.render(this);
    }
}
