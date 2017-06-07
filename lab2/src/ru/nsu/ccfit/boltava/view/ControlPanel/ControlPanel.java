package ru.nsu.ccfit.boltava.view.ControlPanel;

import ru.nsu.ccfit.boltava.model.FactoryManager;

import javax.swing.*;

public class ControlPanel extends JComponent {

    private LabeledSliderWithTextField mDealersTimeoutControl;
    private LabeledSliderWithTextField mBodySupplierTimeoutControl;
    private LabeledSliderWithTextField mEngineSupplierTimeoutControl;
    private LabeledSliderWithTextField mAccessorySupplierTimeoutControl;
    private JPanel mPanel;
    private JLabel mTitle;

    private static int MIN_TIMEOUT = 1;
    private static int MAX_TIMEOUT = 10000;
    private static int SPACING = 10;

    public ControlPanel(FactoryManager factoryManager) {
        mTitle.setText("Timeout Control Panel");
        factoryManager.getDealers().forEach(dealer -> mDealersTimeoutControl.addOnValueChangedListener(dealer));
        factoryManager.getEngineSuppliers().forEach(supplier -> mEngineSupplierTimeoutControl.addOnValueChangedListener(supplier));
        factoryManager.getBodySuppliers().forEach(supplier -> mBodySupplierTimeoutControl.addOnValueChangedListener(supplier));
        factoryManager.getAccessorySuppliers().forEach(supplier -> mAccessorySupplierTimeoutControl.addOnValueChangedListener(supplier));
    }

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
