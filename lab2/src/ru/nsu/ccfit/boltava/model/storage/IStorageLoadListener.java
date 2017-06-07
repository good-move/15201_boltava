package ru.nsu.ccfit.boltava.model.storage;

import java.awt.*;

public interface IStorageLoadListener {

    void onStorageLoadChanged(Storage<? extends Component> storage);

}
