package ru.nsu.ccfit.boltava.view;

import ru.nsu.ccfit.boltava.model.EnvironmentConfiguration;
import ru.nsu.ccfit.boltava.model.FactoryManager;
import ru.nsu.ccfit.boltava.view.ControlPanel.ControlPanel;
import ru.nsu.ccfit.boltava.view.ProductionStatistics.ProductionStatisticsPanel;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class MainWindow extends JFrame {

    private final static String TOGGLE_FACTORY_POWER_ACTION = "toggle_factory_power_action";
    private final static String START_LABEL = "Start";
    private final static String STOP_LABEL = "Stop";

    private ControlPanel mControlPanel;
    private FactoryManager mFactoryManager;
    private JPanel mPanel;
    private TableDataPanel mCarSalesPanel;
    private ProductionStatisticsPanel mProductionStatsPanel;
    private TableDataPanel mEngineStorageLoadPanel;
    private TableDataPanel mAccessoryStorageLoadPanel;
    private TableDataPanel mBodyStorageLoadPanel;
    private JButton mPowerButton;


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

        mPowerButton.setText(START_LABEL);
        mPowerButton.setActionCommand(TOGGLE_FACTORY_POWER_ACTION);
        mPowerButton.addActionListener(actionEvent -> {
            switch (actionEvent.getActionCommand()) {
                case TOGGLE_FACTORY_POWER_ACTION:
                    handleOnToggleFactoryAction();
                default:
            }
        });
    }

    private void createUIComponents() {
        mControlPanel = new ControlPanel(mFactoryManager);
        String[] rows = mFactoryManager.getCarSerials();

        mCarSalesPanel = new TableDataPanel("Car Sales", new String[]{"Car", "Items Sold"} ,rows);
        mFactoryManager.getCarStorageManager().addCarItemSalesListener(mCarSalesPanel);

        mProductionStatsPanel = new ProductionStatisticsPanel(mFactoryManager);

        EnvironmentConfiguration ec = mFactoryManager.getEnvironmentConfiguration();
        String[] header = new String[] { "Serial", "Items" };
        mBodyStorageLoadPanel = new TableDataPanel("Body Storage", header, ec.getBodySuppliersInfo().keySet().toArray(new String[0]));
        mEngineStorageLoadPanel = new TableDataPanel("Engine Storage", header, ec.getEngineSuppliersInfo().keySet().toArray(new String[0]));
        mAccessoryStorageLoadPanel = new TableDataPanel("Accessory Storage", header, ec.getAccessorySuppliersInfo().keySet().toArray(new String[0]));

        mFactoryManager.getBodyStorageManager().addStorageLoadObserver(mBodyStorageLoadPanel);
        mFactoryManager.getEngineStorageManager().addStorageLoadObserver(mEngineStorageLoadPanel);
        mFactoryManager.getAccessoryStorageManager().addStorageLoadObserver(mAccessoryStorageLoadPanel);
    }


    private void $$$setupUI$$$() {
        createUIComponents();

        mPanel = new JPanel();

    }

    private void handleOnToggleFactoryAction() {
        if (!mFactoryManager.isFactoryLaunched()) {
            mPowerButton.setText(STOP_LABEL);
            mFactoryManager.launchFactory();
        } else {
            mPowerButton.setEnabled(false);
            mFactoryManager.stopFactory();
        }
    }

}
