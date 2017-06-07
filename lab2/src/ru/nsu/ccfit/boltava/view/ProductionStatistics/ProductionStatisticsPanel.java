package ru.nsu.ccfit.boltava.view.ProductionStatistics;

import ru.nsu.ccfit.boltava.model.FactoryManager;

import javax.swing.*;

public class ProductionStatisticsPanel extends JComponent {

    private ProductionInfoRow mCarsProducedInfoLabel;
    private ProductionInfoRow mEnginesSuppliedLabel;
    private ProductionInfoRow mBodiesSuppliedLabel;
    private ProductionInfoRow mAccessoriesSuppliedLabel;
    private JPanel mPanel;
    private JLabel mTitle;
    private ProductionInfoRow mPendingTasksLabel;

    public ProductionStatisticsPanel(FactoryManager factoryManager) {
        mTitle.setText("Supplement Statistics");
        factoryManager.getCarStorageManager().addOnItemPutListener(mCarsProducedInfoLabel);
        factoryManager.getBodyStorageManager().addOnItemPutListener(mBodiesSuppliedLabel);
        factoryManager.getEngineStorageManager().addOnItemPutListener(mEnginesSuppliedLabel);
        factoryManager.getAccessoryStorageManager().addOnItemPutListener(mAccessoriesSuppliedLabel);
        factoryManager.getAssemblyManager().addTaskQueueSizeListener(mPendingTasksLabel);
    }

    private void createUIComponents() {
        mCarsProducedInfoLabel = new ProductionInfoRow("Cars", "0");
        mEnginesSuppliedLabel = new ProductionInfoRow("Engines", "0");
        mBodiesSuppliedLabel = new ProductionInfoRow("Bodies", "0");
        mAccessoriesSuppliedLabel = new ProductionInfoRow("Accessories", "0");
        mPendingTasksLabel = new ProductionInfoRow("Pending Tasks", "0");
    }

}
