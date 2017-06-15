package ru.nsu.ccfit.boltava.view;

import ru.nsu.ccfit.boltava.model.chat.User;
import ru.nsu.ccfit.boltava.model.message.message_content.TextMessage;

public interface IMessageInputPanelEventListener {

    void onTextMessageSubmit(String textMessage);

}
