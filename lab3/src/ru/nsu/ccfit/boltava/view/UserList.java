package ru.nsu.ccfit.boltava.view;

import javax.swing.*;
import java.util.HashSet;
import java.util.List;

public class UserList extends JComponent implements  IUserListObserver {

    private HashSet<String> usernames;
    private JList<String> userListComponent;
    private DefaultListModel<String> model;
    private JPanel panel;
    private JScrollPane scrollPane;
    private JLabel title;

    UserList(List<String> usernames) {
        this.usernames = new HashSet<>(usernames);
        model = new DefaultListModel<>();
        this.usernames.forEach(u -> model.addElement(u));
        userListComponent.setModel(model);

        title.setText("Online users");
    }

    @Override
    public void onUserListSet(List<String> usernames) {
        this.usernames = new HashSet<>(usernames);
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
