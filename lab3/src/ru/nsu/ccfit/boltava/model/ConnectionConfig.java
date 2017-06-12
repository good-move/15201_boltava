package ru.nsu.ccfit.boltava.model;

import ru.nsu.ccfit.boltava.model.message.IMessageHandler;
import ru.nsu.ccfit.boltava.model.message.ISocketMessageStream;

public class ConnectionConfig<T> {

    private String mHost;
    private int mPort;
    private ISocketMessageStream.MessageStreamType mStreamType;

    public ISocketMessageStream.MessageStreamType getStreamType() {
        return mStreamType;
    }

    public void setStreamType(ISocketMessageStream.MessageStreamType mStreamType) {
        this.mStreamType = mStreamType;
    }

    private IMessageHandler mMessageHandler;

    public String getHost() {
        return mHost;
    }

    public void setHost(String mHost) {
        this.mHost = mHost;
    }

    public int getPort() {
        return mPort;
    }

    public void setPort(int mPort) {
        this.mPort = mPort;
    }

    public IMessageHandler getMessageHandler() {
        return mMessageHandler;
    }

    public void setMessageHandler(IMessageHandler mMessageHandler) {
        this.mMessageHandler = mMessageHandler;
    }

}
