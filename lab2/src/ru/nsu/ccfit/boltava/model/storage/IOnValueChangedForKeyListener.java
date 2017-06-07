package ru.nsu.ccfit.boltava.model.storage;

import java.awt.*;

public interface IOnValueChangedForKeyListener<Key, Value> {

    void onValueChangedForKey(Key key, Value value);

}
