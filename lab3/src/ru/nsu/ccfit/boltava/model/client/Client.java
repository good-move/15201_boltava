package ru.nsu.ccfit.boltava.model.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.nsu.ccfit.boltava.model.chat.User;
import ru.nsu.ccfit.boltava.model.message.Request;
import ru.nsu.ccfit.boltava.model.message.ServerMessage;
import ru.nsu.ccfit.boltava.model.message.TextMessage;
import ru.nsu.ccfit.boltava.model.message.event.NewTextMessageEvent;
import ru.nsu.ccfit.boltava.model.message.event.UserJoinedChatEvent;
import ru.nsu.ccfit.boltava.model.message.event.UserLeftChatEvent;
import ru.nsu.ccfit.boltava.model.message.request.GetUserListRequest;
import ru.nsu.ccfit.boltava.model.message.request.LoginRequest;
import ru.nsu.ccfit.boltava.model.message.request.LogoutRequest;
import ru.nsu.ccfit.boltava.model.message.request.PostTextMessageRequest;
import ru.nsu.ccfit.boltava.model.message.response.*;
import ru.nsu.ccfit.boltava.model.net.ClientObjectStream;
import ru.nsu.ccfit.boltava.model.net.ClientXMLStream;
import ru.nsu.ccfit.boltava.model.net.IClientSocketMessageStream;
import ru.nsu.ccfit.boltava.model.net.ISocketMessageStream;
import ru.nsu.ccfit.boltava.model.serializer.IMessageSerializer;
import ru.nsu.ccfit.boltava.model.serializer.XMLSerializer;
import ru.nsu.ccfit.boltava.view.*;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;

public class Client implements IMessageInputPanelEventListener, IOnLoginSubmitListener {

    private static Logger logger = LogManager.getLogger("ConsoleLogger");

    private Chat chat;
    private LoginView loginView;

    private User profile;
    private String sessionId;
    private String queriedUsername;
    private List<String> onlineUsers = new ArrayList<>();
    private ArrayList<TextMessage> chatHistory = new ArrayList<>();

    private boolean isLoggedIn = false;

    private Client client;
    private DeliveryService deliveryService;
    private ClientMessageHandler messageHandler;

    private HashSet<IChatMessageRenderer> chatMessageRenderers = new HashSet<>();
    private LinkedBlockingQueue<Request> sentRequestsQueue = new LinkedBlockingQueue<>();


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

            messageHandler = new ClientMessageHandler(client);
            deliveryService = new DeliveryService(config, messageHandler);
            deliveryService.start();

            loginView = new LoginView(client);
            loginView.addOnLoginSubmitListener(client);
        } catch (IOException | JAXBException e) {
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
            chat.dispose();
        } catch (NullPointerException e) {

        }
    }

    //    ****************************** Client state control methods ******************************

    public void addUserListObserver(IUserListObserver observer) {
        messageHandler.addUserListObserver(observer);
    }

    public String getSessionId() {
        return sessionId;
    }

    public User getProfile() {
        return profile;
    }

    public List<String> getOnlineUsers() {
        return onlineUsers;
    }

    public void setOnlineUsers(List<String> onlineUsers) {
        this.onlineUsers = onlineUsers;
        // update listeners
    }

    public ArrayList<TextMessage> getChatHistory() {
        return chatHistory;
    }

    public void setChatHistory(ArrayList<TextMessage> chatHistory) {
        this.chatHistory = chatHistory;
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

            sendRequest(new GetUserListRequest(sessionId));

            loginView.setVisible(false);
            chat = new Chat(client);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private LinkedBlockingQueue<Request> getSentRequestsQueue() {
        return sentRequestsQueue;
    }

    private void sendRequest(Request request) throws InterruptedException {
        deliveryService.sendMessage(request);
        sentRequestsQueue.put(request);
    }

    //    ****************************** Interface methods ******************************

    public void addChatMessageRenderer(IChatMessageRenderer renderer) {
        chatMessageRenderers.add(renderer);
    }

    @Override
    public void onTextMessageSubmit(String textMessage) {
        try {
            sendRequest(new PostTextMessageRequest(sessionId, textMessage));
            addMessageToHistory(new TextMessage(profile.getUsername(), textMessage));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoginSubmit(String username) {
        try {
            sendRequest(new LoginRequest(username, "TYPE"));
            this.queriedUsername = username;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class DeliveryService {

        private boolean isStarted = false;
        private boolean isStopped = false;
        private IClientSocketMessageStream mStream;
        private Thread mSenderThread;
        private Thread mReceiverThread;
        private Socket mSocket;
        private IClientMessageHandler mMsgHandler;
        private LinkedBlockingQueue<Request> mSendMsgQueue = new LinkedBlockingQueue<>();

        private DeliveryService(ConnectionConfig connectionConfig, IClientMessageHandler handler) throws IOException, JAXBException {
            mSocket = new Socket(connectionConfig.getHost(), connectionConfig.getPort());
            mMsgHandler = handler;

            switch (connectionConfig.getStreamType()) {
                case OBJ: mStream = new ClientObjectStream(mSocket); break;
                case XML: {
                    XMLSerializer serializer = new XMLSerializer(XMLContext.getContext());
                    serializer.setRequestQueue(client.getSentRequestsQueue());
                    mStream = new ClientXMLStream(mSocket, serializer);
                    break;
                    }
                default: throw new RuntimeException("Unknown stream type: " + connectionConfig.getStreamType().toString());
            }

            mSenderThread = new Thread(() -> {
                try {
                    while (!Thread.interrupted()) {
                        mStream.write(mSendMsgQueue.take());
                    }
                } catch (InterruptedException |
                        ISocketMessageStream.StreamWriteException |
                        IMessageSerializer.MessageSerializationException e) {
                    e.printStackTrace();
                } finally {
                    logger.info("interrupted");
                    stop();
                }
            }, "Sender Thread");

            mReceiverThread = new Thread(() -> {
                try {
                    while (!Thread.interrupted()) {
                        ServerMessage msg = mStream.read();
                        msg.handle(mMsgHandler);
                    }
                } catch (IMessageSerializer.MessageSerializationException | ISocketMessageStream.StreamReadException e) {
                    e.printStackTrace();
                } finally {
                    logger.info( "interrupted");
                    stop();
                }
            }, "Receiver Thread");

        }

        public void sendMessage(Request msg) throws InterruptedException {
            mSendMsgQueue.put(msg);
        }

        public void start() {
            if (isStarted) throw new IllegalStateException("Delivery Service cannot be started more than once");
            mSenderThread.start();
            mReceiverThread.start();
            isStarted = true;
        }

        public void stop() {
            try {
                isStopped = true;
                mSocket.close();
                mSenderThread.interrupt();
                mReceiverThread.interrupt();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static class XMLContext {

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


        public static JAXBContext getContext() {
            return jaxbContext;
        }

    }

}
