package ru.otus;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class AlternatePrinting {

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private boolean threadOneTurn = true;

    public void printNumbers() {
        Thread thread1 = new Thread(() -> {
            printSequence(1, 1, 10);
            printSequence(1, 9, 1);
        }, "Поток 1");
        Thread thread2 = new Thread(() -> {
            printSequence(2, 1, 10);
            printSequence(2, 9, 1);
        }, "Поток 2");

        thread1.start();
        thread2.start();
    }

    private void printSequence(int threadNumber, int start, int end) {
        int step = start < end ? 1 : -1;
        for (int i = start; step > 0 ? i <= end : i >= end; i += step) {
            lock.lock();
            try {
                while ((threadNumber == 1 && !threadOneTurn) || (threadNumber == 2 && threadOneTurn)) {
                    condition.await();
                }
                System.out.println(Thread.currentThread().getName() + ":" + i);
                threadOneTurn = !threadOneTurn;
                condition.signalAll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        new AlternatePrinting().printNumbers();
    }
}