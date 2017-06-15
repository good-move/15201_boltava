package ru.nsu.ccfit.boltava.view;

import ru.nsu.ccfit.boltava.model.chat.User;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;

public class UserList extends JComponent implements  IUserListObserver {

    private HashSet<User> users;
    private JList<String> userListComponent;
    private DefaultListModel<String> model;
    private JPanel panel;
    private JScrollPane scrollPane;

    UserList(ArrayList<User> users) {
        this.users = new HashSet<>(users);
        model = new DefaultListModel<>();
        users.forEach(user -> model.addElement(user.getUsername()));
        userListComponent.setModel(model);
    }

    @Override
    public void onUserJoined(User user) {
        if (!users.contains(user)) {
            users.add(user);
            model.addElement(user.getUsername());
        }
    }

    @Override
    public void onUserLeft(User user) {
        users.remove(user);
        model.removeElement(user.getUsername());
    }

}
