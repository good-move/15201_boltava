package ru.nsu.ccfit.boltava.factory;


import ru.nsu.ccfit.boltava.BlockingQueue;

public class AssemblyLines {

    private BlockingQueue<ITask> mTaskQueue;
    private Thread[] mWorkers;
    private int mActiveWorkersCount = 0;

    public AssemblyLines(int taskQueueSize, int workersCount) {
        mTaskQueue = new BlockingQueue<>(taskQueueSize);
        mWorkers = new Thread[workersCount];

        for (int i = 0; i < workersCount; ++i) {
            mWorkers[i] = new Thread(new Worker());
            mWorkers[i].start();
        }
    }

    public void addTask(ITask ITask) throws InterruptedException {
        mTaskQueue.enqueue(ITask);
    }

    private synchronized void incrementTaskCounter() {
        mActiveWorkersCount++;
    }

    private synchronized void decrementTaskCounter() {
        mActiveWorkersCount--;
    }

    public int getActiveWorkersCount() {
        return mActiveWorkersCount;
    }

    private class Worker implements Runnable {

        @Override
        public void run() {
            try {
                while (true) {
                    ITask ITask = mTaskQueue.dequeue();
                    incrementTaskCounter();
                    ITask.execute();
                    decrementTaskCounter();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
