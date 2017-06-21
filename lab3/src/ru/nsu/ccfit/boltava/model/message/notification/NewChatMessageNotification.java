package ru.nsu.ccfit.boltava.model.message.notification;

import ru.nsu.ccfit.boltava.model.client.IClientMessageHandler;
import ru.nsu.ccfit.boltava.model.message.Notification;
import ru.nsu.ccfit.boltava.model.message.message_content.ChatMessage;

public class NewChatMessageNotification extends Notification {

    private String sender;
    private ChatMessage content;

    public NewChatMessageNotification(String sessionId, ChatMessage content, String sender) {
        this.sessionId = sessionId;
        this.content = content;
        this.sender = sender;
    }

    public String getSender() {
        return sender;
    }

    public ChatMessage getContent() {
        return content;
    }

    @Override
    public void handle(IClientMessageHandler messageHandler) {
        messageHandler.handle(this);
    }

}
