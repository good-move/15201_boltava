package ru.nsu.ccfit.boltava.threadpool;

public interface Task extends Runnable {

    String getName();
    void execute();

}
