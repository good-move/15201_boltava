package ru.nsu.ccfit.boltava.factory;


import ru.nsu.ccfit.boltava.BlockingQueue;

class AssemblyLines {

    private BlockingQueue<ITask> mTaskQueue;
    private Thread[] mWorkers;
    private int mActiveWorkersCount = 0;

    public AssemblyLines(int taskQueueSize, int workersCount) {
        mTaskQueue = new BlockingQueue<>(taskQueueSize);
        mWorkers = new Thread[workersCount];

        for (int i = 0; i < workersCount; ++i) {
            mWorkers[i] = new Thread(new Worker("Assembly Worker " + i));
            mWorkers[i].setName("Assembly Worker " + i);
            mWorkers[i].start();
        }
    }

    public void addTask(ITask ITask) throws InterruptedException {
        mTaskQueue.enqueue(ITask);
    }

    public void shutDown() {
        for (Thread thread : mWorkers) {
            thread.interrupt();
        }
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

        private final String mName;

        Worker(String name) {
            mName = name;
        }

        @Override
        public void run() {
            try {
                while (true) {
                    System.out.println(this.getClass().getSimpleName() + ": hard working");
                    ITask ITask = mTaskQueue.dequeue();
                    incrementTaskCounter();
                    ITask.execute();
                    decrementTaskCounter();
                }
            } catch (InterruptedException e) {
                System.out.println(mName + ": I've been interrupted");
            }
        }

    }

}
