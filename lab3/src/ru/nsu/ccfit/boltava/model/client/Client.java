package ru.nsu.ccfit.boltava.model.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.nsu.ccfit.boltava.model.chat.User;
import ru.nsu.ccfit.boltava.model.message.Request;
import ru.nsu.ccfit.boltava.model.message.TextMessage;
import ru.nsu.ccfit.boltava.model.message.request.GetUserList;
import ru.nsu.ccfit.boltava.model.message.request.Login;
import ru.nsu.ccfit.boltava.model.message.request.SendTextMessage;
import ru.nsu.ccfit.boltava.view.*;

import javax.xml.soap.Text;
import java.io.FileInputStream;
import java.io.IOException;
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
    private ArrayList<TextMessage> chatHistory = new ArrayList<>();

    private boolean isLoggedIn = false;

    private Client client;
    private DeliveryService deliveryService;

    private HashSet<IChatMessageRenderer> chatMessageRenderers = new HashSet<>();
    private LinkedBlockingQueue<Request> sentRequests = new LinkedBlockingQueue<>();


    public Client () {
        client = this;
        setUp();
    }

    public static void main(String[] args) {
        Client client = new Client();
    }

//    ****************************** Lifecycle methods ******************************

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

    //    ****************************** Client state control methods ******************************

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

    public ArrayList<TextMessage> getChatHistory() {
        return chatHistory;
    }

    public void setChatHistory(ArrayList<TextMessage> chatHistory) {
        this.chatHistory = chatHistory;
    }

    public Request getLastSentRequest() {
        return sentRequests.poll();
    }

    void addMessageToHistory(TextMessage msg) {
        chatHistory.add(msg);
        chatMessageRenderers.forEach(msg::render);
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

    //    ****************************** Interface methods ******************************

    public void addChatMessageRenderer(IChatMessageRenderer renderer) {
        chatMessageRenderers.add(renderer);
    }

    @Override
    public void onTextMessageSubmit(String textMessage) {
        try {
            Request request = new SendTextMessage(profile.getUsername(), textMessage);
            request.setSessionId(sessionId);
            addMessageToHistory(new TextMessage(profile.getUsername(), textMessage));
            sentRequests.put(request);
            deliveryService.sendMessage(request);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoginSubmit(String username) {
        try {
            deliveryService.sendMessage(new Login(username, "TYPE"));
            this.queriedUsername = username;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
