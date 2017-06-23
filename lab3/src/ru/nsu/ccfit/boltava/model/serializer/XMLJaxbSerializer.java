package ru.nsu.ccfit.boltava.model.serializer;

import ru.nsu.ccfit.boltava.model.client.Client;
import ru.nsu.ccfit.boltava.model.message.Message;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringWriter;

public class XMLJaxbSerializer implements IMessageSerializer<String> {

    private final Client client;
    private final Marshaller marshaller;
    private final Unmarshaller unmarshaller;

    public XMLJaxbSerializer(JAXBContext context, Client client) throws JAXBException {
        marshaller = context.createMarshaller();
        unmarshaller = context.createUnmarshaller();
        this.client = client;
    }

    @Override
    public String serialize(Message message) throws JAXBException {
        StringWriter writer = new StringWriter();
        marshaller.marshal(message, writer);
        return writer.toString();
    }

    @Override
    public Message deserialize(String xmlString) {
        // 1. look for <event name="{Name}"> and replace it with <event{Name}>
        // 2. if not successful, take sent message from client's queue.
        // 3. look for <success>...</success> and replace it with last message class name
        // 4. same with <error>...</error>
        // 5. => finally get appropriate xml and feed it to unmarshaller

        

        return null;
    }
}
