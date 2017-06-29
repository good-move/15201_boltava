package ru.nsu.ccfit.boltava.view;

import ru.nsu.ccfit.boltava.model.message.TextMessage;
import ru.nsu.ccfit.boltava.model.message.event.UserJoinedChatEvent;
import ru.nsu.ccfit.boltava.model.message.event.UserLeftChatEvent;


public interface IChatMessageRenderer {

    void render(TextMessage msg);
    void render(UserLeftChatEvent msg);
    void render(UserJoinedChatEvent msg);

}
