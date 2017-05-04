package ru.nsu.ccfit.boltava.threadpool;

public interface ITask extends Runnable {

    String getName();
    void execute();

}
