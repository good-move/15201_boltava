package ru.nsu.ccfit.boltava.view;

import ru.nsu.ccfit.boltava.model.FactoryManager;
import ru.nsu.ccfit.boltava.view.ControlPanel.ControlPanel;
import ru.nsu.ccfit.boltava.view.ProductionStatistics.ProductionStatisticsPanel;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow extends JFrame {

    private ControlPanel mControlPanel;
    private FactoryManager mFactoryManager;
    private JPanel mPanel;
    private CarSalesPanel mCarSalesPanel;
    private ProductionStatisticsPanel mProductionStatsPanel;


    public MainWindow(FactoryManager factoryManager) {
        super("Factory");

        mFactoryManager = factoryManager;

        this.$$$setupUI$$$();
        this.setContentPane(mPanel);
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

        this.setVisible(true);
        mFactoryManager.launchFactory();
    }

    private void createUIComponents() {
        mControlPanel = new ControlPanel(mFactoryManager);
        String[] rows = mFactoryManager.getCarSerials();

        mCarSalesPanel = new CarSalesPanel(rows);
        mFactoryManager.getCarStorageManager().addCarPurchasedListener(mCarSalesPanel);

        mProductionStatsPanel = new ProductionStatisticsPanel(mFactoryManager);
    }

    private void $$$setupUI$$$() {
        createUIComponents();

        mPanel = new JPanel();

    }

}
