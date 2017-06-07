package ru.nsu.ccfit.boltava.view.ProductionStatistics;

import ru.nsu.ccfit.boltava.view.IOnValueChangedListener;

import javax.swing.*;

public class ProductionInfoRow extends JComponent implements IOnValueChangedListener<Integer> {
    private JPanel mPanel;
    private JLabel mTitle;
    private JLabel mValue;

    ProductionInfoRow(String title, String value) {
        mTitle.setText(title);
        mValue.setText(value);
    }

    @Override
    public void onValueChanged(Integer value) {
        mValue.setText(String.valueOf(value));
    }

}
