package ru.nsu.ccfit.boltava.view;

import ru.nsu.ccfit.boltava.model.message.message_content.TextMessage;

import javax.swing.*;

public class MessageView extends JComponent {
    private JPanel panel;
    private JLabel authorLabel;
    private JLabel messageLabel;

    MessageView(TextMessage message) {
        authorLabel.setText(message.getAuthor());
        messageLabel.setText(message.getMessageText());
    }
}
