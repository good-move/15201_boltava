package ru.nsu.ccfit.boltava.view;

import ru.nsu.ccfit.boltava.model.chat.User;

import javax.swing.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class UserList extends JComponent implements  IUserListObserver {

    private HashSet<String> usernames = new HashSet<>();
    private JList<String> userListComponent;
    private DefaultListModel<String> model = new DefaultListModel<>();
    private JPanel panel;
    private JScrollPane scrollPane;
    private JLabel title;

    UserList() {
        userListComponent.setModel(model);
        title.setText("Online users");
    }

    UserList(List<String> usernames) {
        this();
        this.usernames = new HashSet<>(usernames);
        this.usernames.forEach(u -> model.addElement(u));
    }

    @Override
    public void onUserListSet(List<User> users) {
        this.usernames = new HashSet<>(
                users.stream()
                        .map(User::getUsername)
                        .collect(Collectors.toCollection(ArrayList::new))
        );
        model.clear();
        this.usernames.forEach(u -> model.addElement(u));
    }

    @Override
    public void onUserJoined(String username) {
        if (!usernames.contains(username)) {
            usernames.add(username);
            model.addElement(username);
        }
    }

    @Override
    public void onUserLeft(String username) {
        usernames.remove(username);
        model.removeElement(username);
    }

}
