package ru.nsu.ccfit.boltava.model.message.response;

import ru.nsu.ccfit.boltava.model.client.IClientMessageHandler;
import ru.nsu.ccfit.boltava.model.message.Response;
import ru.nsu.ccfit.boltava.model.message.message_content.ChatMessage;

import java.util.ArrayList;

public class LoginSuccess extends Response {

    private ArrayList<ChatMessage> chatHistory;

    public LoginSuccess(String sessionId, ArrayList<ChatMessage> chatHistory) {
        super(sessionId);
        this.chatHistory = chatHistory;
    }

    public ArrayList<ChatMessage> getChatHistory() {
        return chatHistory;
    }

    @Override
    public void handle(IClientMessageHandler messageHandler) {
        messageHandler.handle(this);
    }

}
