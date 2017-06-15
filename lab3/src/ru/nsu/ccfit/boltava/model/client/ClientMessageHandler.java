package ru.nsu.ccfit.boltava.model.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.nsu.ccfit.boltava.model.message.message_content.TextMessage;
import ru.nsu.ccfit.boltava.model.message.notification.NewChatMessageNotification;
import ru.nsu.ccfit.boltava.model.message.notification.UserJoinedChat;
import ru.nsu.ccfit.boltava.model.message.notification.UserLeftChat;
import ru.nsu.ccfit.boltava.model.message.response.*;
import ru.nsu.ccfit.boltava.view.IOnChatMessageReceivedListener;
import ru.nsu.ccfit.boltava.view.IUserListObserver;

import java.util.HashSet;

public class ClientMessageHandler implements IClientMessageHandler, IChatMessageHandler {

    private Client client;
    private HashSet<IUserListObserver> userListObservers = new HashSet<>();

    private static final Logger logger = LogManager.getLogger("ConsoleLogger");

    ClientMessageHandler(Client client) {
        this.client = client;
    }

    public void addUserListObserver(IUserListObserver observer) {
        userListObservers.add(observer);
    }

    @Override
    public void handle(ErrorResponse msg) {
        logger.error(msg.getErrorMessage());
    }

    @Override
    public void handle(SuccessResponse msg) {
        logger.info("Success response");
    }

    @Override
    public void handle(LoginSuccess msg) {
        logger.info(String.format("%s response. session id: %s", msg.getClass().getSimpleName(), msg.getSessionId()));


        client.setSessionId(msg.getSessionId());
        client.setChatHistory(msg.getChatHistory());
        try {
            client.startChatApp();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handle(LoginError msg) {
        logger.error(msg.getErrorMessage());
    }

    @Override
    public void handle(GetUserListSuccess msg) {
        logger.info(String.format("%s response. session id: %s", msg.getClass().getSimpleName(), msg.getSessionId()));
    }

    @Override
    public void handle(NewChatMessageNotification msg) {
        logger.info("Got a new message:" + msg.getContent().getClass().getSimpleName());
        client.addMessageToHistory(msg.getContent());
        msg.getContent().handle(this);
    }

    @Override
    public void handle(UserJoinedChat msg) {
        logger.info("Got a new message:" + msg.getClass().getSimpleName());

        userListObservers.forEach(observer -> observer.onUserJoined(msg.getUser()));
    }

    @Override
    public void handle(UserLeftChat msg) {
        logger.info("Got a new message:" + msg.getClass().getSimpleName());

        userListObservers.forEach(observer -> observer.onUserLeft(msg.getUser()));
    }

    @Override
    public void handle(TextMessage msg) {
        logger.info("Text message: " + msg.getMessageText());
    }

}
