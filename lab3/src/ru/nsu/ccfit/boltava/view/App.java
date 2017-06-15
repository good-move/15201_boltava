package ru.nsu.ccfit.boltava.view;

import ru.nsu.ccfit.boltava.model.client.Client;

import javax.swing.*;
import java.awt.*;

public class App extends JFrame {

    Client client;
    private JPanel panel;
    private MainView mainView;
    private UserList userListPanel;

    public App(Client client) {
        this.client = client;

        this.setContentPane(panel);
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.setMinimumSize(new Dimension(400, 300));
    }

    private void createUIComponents() {
        userListPanel = new UserList(client.getOnlineUsers());
        mainView = new MainView(client);
    }

}
