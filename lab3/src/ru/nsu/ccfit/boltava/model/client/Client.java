package ru.nsu.ccfit.boltava.model.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.nsu.ccfit.boltava.model.chat.User;
import ru.nsu.ccfit.boltava.model.message.Request;
import ru.nsu.ccfit.boltava.model.message.ServerMessage;
import ru.nsu.ccfit.boltava.model.message.message_content.ChatMessage;
import ru.nsu.ccfit.boltava.model.message.message_content.TextMessage;
import ru.nsu.ccfit.boltava.model.message.request.GetUserList;
import ru.nsu.ccfit.boltava.model.message.request.Login;
import ru.nsu.ccfit.boltava.model.message.request.Logout;
import ru.nsu.ccfit.boltava.model.message.request.SendChatMessage;
import ru.nsu.ccfit.boltava.model.net.ClientMessageStreamFactory;
import ru.nsu.ccfit.boltava.model.net.IClientSocketMessageStream;
import ru.nsu.ccfit.boltava.view.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;

public class Client implements IMessageInputPanelEventListener, IOnLoginSubmitListener {

    private static Logger logger = LogManager.getLogger("ConsoleLogger");

    private App app;
    private LoginView loginView;

    private User profile;
    private String sessionId;
    private String queriedUsername;
    private ArrayList<User> onlineUsers = new ArrayList<>();
    private ArrayList<ChatMessage> chatHistory = new ArrayList<>();

    private boolean isLoggedIn = false;

    private Client client;
    private DeliveryService deliveryService;

    private HashSet<IChatMessageRenderer> chatMessageRenderers = new HashSet<>();


    private Client () {
        client = this;
        setUp();
    }

    public static void main(String[] args) {
        Client client = new Client();
    }

    private void setUp() {
        try (FileInputStream is = new FileInputStream("client.properties")) {
            Properties props = new Properties();
            props.load(is);

            ConnectionConfig config = new ConnectionConfig();
            config.setHost(props.getProperty("host"));
            config.setPort(Integer.parseInt(props.getProperty("port")));
            config.setStreamType(props.getProperty("mode"));

            deliveryService = new DeliveryService(config, new ClientMessageHandler(client));
            deliveryService.start();

            loginView = new LoginView(client);
            loginView.addOnLoginSubmitListener(client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void loginViewClosed() {
        if (loginView.isEnabled()) {
            loginView.setEnabled(false);
            exit();
        }
    }

    public void appViewClosed() {
        exit();
    }

    private void exit() {
        try {
            deliveryService.stop();
            loginView.dispose();
            app.dispose();
        } catch (NullPointerException e) {

        }
    }

    void onLogin(String sessionId) {
        if (isLoggedIn) {
            throw new RuntimeException("Double login on client side");
        }
        try {
            this.isLoggedIn = true;
            this.sessionId = sessionId;
            this.profile = new User(queriedUsername);

            Request request = new GetUserList(profile.getUsername());
            request.setSessionId(sessionId);
            deliveryService.sendMessage(request);

            loginView.setVisible(false);
            app = new App(client);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getSessionId() {
        return sessionId;
    }

    public User getProfile() {
        return profile;
    }

    public ArrayList<User> getOnlineUsers() {
        return onlineUsers;
    }

    public void setOnlineUsers(ArrayList<User> onlineUsers) {
        this.onlineUsers = onlineUsers;
        // update listeners
    }

    public ArrayList<ChatMessage> getChatHistory() {
        return chatHistory;
    }

    void setChatHistory(ArrayList<ChatMessage> chatHistory) {
        this.chatHistory = chatHistory;
    }

    public void addChatMessageRenderer(IChatMessageRenderer renderer) {
        chatMessageRenderers.add(renderer);
    }

    void addMessageToHistory(ChatMessage msg) {
        chatHistory.add(msg);
        chatMessageRenderers.forEach(msg::render);
    }

    @Override
    public void onTextMessageSubmit(String textMessage) {
        try {
            TextMessage message = new TextMessage(profile.getUsername(), textMessage);
            Request request = new SendChatMessage(profile.getUsername(), message);
            request.setSessionId(sessionId);
            addMessageToHistory(message);
            deliveryService.sendMessage(request);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoginSubmit(String username) {
        try {
            deliveryService.sendMessage(new Login(username));
            this.queriedUsername = username;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
