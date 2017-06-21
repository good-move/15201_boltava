package ru.nsu.ccfit.boltava.view;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

public class LoginView extends JFrame implements IOnValueChangedListener<String> {

    private JTextField usernameTextField;
    private JPanel panel;
    private JLabel titleLabel;
    private JLabel errorLabel;
    private JButton loginBtn;

    private ArrayList<IOnLoginSubmitListener> listeners = new ArrayList<>();

    public LoginView() {
        titleLabel.setText("GChat");
        errorLabel.setText("");
        loginBtn.setText("Log in");
        loginBtn.addActionListener((e) -> {
            submitLogin();
        });

        usernameTextField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent keyEvent) {
                if (keyEvent.getKeyCode() == KeyEvent.VK_ENTER) {
                    submitLogin();
                }
            }
        });

        this.setContentPane(panel);
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.pack();
        this.setVisible(true);
    }

    private void submitLogin() {
        String username = usernameTextField.getText();
        if (username.length() > 0) {
            listeners.forEach(l -> l.onLoginSubmit(username));
        }
    }

    public void addOnLoginSubmitListener(IOnLoginSubmitListener listener) {
        listeners.add(listener);
    }

    @Override
    public void onValueChanged(String value) {
        errorLabel.setText(value);
    }
}
