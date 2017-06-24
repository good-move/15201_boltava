package ru.nsu.ccfit.boltava.model.message;

import ru.nsu.ccfit.boltava.model.message.notification.NewChatMessageNotification;
import ru.nsu.ccfit.boltava.model.message.notification.UserJoinedChat;
import ru.nsu.ccfit.boltava.model.message.notification.UserLeftChat;
import ru.nsu.ccfit.boltava.model.message.request.GetUserList;
import ru.nsu.ccfit.boltava.model.message.request.Login;
import ru.nsu.ccfit.boltava.model.message.request.Logout;
import ru.nsu.ccfit.boltava.model.message.response.*;

public class MessageFactory {

    public static Login getLoginRequest() {
        return new Login( "", "");
    }

    public static LoginSuccess getLoginSuccess() {
        return new LoginSuccess("");
    }

    public static LoginError getLoginError() {
        return new LoginError("",0);
    }

    public static Logout getLogoutRequest() {
        return new Logout("");
    }

    public static GetUserList getUserListRequest() {
        return new GetUserList("");
    }

    public static GetUserListSuccess getUserListSuccess() {
        return new GetUserListSuccess(null);
    }

    public static ErrorResponse getErrorResponse() {
        return new ErrorResponse(null, 0);
    }

    public static SuccessResponse getSuccessResponse() {
        return new SuccessResponse();
    }

    public static NewChatMessageNotification getNewChatMessage() {
        return new NewChatMessageNotification(null, null);
    }

    public static UserLeftChat getUserLeftChat() {
        return new UserLeftChat(null);
    }

    public static UserJoinedChat getUserJoinedChat() {
        return new UserJoinedChat(null);
    }

}
