package ru.nsu.ccfit.boltava.view.StorageInfo;

import javax.swing.*;

public class StorageInfoRow extends JComponent {
    private JLabel mItemType;
    private JLabel mStorageLoad;
    private JLabel mStorageSize;
    private JPanel mPanel;


    public StorageInfoRow(String itemType) {
        mItemType.setText(itemType);
        mStorageLoad.setText("0");
    }

    public void setStorageLoad(String load) {
        mStorageLoad.setText(load);
    }

    public void setStorageSize(int size) {
        mStorageSize.setText(String.valueOf(size));
    }


}
