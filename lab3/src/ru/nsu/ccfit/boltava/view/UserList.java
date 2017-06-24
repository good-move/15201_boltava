package ru.nsu.ccfit.boltava.view;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;

public class UserList extends JComponent implements  IUserListObserver {

    private HashSet<String> usersnames;
    private JList<String> userListComponent;
    private DefaultListModel<String> model;
    private JPanel panel;
    private JScrollPane scrollPane;

    UserList(ArrayList<String> usernames) {
        this.usersnames = new HashSet<>(usernames);
        model = new DefaultListModel<>();
        this.usersnames.forEach(u -> model.addElement(u));
        userListComponent.setModel(model);
    }

    @Override
    public void onUserJoined(String username) {
        if (!usersnames.contains(username)) {
            usersnames.add(username);
            model.addElement(username);
        }
    }

    @Override
    public void onUserLeft(String username) {
        usersnames.remove(username);
        model.removeElement(username);
    }

}
