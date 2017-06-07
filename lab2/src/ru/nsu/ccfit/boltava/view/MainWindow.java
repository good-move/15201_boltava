package ru.nsu.ccfit.boltava.view;

import ru.nsu.ccfit.boltava.model.FactoryManager;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow extends JFrame {

    private JPanel mRootPanel;
    private ControlPanel mControlPanel;
    private JLabel mControlPanelTitle;
    private CarSalesPanel mWorkflowStats;
    private FactoryManager mFactoryManager;


    public MainWindow(FactoryManager factoryManager) {
        super("Factory");

        mFactoryManager = factoryManager;


        this.$$$setupUI$$$();
        this.setContentPane(mRootPanel);
        this.setResizable(false);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.pack();

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent windowEvent) {
                factoryManager.stopFactory();
            }
        });

        mControlPanelTitle.setText("Timeout Control Panel");

        this.setVisible(true);
        mFactoryManager.launchFactory();
    }

    private void createUIComponents() {
        mControlPanel = new ControlPanel(mFactoryManager);
        String[] rows = mFactoryManager.getCarSerials();

        mWorkflowStats = new CarSalesPanel(rows);
        mFactoryManager.getCarStorageManager().addCarPurchasedListener(mWorkflowStats);
    }

    private void $$$setupUI$$$() {
        createUIComponents();

        mRootPanel = new JPanel();

    }

}
