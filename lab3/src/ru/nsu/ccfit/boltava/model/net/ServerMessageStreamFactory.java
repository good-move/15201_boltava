package ru.nsu.ccfit.boltava.model.net;

import ru.nsu.ccfit.boltava.model.message.event.NewTextMessageEvent;
import ru.nsu.ccfit.boltava.model.message.event.UserJoinedChatEvent;
import ru.nsu.ccfit.boltava.model.message.event.UserLeftChatEvent;
import ru.nsu.ccfit.boltava.model.message.request.GetUserListRequest;
import ru.nsu.ccfit.boltava.model.message.request.LoginRequest;
import ru.nsu.ccfit.boltava.model.message.request.LogoutRequest;
import ru.nsu.ccfit.boltava.model.message.request.PostTextMessageRequest;
import ru.nsu.ccfit.boltava.model.message.response.*;
import ru.nsu.ccfit.boltava.model.serializer.XMLSerializer;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.net.Socket;

public class ServerMessageStreamFactory {

    private static JAXBContext jaxbContext;
    static  {
        Class[] classes = new Class[] {
                NewTextMessageEvent.class,
                UserJoinedChatEvent.class,
                UserLeftChatEvent.class,
                LoginRequest.class,
                LogoutRequest.class,
                PostTextMessageRequest.class,
                GetUserListRequest.class,
                SuccessResponse.class,
                ErrorResponse.class,
                GetUserListSuccess.class,
                LoginError.class,
                LoginSuccess.class
        };

        try {
            jaxbContext = JAXBContext.newInstance(classes);
        } catch (JAXBException e) {
            e.printStackTrace();
        }
    }

    public static IServerSocketMessageStream get(ISocketMessageStream.MessageStreamType type, Socket socket) throws IOException, JAXBException {
        switch (type) {
            case OBJ: return new ServerObjectStream(socket);
            case XML:return new ServerXMLStream(socket, new XMLSerializer(jaxbContext));
            default: throw new RuntimeException("Unknown stream type: " + type.toString());
        }
    }

}
