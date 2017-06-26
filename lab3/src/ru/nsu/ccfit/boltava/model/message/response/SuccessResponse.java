package ru.nsu.ccfit.boltava.model.message.response;

import ru.nsu.ccfit.boltava.model.client.IClientMessageHandler;
import ru.nsu.ccfit.boltava.model.message.MessageFactory;
import ru.nsu.ccfit.boltava.model.message.Response;

import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

public abstract class SuccessResponse extends Response {

    public abstract void handle(IClientMessageHandler messageHandler);
}
