package ru.nsu.ccfit.boltava.view;

import ru.nsu.ccfit.boltava.model.message.TextMessage;
import ru.nsu.ccfit.boltava.model.message.event.UserJoinedChatEvent;
import ru.nsu.ccfit.boltava.model.message.event.UserLeftChatEvent;

import javax.swing.*;
import java.awt.*;


public class ChatView extends JComponent implements IChatMessageRenderer {

    private final int BORDER_OFFSET = 10;
    private JPanel panel;
    private JList<String> messageList;
    private DefaultListModel<String> model = new DefaultListModel<>();
    private JScrollPane scrollPane;

    ChatView() {
        ListElementRenderer renderer = new ListElementRenderer();
        messageList.setCellRenderer(renderer);
        messageList.setModel(model);
        messageList.setVisible(true);
    }

    @Override
    public void render(TextMessage msg) {
        SwingUtilities.invokeLater(
                new Renderer(String.format(StylesBundle.TEXT_MESSAGE_MARKUP, msg.getAuthor(), msg.getMessage()))
        );
    }

    @Override
    public void render(UserLeftChatEvent msg) {
        SwingUtilities.invokeLater(
                new Renderer(String.format(StylesBundle.USERLIST_EVENT_MARKUP, msg.getUsername(), "left chat" ))
        );
    }

    @Override
    public void render(UserJoinedChatEvent msg) {
        SwingUtilities.invokeLater(
                new Renderer(String.format(StylesBundle.USERLIST_EVENT_MARKUP, msg.getUsername(), "joined chat" ))
        );
    }

    private class Renderer implements Runnable {

        String message;

        Renderer(String message) {
            this.message = message;
        }

        @Override
        public void run() {
            model.addElement(message);
            SwingUtilities.invokeLater(this::scrollToBottom);
        }

        private void scrollToBottom() {
            int lastIndex = model.getSize() - 1;
            if (lastIndex >= 0) {
                messageList.ensureIndexIsVisible(lastIndex);
            }
        }

    }


    private class ListElementRenderer extends DefaultListCellRenderer {

        private int width = super.getWidth();

        private static final String CHAT_MESSAGE_FORMAT =
                "<html>" +
                    "<body><p WIDTH=%d style='padding:5px'>%s</p</body>" +
                "</html>";

        @Override
        public Component getListCellRendererComponent(JList list, Object object,
                                                      int index, boolean isSelected, boolean hasFocus) {
            width = scrollPane.getViewport().getWidth()  - BORDER_OFFSET;
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
