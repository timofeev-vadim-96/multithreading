package org.example.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Класс обеденного стола
 */
public class Table extends Thread {
    private final List<Philosopher> philosophers;
    private final List<Fork> forks;
    private final CountDownLatch latch;
    private final int numberOfPersons;

    /**
     * Конструктор стола
     *
     * @param numberOfPersons количество философов
     */
    public Table(int numberOfPersons) {
        this.philosophers = new ArrayList<>();
        this.forks = new ArrayList<>();
        latch = new CountDownLatch(numberOfPersons);
        this.numberOfPersons = numberOfPersons;
    }

    /**
     * Метод рассаживания за стол философов и расположения такого же количества вилок на столе
     */
    private void setTable() {
        for (int i = 0; i < numberOfPersons; i++) {
            forks.add(new Fork(i + 1));
        }
        for (int i = 0; i < numberOfPersons; i++) {
            int rightFork = i + 1;
            if (rightFork == forks.size()) rightFork = 0;
            philosophers.add(
                    new Philosopher(forks.get(i), forks.get(rightFork), String.format("Философ # %d", i + 1), latch));
        }
    }

    /**
     * Метод начала пира
     */
    private void beginFeast() {
        setTable();
        System.out.println("Начало пира...");
        for (Philosopher philosopher : philosophers) {
            philosopher.start();
        }
    }

    @Override
    public void run() {
        beginFeast();
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Все философы успешно поели!...");
    }
}
