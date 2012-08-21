package eu.javaspecialists.deadlock.labideas.basic;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Hello world!
 */
public class App implements Runnable {
    private static Object lock = new Object();

    private Set<Integer> numbers = new ConcurrentSkipListSet<>();

    public static void main(String[] args) {
        App app = new App();
        for (int i = 0; i < 20; i++) {
            Thread t = new Thread(app);
            t.start();
        }
    }

    @Override
    public void run() {
        while (numbers.size() < 32000) {
            f();
            g();
        }
    }

    public synchronized void f() {
        synchronized (lock) {
            int i = ThreadLocalRandom.current().nextInt();
            System.out.println(i);
            numbers.add(i);
        }
    }

    public void g() {
        synchronized (lock) {
            f();
        }
    }
}
