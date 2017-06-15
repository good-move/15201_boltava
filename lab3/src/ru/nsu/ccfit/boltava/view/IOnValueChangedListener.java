package ru.nsu.ccfit.boltava.view;

import ru.nsu.ccfit.boltava.model.message.response.LoginError;
import ru.nsu.ccfit.boltava.model.message.response.LoginSuccess;

public interface IOnValueChangedListener<T> {

    void onValueChanged(T value);

}
