package org.example.model;

/**
 * Вилка
 */
public class Fork {
    private final int forkNumber;

    /**
     * Конструктор
     *
     * @param forkNumber порядковый номер вилки
     */
    public Fork(int forkNumber) {
        this.forkNumber = forkNumber;
    }

    @Override
    public String toString() {
        return String.format("вилка # %d", forkNumber);
    }
}
