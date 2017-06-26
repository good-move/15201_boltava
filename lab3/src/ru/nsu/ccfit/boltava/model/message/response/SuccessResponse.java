package ru.nsu.ccfit.boltava.model.message.response;

import ru.nsu.ccfit.boltava.model.client.IClientMessageHandler;
import ru.nsu.ccfit.boltava.model.message.Response;

public abstract class SuccessResponse extends Response {

    public abstract void handle(IClientMessageHandler messageHandler);
}
