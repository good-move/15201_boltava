package ru.nsu.ccfit.boltava.model.message;

import ru.nsu.ccfit.boltava.model.message.event.NewTextMessageEvent;
import ru.nsu.ccfit.boltava.model.message.event.UserJoinedChatEvent;
import ru.nsu.ccfit.boltava.model.message.event.UserLeftChatEvent;
import ru.nsu.ccfit.boltava.model.message.request.GetUserListRequest;
import ru.nsu.ccfit.boltava.model.message.request.LoginRequest;
import ru.nsu.ccfit.boltava.model.message.request.LogoutRequest;
import ru.nsu.ccfit.boltava.model.message.request.PostTextMessageRequest;
import ru.nsu.ccfit.boltava.model.message.response.*;

public class MessageFactory {

    public static LoginRequest getLoginRequest() {
        return new LoginRequest( "", "");
    }

    public static LoginSuccess getLoginSuccess() {
        return new LoginSuccess("");
    }

    public static LoginError getLoginError() {
        return new LoginError("",0);
    }

    public static LogoutRequest getLogoutRequest() {
        return new LogoutRequest("");
    }

    public static GetUserListRequest getUserListRequest() {
        return new GetUserListRequest("");
    }

    public static GetUserListSuccess getUserListSuccess() {
        return new GetUserListSuccess(null);
    }

    public static NewTextMessageEvent getNewTextMessage() {
        return new NewTextMessageEvent(null, null);
    }

    public static UserLeftChatEvent getUserLeftChat() {
        return new UserLeftChatEvent(null);
    }

    public static UserJoinedChatEvent getUserJoinedChat() {
        return new UserJoinedChatEvent(null);
    }
    public static PostTextMessageRequest getSendTextMessageRequest() {
        return new PostTextMessageRequest(null, null);
    }

    public static LogoutError getLogoutError() {
        return new LogoutError(null, 0);
    }

    public static PostTextMessageError getPostTextMessageError() {
        return new PostTextMessageError(null, 0);
    }

    public static GetUserListError getUserListError() {
        return new GetUserListError(null, 0);
    }

    public static LogoutSuccess getLogoutSuccess() {
        return new LogoutSuccess();
    }

    public static PostTextMessageSuccess getPostTextMessageSuccess() {
        return new PostTextMessageSuccess();
    }

}
