package ru.nsu.ccfit.boltava.view;

import java.awt.*;

public interface IOnValueChangedForKeyListener<Key, Value> {

    void onValueChangedForKey(Key key, Value value);

}
