package ru.nsu.ccfit.boltava;


public class ThreadPool {

    private BlockingQueue<Runnable> mTaskQueue;
    private Thread[] mWorkers;
    private int mActiveWorkersCount = 0;

    private final Object lock = new Object();

    public ThreadPool(int queueSize, int workersCount) {
        mTaskQueue = new BlockingQueue<>(queueSize);
        mWorkers = new Thread[workersCount];

        for (int i = 0; i < workersCount; ++i) {
            mWorkers[i] = new Thread(new Worker());
            mWorkers[i].start();
        }
    }

    public void feed(Runnable task) throws InterruptedException {
        mTaskQueue.enqueue(task);
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
                    Runnable task = mTaskQueue.dequeue();
                    incrementTaskCounter();
                    task.run();
                    decrementTaskCounter();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
