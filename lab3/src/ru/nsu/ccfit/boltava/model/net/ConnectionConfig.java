package ru.nsu.ccfit.boltava.model.net;

import ru.nsu.ccfit.boltava.model.net.ISocketMessageStream;

public class ConnectionConfig {

    private String mHost;
    private int mPort;
    private ISocketMessageStream.MessageStreamType mStreamType;

    public ISocketMessageStream.MessageStreamType getStreamType() {
        return mStreamType;
    }

    public void setStreamType(ISocketMessageStream.MessageStreamType mStreamType) {
        this.mStreamType = mStreamType;
    }

    public void setStreamType(String streamType) {
        if (streamType == null) {
            String msg = "Stream Type can't be null";
            throw new IllegalArgumentException(msg);
        }

        switch (streamType) {
            case "XML": mStreamType = ISocketMessageStream.MessageStreamType.XML;
                break;
            case "OBJ": mStreamType = ISocketMessageStream.MessageStreamType.OBJ;
                break;
            default: throw new RuntimeException("Unknown stream type: " + streamType);
        }

    }

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

}
