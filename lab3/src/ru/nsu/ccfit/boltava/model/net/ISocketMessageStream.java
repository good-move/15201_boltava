package ru.nsu.ccfit.boltava.model.net;

import org.xml.sax.SAXException;
import ru.nsu.ccfit.boltava.model.message.Message;
import ru.nsu.ccfit.boltava.model.serializer.IMessageSerializer;

import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public interface ISocketMessageStream<InMessage extends Message, OutMessage extends Message> {

    OutMessage read() throws IOException, ClassNotFoundException, JAXBException, ParserConfigurationException, SAXException, IServerSocketMessageStream.StreamReadException, IMessageSerializer.MessageSerializationException;
    void write(InMessage msg) throws IOException, JAXBException, IMessageSerializer.MessageSerializationException, StreamReadException, StreamWriteException;

    enum MessageStreamType {
        XML,
        OBJ
    }

    class StreamReadException extends Exception {

        public StreamReadException() {}

        public StreamReadException(String msg) { super(msg); }

        public StreamReadException(Throwable cause) { super(cause); }

        public StreamReadException(String msg, Throwable cause) { super(msg, cause); }

        public StreamReadException(String msg, Throwable cause, boolean suppressionAllowed, boolean stackTraceEnabled) {
            super(msg, cause, suppressionAllowed, stackTraceEnabled);
        }

    }

    class StreamWriteException extends Exception {

        public StreamWriteException() {}

        public StreamWriteException(String msg) { super(msg); }

        public StreamWriteException(Throwable t) { super(t); }

        public StreamWriteException(String msg, Throwable cause) { super(msg, cause); }

        public StreamWriteException(String msg, Throwable cause, boolean suppressionAllowed, boolean stackTraceEnabled) {
            super(msg, cause, suppressionAllowed, stackTraceEnabled);
        }

    }

}
