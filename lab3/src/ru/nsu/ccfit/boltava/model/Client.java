package ru.nsu.ccfit.boltava.model;

import ru.nsu.ccfit.boltava.model.message.ClientMessageHandler;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Client {

    public static void main(String[] args) {
        try (FileInputStream is = new FileInputStream("client.properties")) {
            Properties props = new Properties();
            props.load(is);

            ConnectionConfig config = new ConnectionConfig();
            config.setHost(props.getProperty("host"));
            config.setPort(Integer.parseInt(props.getProperty("port")));
            config.setStreamType(props.getProperty("mode"));
            config.setMessageHandler(new ClientMessageHandler());

            Connection connection = new Connection(config);
            connection.listen();
        } catch (IOException e) {
            e.printStackTrace();
//            System.err.println(e.getMessage());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (Exception e) {
//            System.err.println(e.getMessage());
            e.printStackTrace();
        }

    }

}
