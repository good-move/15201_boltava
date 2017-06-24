package ru.nsu.ccfit.boltava.model.message.request;

import ru.nsu.ccfit.boltava.model.message.MessageFactory;
import ru.nsu.ccfit.boltava.model.message.Request;
import ru.nsu.ccfit.boltava.model.message.message_content.ChatMessage;
import ru.nsu.ccfit.boltava.model.server.IServerMessageHandler;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(
        factoryClass = MessageFactory.class,
        factoryMethod = "getLoginRequest"
)
public class SendChatMessage extends Request {

    private ChatMessage content;

    public SendChatMessage(String user, ChatMessage content) {
        super(user);
        this.content = content;
    }

    public ChatMessage getContent() {
        return content;
    }

    @Override
    public void handle(IServerMessageHandler messageHandler) throws InterruptedException {
        messageHandler.handle(this);
    }
}
