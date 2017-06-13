package ru.nsu.ccfit.boltava.model.server;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.nsu.ccfit.boltava.model.chat.User;
import ru.nsu.ccfit.boltava.model.message.*;
import ru.nsu.ccfit.boltava.model.message.types.*;

import static ru.nsu.ccfit.boltava.model.server.Errors.ErrorName.*;

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

        if (server.isUsernameTaken(msg.getUsername())) {
            response = new LoginError(
                    Errors.ErrorMessages.get(UsernameIsTaken),
                    Errors.ErrorCodes.get(UsernameIsTaken)
            );
        } else if(!server.isUsernameFormatValid(msg.getUsername())) {
            response = new LoginError(
                    Errors.ErrorMessages.get(InvalidUsernameFormat),
                    Errors.ErrorCodes.get(InvalidUsernameFormat)
                    // TODO
                    // add expected msg format message
            );
        } else {
            String sessionId = String.valueOf(member.getID());
            member.setUser(new User(msg.getUsername()));
            server.registerUsername(msg.getUsername());
            member.setSessionId(sessionId);
            response = new LoginSuccess(sessionId);
        }

        member.sendMessage(response);
        // send message history to new user

        Notification notification = new UpdateUserList(member.getUser(), UpdateUserList.Action.Add);
        server.enqueueMessage(notification);

    }

    @Override
    public void handle(Logout msg) throws InterruptedException {
        logger.info(String.format("%s request. Username: %s", msg.getClass().getSimpleName(), msg.getUsername()));

    }

    @Override
    public void handle(SendTextMessage msg) throws InterruptedException {
        logger.info(String.format("%s request. Username: %s", msg.getClass().getSimpleName(), msg.getUsername()));

        ServerMessage serverMessage = checkMessageBroken(msg);

        if (serverMessage == null) {
            serverMessage = new NewTextMessage(msg.getMessageText(), msg.getUsername());
        }

        member.sendMessage(serverMessage);
    }

    @Override
    public void handle(GetUserList msg) throws InterruptedException {
        logger.info(String.format("%s request. Username: %s", msg.getClass().getSimpleName(), msg.getUsername()));

        Response response = checkMessageBroken(msg);

        if (response == null) {
            response = new GetUserListSuccess(server.getOnlineUsersList());
        }

        member.sendMessage(response);
    }

    private Response checkMessageBroken(Request msg) {
        Response response = null;
        if (!msg.getUsername().equals(member.getUser().getUsername())){
            response = new LoginError(
                    Errors.ErrorMessages.get(UsernameMismatch),
                    Errors.ErrorCodes.get(UsernameMismatch)
            );
        } else if (msg.getSessionId().equals(member.getSessionId())) {
            response = new LoginError(
                    Errors.ErrorMessages.get(SessionIdMismatch),
                    Errors.ErrorCodes.get(SessionIdMismatch)
            );
        }

        return response;
    }

}
