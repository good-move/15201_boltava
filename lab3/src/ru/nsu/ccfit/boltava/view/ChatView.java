package ru.nsu.ccfit.boltava.view;

import ru.nsu.ccfit.boltava.model.message.TextMessage;
import ru.nsu.ccfit.boltava.model.message.event.UserJoinedChatEvent;
import ru.nsu.ccfit.boltava.model.message.event.UserLeftChatEvent;

import javax.swing.*;

public class ChatView extends JComponent implements IChatMessageRenderer {

    private JPanel panel;
    private JList<String> messageList;
    private DefaultListModel<String> model = new DefaultListModel<>();
    private JScrollPane scrollPane;

    private static final String TEXT_MESSAGE_MARKUP = (
        "<html>" +
            "<div style='padding:5px; max-width=%d'>" +
                "<p style='color:blue; font-size:1em'>%s</p>" +
                "<p style='font-size:1.1em;'>%s</p>" +
            "</div>" +
        "</html>"
    );

    private static final String USERLIST_EVENT_MARKUP = (
        "<html>" +
            "<div style='padding:5px; max-width=%d;'>" +
                "<p style='color:gray'>%s %s</p>" +
            "</div>" +
        "</html>"
    );

    ChatView() {
        messageList.setModel(model);
    }

    private void renderMessage(String message) {
        model.addElement(message);
        SwingUtilities.invokeLater(() -> {
            int lastIndex = model.getSize() - 1;
            if (lastIndex >= 0) {
                messageList.ensureIndexIsVisible(lastIndex);
            }
        });
    }

    @Override
    public void render(TextMessage msg) {
        renderMessage(String.format(TEXT_MESSAGE_MARKUP, scrollPane.getViewport().getWidth(), msg.getAuthor(), msg.getMessage()));
    }

    @Override
    public void render(UserLeftChatEvent msg) {
        renderMessage(String.format(USERLIST_EVENT_MARKUP, scrollPane.getViewport().getWidth(), msg.getUsername(), "left chat" ));
    }

    @Override
    public void render(UserJoinedChatEvent msg) {
        renderMessage(String.format(USERLIST_EVENT_MARKUP, scrollPane.getViewport().getWidth(), msg.getUsername(), "joined chat" ));
    }

}
