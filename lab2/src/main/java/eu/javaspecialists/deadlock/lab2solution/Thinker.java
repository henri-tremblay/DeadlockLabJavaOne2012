package eu.javaspecialists.deadlock.lab2solution;

import java.util.concurrent.*;

public class Thinker implements Callable<String> {
    private final int id;
    private final Krasi left, right;

    public Thinker(int id, Krasi left, Krasi right) {
        this.id = id;
        this.left = left;
        this.right = right;
    }

    public String call() throws Exception {
        for (int i = 0; i < 1000; i++) {
            drink();
            think();
        }
        return "Java is fun";
    }

    public void drink() {
        left.lock();
        try {
            right.lock();
            try {
                System.out.printf("(%d) Drinking%n", id);
            } finally {
                right.unlock();
            }
        } finally {
            left.unlock();
        }
    }

    public void think() {
        System.out.printf("(%d) Thinking%n", id);
    }
}
