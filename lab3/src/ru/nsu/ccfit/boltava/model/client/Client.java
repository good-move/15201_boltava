package ru.nsu.ccfit.boltava.model.client;

import ru.nsu.ccfit.boltava.model.chat.User;
import ru.nsu.ccfit.boltava.model.message.Request;
import ru.nsu.ccfit.boltava.model.message.message_content.ChatMessage;
import ru.nsu.ccfit.boltava.model.message.message_content.TextMessage;
import ru.nsu.ccfit.boltava.model.message.request.GetUserList;
import ru.nsu.ccfit.boltava.model.message.request.Login;
import ru.nsu.ccfit.boltava.model.message.request.SendChatMessage;
import ru.nsu.ccfit.boltava.view.*;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Properties;

public class Client implements IMessageInputPanelEventListener, IOnLoginSubmitListener {

    private App app;
    private LoginView loginView;

    private User profile;
    private String sessionId;
    private String queriedUsername;
    private ArrayList<User> onlineUsers = new ArrayList<>();
    private ArrayList<ChatMessage> chatHistory = new ArrayList<>();

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

            loginView = new LoginView();
            loginView.addOnLoginSubmitListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void startChatApp() throws InterruptedException {
        profile = new User(queriedUsername);
        loginView.dispose();
        app = new App(client);
        Request request = new GetUserList(profile.getUsername());
        request.setSessionId(sessionId);
        deliveryService.sendMessage(request);
    }

    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }

    public String getSessionId() {
        return sessionId;
    }

    public User getProfile() {
        return profile;
    }

    public void setProfile(User profile) {
        this.profile = profile;
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
