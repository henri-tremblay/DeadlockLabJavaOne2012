package eu.javaspecialists.deadlock.lab1;

import java.util.concurrent.*;

/**
 * Our philosopher always first locks left, then right.  If all of the thinkers
 * sit in a circle and their threads call "drink()" at the same time, then they
 * will end up with a deadlock.
 * <p/>
 * Instead of locking first on left and then on right, change the code to lock
 * on the bigger Krasi first.  This will avoid the deadlock, as we will always
 * lock in the same order.
 *
 * @author Heinz Kabutz
 */
public class Thinker implements Callable<String> {
    private final int id;
    private final Krasi right, left;

    public Thinker(int id, Krasi right, Krasi left) {
        this.id = id;
        this.left = left;
        this.right = right;
    }

    @Override
    public String call() throws Exception {
        for (int i = 0; i < 1000; i++) {
            drink();
            think();
        }
        return "Java is fun";
    }

    @SuppressWarnings("boxing")
    public void drink() {
        synchronized (right) {
            synchronized (left) {
                System.out.printf("(%d) Drinking%n", id);
            }
        }
    }

    @SuppressWarnings("boxing")
    public void think() {
        System.out.printf("(%d) Thinking%n", id);
    }
}
