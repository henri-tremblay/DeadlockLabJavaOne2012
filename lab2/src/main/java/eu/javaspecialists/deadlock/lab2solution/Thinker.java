package eu.javaspecialists.deadlock.lab2solution;

import java.util.concurrent.*;

/**
 * Our philosopher always first locks left, then right.  If all of the thinkers
 * sit in a circle and their threads call "drink()" at the same time, then they
 * will end up with a deadlock.
 * <p/>
 * In our solution we did not add a random sleep, but you might need to do this
 * if you have a lot of conflicts.
 * <p/>
 * A fun thing to try is to count how many times you had to back off when you
 * could not acquire a lock.
 *
 * @author Heinz Kabutz
 */
public class Thinker implements Callable<String> {
    private final int id;
    private final Krasi right, left;

    public Thinker(int id, Krasi right, Krasi left) {
        this.id = id;
        this.right = right;
        this.left = left;
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
        while (true) {
            right.lock();
            try {
                if (left.tryLock()) {
                    try {
                        System.out.printf("(%d) Drinking%n", id);
                        return; // remember to return after a good drink
                    } finally {
                        left.unlock();
                    }
                }
            } finally {
                right.unlock();
            }
            // Possibly add a short random sleep to avoid a livelock, but only
            // do this after you have unlocked both locks.
        }
    }

    @SuppressWarnings("boxing")
    public void think() {
        System.out.printf("(%d) Thinking%n", id);
    }
}
