package ru.nsu.ccfit.boltava.model.message;

public interface IMessageHandler {

    void handle(Message msg);
    void handle(ErrorResponse msg);
    void handle(SuccessResponse msg);
    void handle(LoginSuccess msg);
    void handle(GetUserListSuccess msg);
    void handle(NewTextMessage msg);
    void handle(UpdateUserList msg);

}
