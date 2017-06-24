package ru.nsu.ccfit.boltava.model.server;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.nsu.ccfit.boltava.model.chat.User;
import ru.nsu.ccfit.boltava.model.message.Notification;
import ru.nsu.ccfit.boltava.model.message.Request;
import ru.nsu.ccfit.boltava.model.message.Response;
import ru.nsu.ccfit.boltava.model.message.ServerMessage;
import ru.nsu.ccfit.boltava.model.message.notification.NewChatMessageNotification;
import ru.nsu.ccfit.boltava.model.message.notification.UserJoinedChat;
import ru.nsu.ccfit.boltava.model.message.request.GetUserList;
import ru.nsu.ccfit.boltava.model.message.request.Login;
import ru.nsu.ccfit.boltava.model.message.request.Logout;
import ru.nsu.ccfit.boltava.model.message.request.SendChatMessage;
import ru.nsu.ccfit.boltava.model.message.response.GetUserListSuccess;
import ru.nsu.ccfit.boltava.model.message.response.LoginError;
import ru.nsu.ccfit.boltava.model.message.response.LoginSuccess;

import static ru.nsu.ccfit.boltava.model.server.ErrorBundle.ErrorName.*;

public class ServerMessageHandler implements IServerMessageHandler {

    private static final Logger logger = LogManager.getLogger("ConsoleLogger");

    private ChatMember member;
    private Server server;

    ServerMessageHandler(Server server, ChatMember member) {
        this.server = server;
        this.member = member;
    }

    @Override
    public void handle(Login msg) throws InterruptedException {
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
            member.setUser(new User(msg.getUsername()));
            server.registerUsername(msg.getUsername());
            member.setSessionId(sessionId);
            response = new LoginSuccess(sessionId);

            member.sendMessage(response);

            Notification notification = new UserJoinedChat(
                    member.getUser().getUsername()
            );
            server.broadcastMessageFrom(notification, member);
            return;
        }

        member.sendMessage(response);
    }

    @Override
    public void handle(Logout msg) throws InterruptedException {
        logger.info(String.format("%s request. Username: %s", msg.getClass().getSimpleName(), member.getUser().getUsername()));
        member.close();
    }

    @Override
    public void handle(SendChatMessage msg) throws InterruptedException {
        logger.info(String.format("%s request. Username: %s", msg.getClass().getSimpleName(), member.getUser().getUsername()));

        ServerMessage serverMessage = checkMessageBroken(msg);

        if (serverMessage == null) {
            serverMessage = new NewChatMessageNotification(member.getSessionId(), msg.getContent(), member.getUser().getUsername());
            server.broadcastMessageFrom(serverMessage, member);
        } else {
            member.sendMessage(serverMessage);
        }

    }

    @Override
    public void handle(GetUserList msg) throws InterruptedException {
        logger.info(String.format("%s request. Username: %s", msg.getClass().getSimpleName(), member.getUser().getUsername()));

        Response response = checkMessageBroken(msg);

        if (response == null) {
            response = new GetUserListSuccess(member.getSessionId(), server.getOnlineUsersList());
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
//        }
//        else if (msg.getSender() == null) {
//            response = new LoginError(
//                    ErrorBundle.ErrorMessages.get(NullUsername),
//                    ErrorBundle.ErrorCodes.get(NullUsername)
//            );
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
