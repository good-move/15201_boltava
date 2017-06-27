package ru.nsu.ccfit.boltava.model.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.nsu.ccfit.boltava.model.message.TextMessage;
import ru.nsu.ccfit.boltava.model.message.event.NewTextMessageEvent;
import ru.nsu.ccfit.boltava.model.message.event.UserJoinedChatEvent;
import ru.nsu.ccfit.boltava.model.message.event.UserLeftChatEvent;
import ru.nsu.ccfit.boltava.model.message.response.*;
import ru.nsu.ccfit.boltava.view.IChatMessageRenderer;
import ru.nsu.ccfit.boltava.view.IUserListObserver;

import java.util.HashSet;

public class ClientMessageHandler implements IClientMessageHandler {

    private Client client;
    private HashSet<IUserListObserver> userListObservers = new HashSet<>();
    private HashSet<IChatMessageRenderer> chatMessageRenderers = new HashSet<>();

    private static final Logger logger = LogManager.getLogger(ClientMessageHandler.class);

    ClientMessageHandler(Client client) {
        this.client = client;
    }

    void addMessageRenderer(IChatMessageRenderer renderer) {
        chatMessageRenderers.add(renderer);
    }

    void addUserListObserver(IUserListObserver observer) {
        userListObservers.add(observer);
    }

    void renderTextMessage(TextMessage message) {
        chatMessageRenderers.forEach(message::render);
    }

    @Override
    public void handle(LoginSuccess msg) {
        logger.info(String.format("%s response. session id: %s", msg.getClass().getSimpleName(), msg.getSessionId()));
        client.onLoginSuccess(msg.getSessionId());
    }

    @Override
    public void handle(LoginError error) {
        logger.error(error.getErrorMessage());
        client.onLoginError(error);
    }

    @Override
    public void handle(GetUserListSuccess msg) {
        logger.info(String.format("%s response.", msg.getClass().getSimpleName()));
        client.setOnlineUsers(msg.getOnlineUsers());
        userListObservers.forEach(observer -> observer.onUserListSet(msg.getOnlineUsers()));
    }

    @Override
    public void handle(GetUserListError msg) {

    }

    @Override
    public void handle(PostTextMessageSuccess msg) {

    }

    @Override
    public void handle(PostTextMessageError msg) {

    }

    @Override
    public void handle(LogoutSuccess msg) {

    }

    @Override
    public void handle(LogoutError msg) {

    }

    @Override
    public void handle(NewTextMessageEvent msg) {
        logger.info("Got a new message:" + msg.getMessage().getClass().getSimpleName());
        TextMessage message = new TextMessage(msg.getSender(), msg.getMessage());
        client.addMessageToHistory(message);
        renderTextMessage(message);
    }

    @Override
    public void handle(UserJoinedChatEvent msg) {
        logger.info("Got a new message:" + msg.getClass().getSimpleName());
        userListObservers.forEach(observer -> observer.onUserJoined(msg.getUsername()));
        chatMessageRenderers.forEach(msg::render);
    }

    @Override
    public void handle(UserLeftChatEvent msg) {
        logger.info("Got a new message:" + msg.getClass().getSimpleName());
        userListObservers.forEach(observer -> observer.onUserLeft(msg.getUsername()));
        chatMessageRenderers.forEach(msg::render);

    }

}
