package ru.nsu.ccfit.boltava.model.factory;


import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import ru.nsu.ccfit.boltava.model.BlockingQueue;
import ru.nsu.ccfit.boltava.view.IOnValueChangedListener;

class AssemblyLines {

    private static Logger logger = LogManager.getLogger(AssemblyLines.class.getName());
    private BlockingQueue<ITask> mTaskQueue;
    private Thread[] mWorkers;

    AssemblyLines(int taskQueueSize, int workersCount) {
        mTaskQueue = new BlockingQueue<>(taskQueueSize);
        mWorkers = new Thread[workersCount];

        for (int i = 0; i < workersCount; ++i) {
            mWorkers[i] = new Thread(new Worker("Assembly Worker " + i));
            mWorkers[i].setName("Assembly Worker " + i);
            mWorkers[i].start();
        }
    }

    void addTask(ITask ITask) throws InterruptedException {
        mTaskQueue.enqueue(ITask);
    }

    void shutDown() {
        for (Thread thread : mWorkers) {
            thread.interrupt();
        }
        mTaskQueue.clear();
    }

    void addTaskQueueSizeListener(IOnValueChangedListener<Integer> listener) {
        mTaskQueue.addOnValueChangedListener(listener);
    }

    private class Worker implements Runnable {

        private final String mName;

        Worker(String name) {
            mName = name;
        }

        @Override
        public void run() {
            try {
                while (!Thread.interrupted()) {
                    ITask task = mTaskQueue.dequeue();
                    task.execute();
                }
            } catch (InterruptedException e) {
                logger.info(mName + ": I've been interrupted");
            }
        }

    }

}
