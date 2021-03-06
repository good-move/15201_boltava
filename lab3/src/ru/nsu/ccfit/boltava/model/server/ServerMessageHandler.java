package ru.nsu.ccfit.boltava.model.server;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.nsu.ccfit.boltava.model.chat.User;
import ru.nsu.ccfit.boltava.model.message.*;
import ru.nsu.ccfit.boltava.model.message.event.NewTextMessageEvent;
import ru.nsu.ccfit.boltava.model.message.event.UserJoinedChatEvent;
import ru.nsu.ccfit.boltava.model.message.request.GetUserListRequest;
import ru.nsu.ccfit.boltava.model.message.request.LoginRequest;
import ru.nsu.ccfit.boltava.model.message.request.LogoutRequest;
import ru.nsu.ccfit.boltava.model.message.request.PostTextMessageRequest;
import ru.nsu.ccfit.boltava.model.message.response.GetUserListSuccess;
import ru.nsu.ccfit.boltava.model.message.response.LoginError;
import ru.nsu.ccfit.boltava.model.message.response.LoginSuccess;

import static ru.nsu.ccfit.boltava.model.server.ErrorBundle.ErrorName.*;

public class ServerMessageHandler implements IServerMessageHandler {

    private static final Logger logger = LogManager.getLogger(ServerMessage.class);

    private ChatMember member;
    private Server server;

    ServerMessageHandler(Server server, ChatMember member) {
        this.server = server;
        this.member = member;
    }

    @Override
    public void handle(LoginRequest msg) throws InterruptedException {
        logger.info(String.format("%s request. Username: %s", msg.getClass().getSimpleName(), msg.getUsername()));

        Response response;

        if (member.getSessionId() != null) {
            response = new LoginError(
                    ErrorBundle.ErrorMessages.get(DoubleLogin),
                    ErrorBundle.ErrorCodes.get(DoubleLogin)
            );
        }
        else if (server.isUsernameTaken(msg.getUsername())) {
            response = new LoginError(
                    ErrorBundle.ErrorMessages.get(UsernameIsTaken),
                    ErrorBundle.ErrorCodes.get(UsernameIsTaken)
            );
        } else if(!server.isUsernameFormatValid(msg.getUsername())) {
            response = new LoginError(
                    ErrorBundle.ErrorMessages.get(InvalidUsernameFormat),
                    ErrorBundle.ErrorCodes.get(InvalidUsernameFormat)
            );
            // TODO
            // add "valid username format" message
        } else {
            String sessionId = String.valueOf(member.getID());
            member.setUser(new User(msg.getUsername(), msg.getClientType()));
            server.registerUsername(msg.getUsername());
            member.setSessionId(sessionId);
            response = new LoginSuccess(sessionId);

            member.sendMessage(response);

            server.getChatHistorySneakPeek().forEach(message -> {
                try {
                    member.sendMessage(new NewTextMessageEvent(message.getMessage(), message.getAuthor()));
                } catch (InterruptedException e) {
                    logger.trace(e.getMessage());
                }
            });

            Event event = new UserJoinedChatEvent(
                    member.getUser().getUsername()
            );
            server.broadcastMessageFrom(event, member);
            return;
        }

        member.sendMessage(response);
    }

    @Override
    public void handle(LogoutRequest msg) throws InterruptedException {
        logger.info(String.format("%s request. Username: %s", msg.getClass().getSimpleName(), member.getUser().getUsername()));
        member.close();
    }

    @Override
    public void handle(PostTextMessageRequest msg) throws InterruptedException {
        logger.info(String.format("%s request. Username: %s", msg.getClass().getSimpleName(), member.getUser().getUsername()));

        ServerMessage serverMessage = checkMessageBroken(msg);

        if (serverMessage == null) {
            server.rollMessageHistory(new TextMessage(member.getUser().getUsername(), msg.getMessage()));
            serverMessage = new NewTextMessageEvent(msg.getMessage(), member.getUser().getUsername());
            server.broadcastMessageFrom(serverMessage, member);
        } else {
            member.sendMessage(serverMessage);
        }

    }

    @Override
    public void handle(GetUserListRequest msg) throws InterruptedException {
        logger.info(String.format("%s request. Username: %s", msg.getClass().getSimpleName(), member.getUser().getUsername()));

        Response response = checkMessageBroken(msg);

        if (response == null) {
            response = new GetUserListSuccess(server.getOnlineUsersList());
        }

        member.sendMessage(response);
    }

    private Response checkMessageBroken(Request msg) {
        Response response = null;

        if (member.getSessionId() == null) {
            response = new LoginError(
                    ErrorBundle.ErrorMessages.get(LoginRequired),
                    ErrorBundle.ErrorCodes.get(LoginRequired)
            );
        } else if (msg.getSessionId() == null) {
            response = new LoginError(
                    ErrorBundle.ErrorMessages.get(NullSessionId),
                    ErrorBundle.ErrorCodes.get(NullSessionId)
            );
        } else if (!member.getSessionId().equals(msg.getSessionId())) {
            response = new LoginError(
                    ErrorBundle.ErrorMessages.get(SessionIdMismatch),
                    ErrorBundle.ErrorCodes.get(SessionIdMismatch)
            );
        }

        return response;
    }

}
