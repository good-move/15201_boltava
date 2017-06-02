package ru.nsu.ccfit.boltava.view;

import javax.swing.*;

public class ControlPanel extends JComponent {

    private LabeledSliderWithTextField mDealersTimeoutControl;
    private LabeledSliderWithTextField mBodySupplierTimeoutControl;
    private LabeledSliderWithTextField mEngineSupplierTimeoutControl;
    private LabeledSliderWithTextField mAccessorySupplierTimeoutControl;
    private JPanel mPanel;

    private static int MIN_TIMEOUT = 1;
    private static int MAX_TIMEOUT = 10000;
    private static int SPACING = 10;

    private void createUIComponents() {
        mDealersTimeoutControl = new LabeledSliderWithTextField(
                "Dealers", MIN_TIMEOUT, MAX_TIMEOUT, SPACING);
        mBodySupplierTimeoutControl = new LabeledSliderWithTextField(
                "Body", MIN_TIMEOUT, MAX_TIMEOUT, SPACING);
        mEngineSupplierTimeoutControl = new LabeledSliderWithTextField(
                "Engine", MIN_TIMEOUT, MAX_TIMEOUT, SPACING);
        mAccessorySupplierTimeoutControl = new LabeledSliderWithTextField(
                "Accessory", MIN_TIMEOUT, MAX_TIMEOUT, SPACING);
    }

}
