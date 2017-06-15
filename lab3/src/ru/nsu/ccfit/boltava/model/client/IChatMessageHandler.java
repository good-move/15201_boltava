package ru.nsu.ccfit.boltava.model.client;

import ru.nsu.ccfit.boltava.model.message.message_content.TextMessage;

public interface IChatMessageHandler {

    void handle(TextMessage msg);

}
