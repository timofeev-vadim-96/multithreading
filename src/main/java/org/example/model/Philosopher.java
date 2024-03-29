package org.example.model;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

/**
 * Философ
 */
public class Philosopher extends Thread {
    private final Fork leftFork;
    private final Fork rightFork;
    private final String name;
    private int eatCounter;
    private final CountDownLatch latch;
    private Semaphore waiter;

    /**
     * @param leftFork  порядковый номер вилки, лежащей слева
     * @param rightFork порядковый номер вилки по правую руку
     * @param name      имя философа / индекс
     * @param latch     счетчик потоков
     */
    public Philosopher(Fork leftFork, Fork rightFork, String name, CountDownLatch latch, Semaphore waiter) {
        this.leftFork = leftFork;
        this.rightFork = rightFork;
        this.name = name;
        eatCounter = 0;
        this.latch = latch;
        this.waiter = waiter;
    }

    @Override
    public void run() {
        while (eatCounter != 3) {
            try {
                toEat();
                toThink();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        latch.countDown();
    }

    /**
     * Метод принятия пищи
     *
     * @throws InterruptedException
     */
    private void toEat() throws InterruptedException {
        waiter.acquire();
        synchronized (leftFork) {
            synchronized (rightFork) {
                System.out.printf("%s принимает пищу с помощью вилок: %s, %s\n", name, leftFork, rightFork);
                Thread.sleep(500);
                eatCounter++;
            }
        }
        waiter.release();
    }

    /**
     * Метод размышления
     *
     * @throws InterruptedException
     */
    private void toThink() throws InterruptedException {
        System.out.printf("%s размышляет о вопросах бытия...\n", name);
        Thread.sleep(new Random().nextInt(501, 1501));
    }
}
