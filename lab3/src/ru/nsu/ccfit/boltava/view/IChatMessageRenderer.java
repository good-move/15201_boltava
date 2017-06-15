package ru.nsu.ccfit.boltava.view;

import ru.nsu.ccfit.boltava.model.message.message_content.TextMessage;


public interface IChatMessageRenderer {

    void render(TextMessage msg);

}
