package ru.nsu.ccfit.boltava.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;

public class MessageInputPanel extends JComponent {

    private JPanel panel;
    private JTextArea msgInputField;
    private JButton sendBtn;

    private static final String SUBMIT_TEXT_MESSAGE = "text-submit";
    private static final String INSERT_BREAK = "insert-break";

    private ArrayList<IMessageInputPanelEventListener> listeners = new ArrayList<>();

    MessageInputPanel() {
        sendBtn.setText("Send");
        sendBtn.addActionListener((e) -> {
            submitTextMessage();
        });

        InputMap input = msgInputField.getInputMap();
        KeyStroke enter = KeyStroke.getKeyStroke("ENTER");
        KeyStroke shiftEnter = KeyStroke.getKeyStroke("shift ENTER");
        input.put(shiftEnter, INSERT_BREAK);
        input.put(enter, SUBMIT_TEXT_MESSAGE);


        ActionMap actions = msgInputField.getActionMap();
        actions.put(INSERT_BREAK, actions.get(enter));
        actions.put(SUBMIT_TEXT_MESSAGE, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                submitTextMessage();
            }
        });

    }

    public void addMessageInputPanelEventListener(IMessageInputPanelEventListener listener) {
        listeners.add(listener);
    }

    private void submitTextMessage() {
        String text = msgInputField.getText();
        if (text.length() > 0) {
            listeners.forEach(l -> l.onTextMessageSubmit(text));
            msgInputField.setText("");
        }
    }

}
