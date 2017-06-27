package ru.nsu.ccfit.boltava.view;

import ru.nsu.ccfit.boltava.model.client.Client;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Chat extends JFrame {

    private Client client;
    private JPanel panel;
    private MainView mainView;
    private UserList userListPanel;

    public Chat(Client client) {
        this.client = client;

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent windowEvent) {
                client.appViewClosed();
            }
        });

        this.setTitle("Chat");
        this.setContentPane(panel);
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setMinimumSize(new Dimension(400, 300));
    }

    private void createUIComponents() {
        userListPanel = new UserList();
        client.addUserListObserver(userListPanel);
        mainView = new MainView(client);
    }

}
