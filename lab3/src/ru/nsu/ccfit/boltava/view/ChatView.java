package ru.nsu.ccfit.boltava.view;

import ru.nsu.ccfit.boltava.model.message.TextMessage;

import javax.swing.*;

public class ChatView extends JComponent implements IChatMessageRenderer {

    private JPanel panel;
    private JList<String> messageList;
    private DefaultListModel<String> model = new DefaultListModel<>();
    private JScrollPane scrollPane;

    private final String MESSAGE_MARKUP = (
            "<html>" +
                    "<div style='padding:5px; max-width=%d'>" +
                        "<p style='color:blue; font-size:1em'>%s</p>" +
                        "<p style='font-size:1.1em;'>%s</p>" +
                    "</div>" +
            "</html>"
    );

    ChatView() {
        messageList.setModel(model);
    }

    @Override
    public void render(TextMessage msg) {
        model.addElement(String.format(MESSAGE_MARKUP, scrollPane.getViewport().getWidth(), msg.getAuthor(), msg.getMessage()));
        SwingUtilities.invokeLater(() -> {
            int lastIndex = model.getSize() - 1;
            if (lastIndex >= 0) {
                messageList.ensureIndexIsVisible(lastIndex);
            }
        });
    }

}
