package ru.nsu.ccfit.boltava.threadpool;


public class ThreadPool {

    private BlockingQueue<Task> mTaskQueue;
    private Thread[] mWorkers;
    private int mActiveWorkersCount = 0;

    public ThreadPool(int queueSize, int workersCount) {
        mTaskQueue = new BlockingQueue<>(queueSize);
        mWorkers = new Thread[workersCount];

        for (int i = 0; i < workersCount; ++i) {
            mWorkers[i] = new Thread(new Worker());
            mWorkers[i].start();
        }
    }

    public void feed(Task task) throws InterruptedException {
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
                    Task task = mTaskQueue.dequeue();
                    incrementTaskCounter();
                    task.execute();
                    decrementTaskCounter();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

}
