package ru.nsu.ccfit.boltava.view;

import ru.nsu.ccfit.boltava.model.client.Client;

import javax.swing.*;

public class MainView extends JComponent {
    private JPanel panel;
    private ChatView chatView;
    private MessageInputPanel messageInputPanel;

    MainView(Client client) {
        messageInputPanel.addMessageInputPanelEventListener(client);
        client.addChatMessageRenderer(chatView);
    }

}