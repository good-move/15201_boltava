package ru.nsu.ccfit.boltava.view;

import ru.nsu.ccfit.boltava.model.message.TextMessage;
import ru.nsu.ccfit.boltava.model.message.event.UserJoinedChatEvent;
import ru.nsu.ccfit.boltava.model.message.event.UserLeftChatEvent;

import javax.swing.*;
import java.awt.*;


public class ChatView extends JComponent implements IChatMessageRenderer {

    private JPanel panel;
    private JList<String> messageList;
    private DefaultListModel<String> model = new DefaultListModel<>();
    private JScrollPane scrollPane;

    ChatView() {
        ChatMessageRenderer renderer = new ChatMessageRenderer();
        messageList.setCellRenderer(renderer);
        messageList.setModel(model);
        JViewport viewport = scrollPane.getViewport();
        viewport.addChangeListener(e -> {
            renderer.setWidth(viewport.getWidth()  - 10);
            scrollPane.validate();
            scrollPane.repaint();
        });
    }

    private void renderMessage(String message) {
        model.addElement(message);
        SwingUtilities.invokeLater(this::autoscroll);
        scrollPane.validate();
        scrollPane.repaint();
    }

    private void autoscroll() {
        int lastIndex = model.getSize() - 1;
        if (lastIndex >= 0) {
            messageList.ensureIndexIsVisible(lastIndex);
        }
    }

    @Override
    public void render(TextMessage msg) {
        renderMessage(String.format(StylesBundle.TEXT_MESSAGE_MARKUP, msg.getAuthor(), msg.getMessage()));
    }

    @Override
    public void render(UserLeftChatEvent msg) {
        renderMessage(String.format(StylesBundle.USERLIST_EVENT_MARKUP, msg.getUsername(), "left chat" ));
    }

    @Override
    public void render(UserJoinedChatEvent msg) {
        renderMessage(String.format(StylesBundle.USERLIST_EVENT_MARKUP, msg.getUsername(), "joined chat" ));
    }


    private class ChatMessageRenderer extends DefaultListCellRenderer {

        private int width = super.getWidth();

        private static final String CHAT_MESSAGE_FORMAT =
                "<html>" +
                    "<body><p WIDTH=%d style='padding:5px'>%s</p</body>" +
                "</html>";

        @Override
        public Component getListCellRendererComponent(JList list, Object object,
                                                      int index, boolean isSelected, boolean hasFocus) {
            final String text = String.format(CHAT_MESSAGE_FORMAT, width, object.toString());
            return super.getListCellRendererComponent(list, text, index, isSelected, hasFocus);
        }

        void setWidth(int width) {
            this.width = width;
        }

    }

    private static class StylesBundle {

        static final String TEXT_MESSAGE_MARKUP = (
                    "<span style='color:blue;'>%s</span><br>" +
                    "<span style='font-size:1.1em;'>%s</span>"
        );

        static final String USERLIST_EVENT_MARKUP = (
                    "<span style='color:gray'>%s %s</span>"
        );

    }

}
