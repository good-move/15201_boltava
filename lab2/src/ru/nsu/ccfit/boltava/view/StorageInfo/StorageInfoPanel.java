package ru.nsu.ccfit.boltava.view.StorageInfo;

import ru.nsu.ccfit.boltava.model.FactoryManager;

import javax.swing.*;

public class StorageInfoPanel {
    private JPanel mPanel;
    private StorageInfoRow mCarStorageInfo;
    private StorageInfoRow mEngineStorageInfo;
    private StorageInfoRow mBodyStorageInfo;
    private StorageInfoRow mAccessoryStorageInfo;

    public StorageInfoPanel(FactoryManager factoryManager) {
//        mCarStorageInfo.setSize(factoryManager.getCarStorageManager().getTotalStorageSize());
//        mEngineStorageInfo.setSize(factoryManager.getEngineStorageManager().getTotalStorageSize());
//        mBodyStorageInfo.setSize(factoryManager.getBodyStorageManager().getTotalStorageSize());
//        mAccessoryStorageInfo.setSize(factoryManager.getAccessoryStorageManager().getTotalStorageSize());

//        factoryManager.getCarStorageManager().addStorageLoadObserver(mCarStorageInfo);
//        factoryManager.getBodyStorageManager().
    }

    private void createUIComponents() {
        mCarStorageInfo = new StorageInfoRow("Car");
        mEngineStorageInfo = new StorageInfoRow("Engine");
        mBodyStorageInfo = new StorageInfoRow("Body");
        mAccessoryStorageInfo = new StorageInfoRow("Accessory");
    }

}
