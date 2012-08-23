package eu.javaspecialists.deadlock.lab1solution;

import java.util.concurrent.*;

/**
 * Our philosopher always first locks left, then right.  If all of the thinkers
 * sit in a circle and their threads call "drink()" at the same time, then they
 * will end up with a deadlock.
 *
 * In our solution, we decide in the constructor which the larger and smaller
 * locks are, rather than doing this every time the drink() method is called.
 *
 * @author Heinz Kabutz
 */
public class Thinker implements Callable<String> {
    private final int id;
    private final Krasi bigger, smaller;

    public Thinker(int id, Krasi left, Krasi right) {
        this.id = id;
        this.bigger = left.compareTo(right) > 0 ? left : right;
        this.smaller = right == bigger ? left : right;
        System.out.println("bigger = " + bigger);
        System.out.println("smaller = " + smaller);
    }

    public String call() throws Exception {
        for (int i = 0; i < 1000; i++) {
            drink();
            think();
        }
        return "Java is fun";
    }

    public void drink() {
        synchronized (bigger) {
            synchronized (smaller) {
                System.out.printf("(%d) Drinking%n", id);
            }
        }
    }

    public void think() {
        System.out.printf("(%d) Thinking%n", id);
    }
}
