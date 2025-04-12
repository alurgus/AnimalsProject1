package org.example;

public class Counter implements AutoCloseable {
    private int count;

    public Counter() {
        this.count = 0;
    }

    public void add() {
        count++;
        System.out.println("Количество животных: " + count);
    }

    @Override
    public void close() {
        if (count == 0) {
            throw new IllegalStateException("Ресурс был закрыт, но не использован.");
        }
        System.out.println("Ресурс закрыт корректно.");
    }
}
