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
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.LinkedBlockingQueue;

public class Client implements IMessageInputPanelEventListener, IOnLoginSubmitListener {


    private final static Logger WORKFLOW_LOGGER = LogManager.getLogger(Client.class);
    private final static Logger CONSOLE_LOGGER = LogManager.getLogger("ConsoleLogger");

    private Chat chat;
    private LoginView loginView;

    private User profile;
    private String sessionId;
    private String clientType;
    private String queriedUsername;
    private List<User> onlineUsers = new ArrayList<>();
    private ArrayList<TextMessage> chatHistory = new ArrayList<>();

    private boolean isLoggedIn = false;

    private Client client;
    private DeliveryService deliveryService;
    private ClientMessageHandler messageHandler;

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

            clientType = config.getStreamType().toString();

            messageHandler = new ClientMessageHandler(client);
            deliveryService = new DeliveryService(config, messageHandler);
            deliveryService.start();

            loginView = new LoginView(client);
            loginView.addOnLoginSubmitListener(client);
        } catch (IOException e) {
            CONSOLE_LOGGER.error("Communication with server crashed. Check connection configuration: " + e.getMessage());
        } catch (JAXBException e) {
            CONSOLE_LOGGER.error("Failed to initialize xml stream: " + e.getMessage());
        } catch (NumberFormatException e) {
            CONSOLE_LOGGER.error("Failed to parse config. " + e.getMessage());
        } catch (Exception e) {
            CONSOLE_LOGGER.error("Failed to start client. " + e.getMessage());
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
            WORKFLOW_LOGGER.info(e.getMessage());
        }
    }

    //    ****************************** Client state control methods ******************************

    public String getSessionId() {
        return sessionId;
    }

    public User getProfile() {
        return profile;
    }

    public List<User> getOnlineUsers() {
        return onlineUsers;
    }

    void setOnlineUsers(List<User> onlineUsers) {
        this.onlineUsers = onlineUsers;
    }

    public ArrayList<TextMessage> getChatHistory() {
        return chatHistory;
    }

    public void setChatHistory(ArrayList<TextMessage> chatHistory) {
        this.chatHistory = chatHistory;
    }

    void addMessageToHistory(TextMessage msg) {
        chatHistory.add(msg);
    }

    void onLoginSuccess(String sessionId) {
        if (isLoggedIn) {
            throw new RuntimeException("Double login on client side");
        }
        try {
            this.isLoggedIn = true;
            this.sessionId = sessionId;
            this.profile = new User(queriedUsername, clientType);

            loginView.setVisible(false);
            chat = new Chat(client);

            sendRequest(new GetUserListRequest(sessionId));
        } catch (InterruptedException e) {
            e.printStackTrace();
            WORKFLOW_LOGGER.info(e.getMessage());
        }
    }

    void onLoginError(LoginError error) {
        loginView.displayError(error.getErrorMessage());
    }

    private LinkedBlockingQueue<Request> getSentRequestsQueue() {
        return sentRequestsQueue;
    }

    private void sendRequest(Request request) throws InterruptedException {
        deliveryService.sendMessage(request);
        sentRequestsQueue.put(request);
    }

    //    ****************************** Interface methods ******************************

    public void addUserListObserver(IUserListObserver observer) {
        messageHandler.addUserListObserver(observer);
    }

    public void addChatMessageRenderer(IChatMessageRenderer renderer) {
        messageHandler.addMessageRenderer(renderer);
    }

    @Override
    public void onTextMessageSubmit(String textMessage) {
        try {
            sendRequest(new PostTextMessageRequest(sessionId, textMessage));
            TextMessage message = new TextMessage(profile.getUsername(), textMessage);
            chatHistory.add(message);
            messageHandler.renderTextMessage(message);
        } catch (InterruptedException e) {
            WORKFLOW_LOGGER.info(e.getMessage());
        }
    }

    @Override
    public void onLoginSubmit(String username) {
        try {
            sendRequest(new LoginRequest(username, clientType));
            this.queriedUsername = username;
        } catch (InterruptedException e) {
            WORKFLOW_LOGGER.info(e.getMessage());
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
            mSocket.setKeepAlive(true);
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
                    WORKFLOW_LOGGER.error(e.getMessage());
                    CONSOLE_LOGGER.info("Communication stopped. Check log files for possible errors");
                } finally {
                    WORKFLOW_LOGGER.info("interrupted");
                    stop();
                }
            }, "Sender Thread");

            mReceiverThread = new Thread(() -> {
                try {
                    while (!Thread.interrupted()) {
                        ServerMessage msg = mStream.read();
                        msg.handle(mMsgHandler);
                    }
                } catch (NullPointerException e) {
                    WORKFLOW_LOGGER.error("Null pointer");
                } catch (IMessageSerializer.MessageSerializationException |
                        ISocketMessageStream.StreamReadException e) {
                    CONSOLE_LOGGER.info("Communication stopped. Check log files for possible errors");
                    WORKFLOW_LOGGER.error(e.getMessage());
                    if (e.getCause() != null && e.getCause().getClass().equals(EOFException.class)) {
                        CONSOLE_LOGGER.info("Lost connection with server");
                    }
                } finally {
                    WORKFLOW_LOGGER.info( "interrupted");
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
                WORKFLOW_LOGGER.error(e.getMessage());
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
                    LoginError.class,
                    LoginSuccess.class,
                    LogoutRequest.class,
                    LogoutSuccess.class,
                    LogoutError.class,
                    PostTextMessageRequest.class,
                    PostTextMessageSuccess.class,
                    PostTextMessageError.class,
                    GetUserListRequest.class,
                    GetUserListSuccess.class,
                    GetUserListError.class
            };

            try {
                jaxbContext = JAXBContext.newInstance(classes);
            } catch (JAXBException e) {
                WORKFLOW_LOGGER.error(e.getMessage());
            }
        }


        static JAXBContext getContext() {
            return jaxbContext;
        }

    }

}
