package ru.nsu.ccfit.boltava.model.serializer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import ru.nsu.ccfit.boltava.model.message.Message;
import ru.nsu.ccfit.boltava.model.message.Request;
import ru.nsu.ccfit.boltava.model.message.Response;
import ru.nsu.ccfit.boltava.model.message.request.GetUserListRequest;
import ru.nsu.ccfit.boltava.model.message.request.LoginRequest;
import ru.nsu.ccfit.boltava.model.message.request.LogoutRequest;
import ru.nsu.ccfit.boltava.model.message.request.PostTextMessageRequest;
import ru.nsu.ccfit.boltava.model.message.response.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.concurrent.BlockingQueue;


public class XMLSerializer implements IMessageSerializer<String> {

    private static final Logger logger = LogManager.getLogger("SerializationLogger");

    private BlockingQueue<Request> lastSentRequestQueue;
    private final Marshaller marshaller;
    private final Unmarshaller unmarshaller;

    private final static String NAMED_TAG_PATTERN = "(?s)(<?xml(.*)?>)?<(\\w+)( name=\"(\\w+)\")>(.*)</(\\w+)>";
    private final static String UNNAMED_ACTION_TAG_PATTERN = "(?s)<(\\w+)_(\\w+)(.*)>(.*)</(\\w+)>$";

    public XMLSerializer(JAXBContext context) throws JAXBException {
        marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        unmarshaller = context.createUnmarshaller();
    }

    public void setRequestQueue(BlockingQueue<Request> lastSentRequestQueue) {
        this.lastSentRequestQueue = lastSentRequestQueue;
    }

    @Override
    public String serialize(Message message) throws MessageSerializationException {
        try {
            StringWriter writer = new StringWriter();
            marshaller.marshal(message, writer);
            String xml = writer.toString();

            logger.info("Serializing");
            logger.info(xml);

            return adaptJAXBToProtocol(xml);
        } catch (JAXBException e) {
            throw new MessageSerializationException(
                    String.format("Failed to marshal message type %s", message.getClass().getSimpleName())
            );
        }
    }

    @Override
    public Message deserialize(String xmlString) throws MessageSerializationException {
        logger.info("Deserializing");
        logger.info(xmlString);

        try {
            xmlString = xmlString.trim();
            Element response = DocumentBuilderFactory
                                .newInstance()
                                .newDocumentBuilder()
                                .parse(new InputSource(new StringReader(xmlString)))
                                .getDocumentElement();

            switch (response.getTagName()) {
                case TagNames.COMMAND:
                case TagNames.EVENT: return deserializeAction(xmlString);
                case TagNames.SUCCESS:
                case TagNames.ERROR: return deserializeResponse(xmlString, response.getTagName());
                default: throw new MessageSerializationException("Unknown tag name: " + response.getTagName());
            }
        } catch (SAXException | IOException | JAXBException | ParserConfigurationException e) {
            throw new MessageSerializationException(e.getMessage(), e);
        }
    }

    // for parsing Event and Response subclasses
    private String adaptProtocolToJAXB(String xmlString) {
        return xmlString.replaceAll(NAMED_TAG_PATTERN, "<$5_$3>$6</$5_$3>");
    }

    // for parsing Event and Response subclasses
    private String adaptJAXBToProtocol(String xmlString) {
        return xmlString.replaceAll(UNNAMED_ACTION_TAG_PATTERN, "<$2 name=\"$1\"$3>$4</$2>");
    }

    private Message deserializeAction(String xmlString) throws JAXBException {
        String adaptedXML = adaptProtocolToJAXB(xmlString);
        StringReader reader = new StringReader(adaptedXML);
        return (Message) unmarshaller.unmarshal(reader);
    }

    private Response deserializeResponse(String xmlString, String tagName) throws MessageSerializationException, JAXBException {
        Class<? extends Response> responseClass = mapRequestToResponseClass(tagName);
        StringReader reader = new StringReader(xmlString);

        Response r = (Response) JAXBContext.newInstance(responseClass).createUnmarshaller().unmarshal(reader);

        logger.info("Deserializing response for class: " + responseClass.getSimpleName() + " Result: " + r.getClass().getSimpleName());

        return r;
    }

    private Class<? extends Response> mapRequestToResponseClass(String responseTagName) throws MessageSerializationException {
        if (lastSentRequestQueue == null) {
            throw new MessageSerializationException("Requests queue is not initialized");
        }

        Request request = lastSentRequestQueue.poll();

        if (request == null) {
            throw new MessageSerializationException("No requests are available to make response serialization");
        }

        logger.info("Mapping request to response. Request: " + request.getClass().getSimpleName());

        if (request.getClass().equals(GetUserListRequest.class)) {
            return responseTagName.equals(TagNames.SUCCESS) ? GetUserListSuccess.class : GetUserListError.class;
        } else if (request.getClass().equals(LoginRequest.class)) {
            return responseTagName.equals(TagNames.SUCCESS) ? LoginSuccess.class : LoginError.class;
        } else if (request.getClass().equals(LogoutRequest.class)) {
            return responseTagName.equals(TagNames.SUCCESS) ? LogoutSuccess.class : LogoutError.class;
        } else if (request.getClass().equals(PostTextMessageRequest.class)) {
            return responseTagName.equals(TagNames.SUCCESS) ? PostTextMessageSuccess.class : PostTextMessageError.class;
        } else {
            throw new MessageSerializationException(
                    String.format("Failed to map tag name %s to any known Message subclass", responseTagName)
            );
        }
    }

    private static class TagNames {
        static final String COMMAND = "command";
        static final String EVENT = "event";
        static final String SUCCESS = "success";
        static final String ERROR = "error";
    }

}
