package ru.nsu.ccfit.boltava.view;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.regex.*;

public class LabeledSliderWithTextField extends JComponent {
    private HashSet<IOnValueChangedListener<Integer>> mOnValueChangedListeners = new HashSet<>();

    private JSlider mSlider;
    private JLabel mLabel;
    private JTextField mTextField;
    private JPanel mPanel;

    private static Pattern mTimeoutPattern = Pattern.compile("\\d+");

    public LabeledSliderWithTextField(String labelText, int min, int max, int spacing) {
        mLabel.setText(labelText);

        mSlider.setMinimum(min);
        mSlider.setMaximum(max);
        mSlider.setMajorTickSpacing(spacing);
        mSlider.setMinorTickSpacing(spacing);
        mSlider.addMouseListener(new MouseEventsListener());
        mSlider.addChangeListener(new SliderChangeListener());

        mTextField.addKeyListener(new KeyEventsListener());
        mTextField.setText(String.valueOf((mSlider.getMaximum() - mSlider.getMinimum()) / 2));
        onTimeoutValueChanged();
    }

    public void addOnValueChangedListener(IOnValueChangedListener<Integer> listener) {
        mOnValueChangedListeners.add(listener);
    }

    public void removeOnValueChangedListener(IOnValueChangedListener<Integer> listener) {
        mOnValueChangedListeners.remove(listener);
    }

    private void onValueChanged(int value) {
        mOnValueChangedListeners.forEach(listener -> listener.onValueChanged(value));
    }


    private void onTimeoutValueChanged() {
        String timeoutString = mTextField.getText().trim();
        if (isTimeoutFormatValid(timeoutString)) {
            mTextField.setBackground(Color.white);
            Integer timeout = Integer.parseInt(timeoutString);
            if (timeout >= mSlider.getMinimum() && timeout <= mSlider.getMaximum()) {
                mSlider.setValue(timeout);
                onValueChanged(timeout);
            } else {
                mTextField.setBackground(Color.red);
            }
        } else {
            mTextField.setBackground(Color.red);
        }
    }

    private boolean isTimeoutFormatValid(String timeoutString) {
        return mTimeoutPattern.matcher(timeoutString).matches();
    }

    private class MouseEventsListener extends MouseAdapter {

        @Override
        public void mouseReleased(MouseEvent event) {
            onTimeoutValueChanged();
        }

    }

    private class KeyEventsListener extends KeyAdapter {

        @Override
        public void keyReleased(KeyEvent keyEvent) {
            onTimeoutValueChanged();
        }

    }

    private class SliderChangeListener implements ChangeListener {

        @Override
        public void stateChanged(ChangeEvent changeEvent) {
            mTextField.setBackground(Color.white);
            mTextField.setText(String.valueOf(mSlider.getValue()));
        }
    }


}
