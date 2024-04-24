package ru.otus;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class AlternatePrinting {

    private final ReentrantLock lock = new ReentrantLock();
    private final Condition condition = lock.newCondition();
    private boolean threadOneTurn = true;

    public void printNumbers() {
        Thread thread1 = new Thread(() -> print(1), "Поток 1");
        Thread thread2 = new Thread(() -> print(2), "Поток 2");

        thread1.start();
        thread2.start();
    }

    private void print(int threadNumber) {
        for (int i = 1; i <= 10; i++) {
            lock.lock();
            try {
                while ((threadNumber == 1 && !threadOneTurn) || (threadNumber == 2 && threadOneTurn)) {
                    condition.await();
                }
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + ":" + i);
                threadOneTurn = !threadOneTurn;
                condition.signalAll();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            } finally {
                lock.unlock();
            }
        }

        for (int i = 9; i >= 1; i--) {
            lock.lock();
            try {
                while ((threadNumber == 1 && !threadOneTurn) || (threadNumber == 2 && threadOneTurn)) {
                    condition.await();
                }
                Thread.sleep(1000);
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